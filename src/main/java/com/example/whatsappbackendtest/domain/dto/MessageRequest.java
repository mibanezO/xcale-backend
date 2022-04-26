package com.example.whatsappbackendtest.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.UUID;

@Data
public class MessageRequest {
    @NotNull
    private BigInteger number;
    @NotEmpty
    private String message;
    @NotNull
    private UUID groupId;
}
