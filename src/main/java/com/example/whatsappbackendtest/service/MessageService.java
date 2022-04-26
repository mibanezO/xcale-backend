package com.example.whatsappbackendtest.service;

import com.example.whatsappbackendtest.domain.dto.MessageRequest;
import com.example.whatsappbackendtest.domain.dto.MessageResponse;
import com.example.whatsappbackendtest.domain.dto.ReceivedMessage;
import com.example.whatsappbackendtest.domain.model.Conversation;
import com.example.whatsappbackendtest.domain.model.ConversationId;
import com.example.whatsappbackendtest.domain.model.Message;
import com.example.whatsappbackendtest.domain.model.User;
import com.example.whatsappbackendtest.repository.ConversationRepository;
import com.example.whatsappbackendtest.repository.GroupRepository;
import com.example.whatsappbackendtest.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final UserService service;
    private final MessageRepository repository;
    private final GroupRepository groupRepository;
    private final ConversationRepository conversationRepository;

    public ReceivedMessage storeMessage(MessageRequest request) {
        Message message = saveMessage(request);
        saveConversation(request.getGroupId(), message);
        ReceivedMessage receivedMessage = new ReceivedMessage();
        copyProperties(request, receivedMessage);
        receivedMessage.setId(message.getId());
        receivedMessage.setUsername(fetchUsername(request.getNumber()));
        return receivedMessage;
    }

    private String fetchUsername(BigInteger number) {
        User user = service.getUser(number);
        return user.getName();
    }

    private Message saveMessage(MessageRequest request) {
        Message message = new Message();
        message.setMessage(request.getMessage());
        message.setDate(LocalDateTime.now());
        message.setId(UUID.randomUUID());
        message.setUserNumber(request.getNumber());
        return repository.save(message);
    }

    private void saveConversation(UUID groupId, Message message) {
        ConversationId conversationId = new ConversationId();
        conversationId.setMessageId(message.getId());
        conversationId.setGroupId(groupId);
        Conversation conversation = new Conversation();
        conversation.setId(conversationId);
        conversation.setMessage(message);
        conversation.setGroup(groupRepository.getById(groupId));
        conversationRepository.save(conversation);
    }

    public MessageResponse fetchMessages(UUID groupId, Integer page) {
        Pageable pageable = PageRequest.of(page, 25)
                .withSort(Sort.Direction.DESC, "message.date");
        Page<Conversation> conversations = conversationRepository.paginateByGroupId(groupId, pageable);
        MessageResponse response = new MessageResponse();
        response.setMessages(
                conversations.getContent().stream()
                        .map(this::toReceivedMessage)
                .collect(Collectors.toList())
        );
        response.setTotal(conversations.getTotalElements());
        Integer nextPage = conversations.hasNext() ? page + 1 : null;
        response.setNextPage(nextPage);
        response.setGroupId(groupId);
        return response;
    }

    private ReceivedMessage toReceivedMessage(Conversation conversation) {
        Message message = conversation.getMessage();
        ReceivedMessage receivedMessage = new ReceivedMessage();
        receivedMessage.setId(message.getId());
        receivedMessage.setNumber(message.getUserNumber());
        receivedMessage.setGroupId(conversation.getId().getGroupId());
        receivedMessage.setMessage(message.getMessage());
        receivedMessage.setUsername(fetchUsername(message.getUserNumber()));
        return receivedMessage;
    }

}
