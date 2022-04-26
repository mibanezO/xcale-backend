package com.example.whatsappbackendtest.controller;

import com.example.whatsappbackendtest.domain.dto.MessageRequest;
import com.example.whatsappbackendtest.domain.dto.ReceivedMessage;
import com.example.whatsappbackendtest.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ConversationController {

    @Value("${messaging.properties.destination-prefix}")
    private String destinationPrefix;
    private final MessageService service;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/messages")
    public void sendMessage(@Payload MessageRequest messageRequest) {
        log.info("Received message {}", messageRequest);
        ReceivedMessage receivedMessage = service.storeMessage(messageRequest);
        String destination = String.format("%s-%s", destinationPrefix, messageRequest.getGroupId().toString());
        log.info("Message saved, sending to {}", destination);
        messagingTemplate.convertAndSend(destination, receivedMessage);
    }

}
