package com.example.whatsappbackendtest.controller;

import com.example.whatsappbackendtest.domain.dto.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiException handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return new ApiException(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), "El cuerpo de la llamada no puede estar vacío");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiException handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String message = ex.getFieldErrors().stream()
                .map(fieldError -> String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.joining("\n"));
        return new ApiException(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), message);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ApiException handleMissingServletRequestPartException(MissingServletRequestPartException ex) {
        return new ApiException(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), String.format("Debe enviar el parámetro %s", ex.getRequestPartName()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiException handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        return new ApiException(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), String.format("Debe enviar el parámetro %s", ex.getParameterName()));
    }

}
