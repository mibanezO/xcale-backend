package com.example.whatsappbackendtest.controller;

import com.example.whatsappbackendtest.domain.dto.UserRequest;
import com.example.whatsappbackendtest.domain.model.User;
import com.example.whatsappbackendtest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.math.BigInteger;

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/{number}")
    public User getUser(@PathVariable @NotNull BigInteger number) {
        return service.getUser(number);
    }

    @PostMapping
    public User create(@RequestBody @NotNull @Valid UserRequest user) {
        return service.create(user);
    }

    @PutMapping
    public User update(@RequestBody @NotNull @Valid UserRequest request) {
        return service.update(request);
    }

    @PostMapping("/{number}/photo")
    public void uploadPhoto(
            @PathVariable @NotNull BigInteger number,
            @RequestPart(name = "photo") @NotNull MultipartFile photo
    ) throws IOException {
        service.uploadPhoto(number, photo);
    }

}
