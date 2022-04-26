package com.example.whatsappbackendtest.controller;

import com.example.whatsappbackendtest.domain.dto.MessageRequest;
import com.example.whatsappbackendtest.domain.dto.ReceivedMessage;
import config.BaseControllerTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.beans.BeanUtils.copyProperties;

@Slf4j
class ConversationControllerTest extends BaseControllerTest {

    @Value("${messaging.properties.destination-prefix}")
    private String destinationPrefix;
    @LocalServerPort
    private Integer port;
    private String socketPath;
    private WebSocketStompClient stompClient;

    @BeforeEach
    void setup() {
        stompClient = new WebSocketStompClient(new SockJsClient(List.of(new WebSocketTransport(new StandardWebSocketClient()))));
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        socketPath = String.format("ws://localhost:%d/chat/room", port);
    }

    @Test
    void shouldOpenSocketAndReceiveMessage() throws ExecutionException, InterruptedException, TimeoutException {
        UUID groupId = UUID.randomUUID();
        String channel = String.format("%s-%s", destinationPrefix, groupId);
        CompletableFuture<ReceivedMessage> blockingQueue = new CompletableFuture<>();
        StompSession stompSession = stompClient.connect(socketPath, new StompSessionHandlerAdapter() {
        }).get(1, TimeUnit.SECONDS);
        stompSession.subscribe(channel, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return ReceivedMessage.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                ReceivedMessage receivedMessage = (ReceivedMessage) payload;
                blockingQueue.complete(receivedMessage);
            }
        });
        MessageRequest request = new MessageRequest();
        request.setGroupId(groupId);
        request.setNumber(new BigInteger("101010101"));
        request.setMessage("Some text message");
        ReceivedMessage message = new ReceivedMessage();
        copyProperties(request, message);
        when(messageService.storeMessage(any())).thenReturn(message);
        stompSession.send("/chat-app/messages", request);

        CompletableFuture.allOf(blockingQueue).join();
        ReceivedMessage receivedMessage = blockingQueue.get();
        assertNotNull(receivedMessage);
        assertEquals(groupId, receivedMessage.getGroupId());
        assertEquals(request.getNumber(), receivedMessage.getNumber());
        assertEquals("Some text message", receivedMessage.getMessage());
    }

}