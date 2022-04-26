package com.example.whatsappbackendtest.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiException {
    private Integer status;
    private LocalDateTime timestamp;
    private String message;
}
