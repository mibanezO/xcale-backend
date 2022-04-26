package com.example.whatsappbackendtest.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.UUID;

@Data
public class UserGroupRequest {
    @NotNull
    private BigInteger userNumber;
    @NotNull
    private UUID groupId;
}
