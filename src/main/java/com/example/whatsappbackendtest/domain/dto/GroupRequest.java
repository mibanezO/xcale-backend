package com.example.whatsappbackendtest.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.util.List;

@Data
public class GroupRequest {
    private String photo;
    @NotEmpty
    private String name;
    @NotEmpty
    @Size(min = 1)
    private List<@NotNull BigInteger> numbers;
}
