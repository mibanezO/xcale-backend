package com.example.whatsappbackendtest.controller;

import com.example.whatsappbackendtest.domain.dto.GroupRequest;
import com.example.whatsappbackendtest.domain.dto.UserGroupRequest;
import com.example.whatsappbackendtest.domain.exception.GroupNotFoundException;
import com.example.whatsappbackendtest.domain.model.Group;
import com.fasterxml.jackson.core.type.TypeReference;
import config.BaseControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GroupControllerTest extends BaseControllerTest {

    private static final String API_BASE_PATH = "/api/group";

    @BeforeEach
    void setup() {
    }

    @Test
    void shouldReturnGroup() throws Exception {
        when(groupService.findById(any())).thenReturn(new Group());
        MvcResult result = mockMvc.perform(
                        get(API_BASE_PATH + '/' + UUID.randomUUID())
                ).andExpect(status().isOk())
                .andReturn();
        assertNotNull(result.getResponse().getContentAsString());
        Group group = convertTo(result.getResponse().getContentAsString(), new TypeReference<Group>() {
        });
        assertNotNull(group);
    }

    @Test
    void shouldReturnStatusNotFound() throws Exception {
        UUID notFoundUUID = UUID.randomUUID();
        when(groupService.findById(any())).thenThrow(new GroupNotFoundException(notFoundUUID));
        mockMvc.perform(
                get(API_BASE_PATH + '/' + notFoundUUID)
        ).andExpect(status().isNotFound());
    }

    @Test
    void whenCreateNewGroup() throws Exception {
        when(groupService.createGroup(any())).thenReturn(new Group());
        GroupRequest request = new GroupRequest();
        request.setName("Some Group");
        request.setNumbers(Collections.singletonList(new BigInteger("101010101")));
        mockMvc.perform(
                post(API_BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request))
        ).andExpect(status().isOk());
    }

    @Test
    void shouldThrowBadRequestOnEmptyBody() throws Exception {
        mockMvc.perform(
                post(API_BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldThrowBadRequestOnMissingParam() throws Exception {
        mockMvc.perform(
                post(API_BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new GroupRequest()))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldThrowBadRequestOnEmptyBodyForUser() throws Exception {
        mockMvc.perform(
                put(API_BASE_PATH + "/user")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldThrowBadRequestOnMissingParamForUser() throws Exception {
        mockMvc.perform(
                put(API_BASE_PATH + "/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new UserGroupRequest()))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldAddUserToGroup() throws Exception {
        UserGroupRequest request = new UserGroupRequest();
        request.setGroupId(UUID.randomUUID());
        request.setUserNumber(new BigInteger("101010101"));
        mockMvc.perform(
                put(API_BASE_PATH + "/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request))
        ).andExpect(status().isOk());
    }

    @Test
    void shouldListGroupForUsers() throws Exception {
        when(groupService.findForUser(any())).thenReturn(Collections.emptyList());
        mockMvc.perform(
                get(API_BASE_PATH + "/user/101010101")
        ).andExpect(status().isOk());
    }

    @Test
    void shouldThrowBadRequestWhenPartIsMissing() throws Exception {
        mockMvc.perform(
                multipart(API_BASE_PATH + "/" + UUID.randomUUID() + "/photo")
                        .file(new MockMultipartFile("someFile", "File Content".getBytes(StandardCharsets.UTF_8)))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldUploadPhoto() throws Exception {
        mockMvc.perform(
                multipart(API_BASE_PATH + "/" + UUID.randomUUID() + "/photo")
                        .file(new MockMultipartFile("photo", "File Content".getBytes(StandardCharsets.UTF_8)))
        ).andExpect(status().isOk());
    }

}