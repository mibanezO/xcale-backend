package com.example.whatsappbackendtest.service;

import com.example.whatsappbackendtest.domain.dto.*;
import com.example.whatsappbackendtest.domain.model.Group;
import com.example.whatsappbackendtest.domain.model.User;
import config.BaseServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.Collections;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MessageServiceTest extends BaseServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private MessageService messageService;

    private User user;
    private Group group;
    private MessageRequest messageRequest;

    @BeforeEach
    void setup() {
        user = createUser();
        group = createGroup();
        messageRequest = new MessageRequest();
        messageRequest.setMessage("Some Message");
        messageRequest.setNumber(user.getNumber());
        messageRequest.setGroupId(group.getId());
    }

    private Group createGroup() {
        GroupRequest request = new GroupRequest();
        request.setName("Some Group");
        request.setNumbers(Collections.singletonList(user.getNumber()));
        return groupService.createGroup(request);
    }

    private User createUser() {
        UserRequest request = new UserRequest();
        request.setNumber(new BigInteger("101010101"));
        request.setName("Some User");
        return userService.create(request);
    }

    @Test
    void shouldSaveMessage() {
        ReceivedMessage receivedMessage = messageService.storeMessage(messageRequest);
        assertNotNull(receivedMessage);
        assertNotNull(receivedMessage.getId());
        assertEquals("Some User", receivedMessage.getUsername());
    }

    @Test
    void shouldListAllAvailableMessages() {
        IntStream.range(1, 11).mapToObj(number -> {
            MessageRequest request = new MessageRequest();
            request.setMessage(String.format("Message Nº %d", number));
            request.setNumber(user.getNumber());
            request.setGroupId(group.getId());
            return request;
        }).forEach(message -> messageService.storeMessage(message));
        MessageResponse messages = messageService.fetchMessages(group.getId(), 0);
        assertNotNull(messages);
        assertEquals(10, messages.getTotal());
        assertEquals(group.getId(), messages.getGroupId());
        assertNotNull(messages.getMessages());
        assertFalse(messages.getMessages().isEmpty());
    }

}