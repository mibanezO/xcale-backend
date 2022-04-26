package com.example.whatsappbackendtest.service;

import com.example.whatsappbackendtest.domain.dto.UserRequest;
import com.example.whatsappbackendtest.domain.exception.UserNotFoundException;
import com.example.whatsappbackendtest.domain.model.User;
import config.BaseServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest extends BaseServiceTest {

    @Autowired
    private UserService userService;
    private UserRequest userRequest;

    @BeforeEach
    void setup() {
        userRequest = new UserRequest();
        userRequest.setName("Test User");
        userRequest.setNumber(new BigInteger("101010101"));
    }

    @Test
    void shouldSaveUser() {
        userService.create(userRequest);
        User user = userService.getUser(userRequest.getNumber());
        assertNotNull(user);
        assertEquals(userRequest.getNumber(), user.getNumber());
        assertEquals(userRequest.getName(), user.getName());
    }

    @Test
    void shouldUpdateName() {
        User savedUser = userService.create(userRequest);
        assertEquals("Test User", savedUser.getName());
        userRequest.setName("Updated User Name");
        User updatedUser = userService.update(userRequest);
        assertEquals("Updated User Name", updatedUser.getName());
    }

    @Test
    void shouldTrowExceptionWhenUserIsNotFound() {
        assertThrows(UserNotFoundException.class, () -> userService.getUser(new BigInteger("101010101")));
    }

    @Test
    void uploadUserPhoto() throws IOException {
        User user = userService.create(userRequest);
        userService.uploadPhoto(user.getNumber(), new MockMultipartFile("photo", "Some Photo".getBytes(StandardCharsets.UTF_8)));
        User userWithPhoto = userService.getUser(user.getNumber());
        assertNotNull(userWithPhoto.getPhoto());
    }

}