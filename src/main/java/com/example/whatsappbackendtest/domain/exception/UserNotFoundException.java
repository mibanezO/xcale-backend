package com.example.whatsappbackendtest.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigInteger;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(BigInteger number) {
        super(String.format("No se encontró al usuario con el teléfono %d", number));
    }
}
