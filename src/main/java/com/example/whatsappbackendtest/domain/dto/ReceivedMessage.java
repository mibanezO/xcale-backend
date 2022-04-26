package com.example.whatsappbackendtest.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReceivedMessage extends MessageRequest {
    private UUID Id;
    private String username;
}
