package com.example.whatsappbackendtest.controller;

import com.example.whatsappbackendtest.domain.dto.UserRequest;
import com.example.whatsappbackendtest.domain.model.User;
import config.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseControllerTest {

    private static final String API_BASE_PATH = "/api/user";

    @Test
    void shouldReturnUser() throws Exception {
        when(userService.getUser(any())).thenReturn(new User());
        mockMvc.perform(
                get(API_BASE_PATH + "/101010101")
        ).andExpect(status().isOk());
    }

    @Test
    void shouldThrowBadRequestWhenUserIsNull() throws Exception {
        mockMvc.perform(
                post(API_BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldThrowBadRequestWhenUserParamsAreMissing() throws Exception {
        mockMvc.perform(
                post(API_BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new UserRequest()))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldCreateUser() throws Exception {
        when(userService.create(any())).thenReturn(new User());
        UserRequest request = new UserRequest();
        request.setNumber(new BigInteger("101010101"));
        request.setName("Some User");
        mockMvc.perform(
                post(API_BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request))
        ).andExpect(status().isOk());
    }

    @Test
    void shouldThrowBadRequestUpdatingUserWithNoBody() throws Exception {
        mockMvc.perform(
                put(API_BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldThrowBadRequestUpdatingUserParamsAreMissing() throws Exception {
        mockMvc.perform(
                put(API_BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new UserRequest()))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateUser() throws Exception {
        when(userService.update(any())).thenReturn(new User());
        UserRequest request = new UserRequest();
        request.setNumber(new BigInteger("101010101"));
        request.setName("Some User");
        mockMvc.perform(
                put(API_BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request))
        ).andExpect(status().isOk());
    }

    @Test
    void shouldThrowBadRequestWhenPartIsMissing() throws Exception {
        mockMvc.perform(
                multipart(API_BASE_PATH + "/101010101/photo")
                        .file(new MockMultipartFile("someFile", "File Content".getBytes(StandardCharsets.UTF_8)))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldUploadPhoto() throws Exception {
        mockMvc.perform(
                multipart(API_BASE_PATH + "/101010101/photo")
                        .file(new MockMultipartFile("photo", "File Content".getBytes(StandardCharsets.UTF_8)))
        ).andExpect(status().isOk());
    }

}