package com.example.whatsappbackendtest.service;

import com.example.whatsappbackendtest.domain.dto.GroupRequest;
import com.example.whatsappbackendtest.domain.dto.UserGroupRequest;
import com.example.whatsappbackendtest.domain.dto.UserRequest;
import com.example.whatsappbackendtest.domain.exception.GroupNotFoundException;
import com.example.whatsappbackendtest.domain.model.Group;
import com.example.whatsappbackendtest.domain.model.User;
import com.example.whatsappbackendtest.domain.model.UserGroup;
import config.BaseServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class GroupServiceTest extends BaseServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;

    private GroupRequest groupRequest;
    private User user;

    @BeforeEach
    void setup() {
        UserRequest request = new UserRequest();
        request.setNumber(new BigInteger("101010101"));
        request.setName("Test User");

        user = userService.create(request);

        groupRequest = new GroupRequest();
        groupRequest.setName("Test Group");
        groupRequest.setNumbers(Collections.singletonList(request.getNumber()));
    }

    @Test
    void shouldSaveGroup() {
        Group group = groupService.createGroup(groupRequest);
        assertNotNull(group);
        Group fetched = groupService.findById(group.getId());
        assertNotNull(fetched);
        assertEquals(fetched.getName(), groupRequest.getName());
    }

    @Test
    void shouldAddUserToGroup() {
        User newUser = createNewUser();
        Group group = groupService.createGroup(groupRequest);
        UserGroupRequest request = new UserGroupRequest();
        request.setUserNumber(newUser.getNumber());
        request.setGroupId(group.getId());
        UserGroup userGroup = groupService.addUser(request);
        assertNotNull(userGroup);
    }

    private User createNewUser() {
        UserRequest request1 = new UserRequest();
        request1.setName("Another User");
        request1.setNumber(new BigInteger("121212121"));
        return userService.create(request1);
    }

    @Test
    void shouldTrowExceptionWhenGroupDoesNotExists() {
        UserGroupRequest request = new UserGroupRequest();
        request.setUserNumber(user.getNumber());
        request.setGroupId(UUID.randomUUID());
        assertThrows(GroupNotFoundException.class, () -> groupService.addUser(request));
    }

    @Test
    void uploadGroupPhoto() throws IOException {
        Group group = groupService.createGroup(groupRequest);
        groupService.uploadPhoto(group.getId(), new MockMultipartFile("file", "Some image".getBytes(StandardCharsets.UTF_8)));
        Group groupWithPhoto = groupService.findById(group.getId());
        assertNotNull(groupWithPhoto.getPhoto());
    }

    @Test
    void fetchAllGroupsForUser() {
        IntStream.range(1, 6).mapToObj(number -> {
            GroupRequest request = new GroupRequest();
            request.setName(String.format("Group Nº %d", number));
            request.setNumbers(Collections.singletonList(user.getNumber()));
            return request;
        }).forEach(groupService::createGroup);
        List<Group> groups = groupService.findForUser(user.getNumber());
        assertNotNull(groups);
        assertFalse(groups.isEmpty());
        assertEquals(5, groups.size());
    }

}