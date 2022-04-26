package com.example.whatsappbackendtest.domain.dto;

import com.example.whatsappbackendtest.domain.model.Message;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class MessageResponse {
    private Integer nextPage;
    private Long total;
    private UUID groupId;
    private List<ReceivedMessage> messages;
}
