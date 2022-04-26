package com.example.whatsappbackendtest.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
public class UserRequest {
    private String photo;
    private String name;
    @NotNull
    private BigInteger number;
}
