package com.example.whatsappbackendtest.service;

import com.example.whatsappbackendtest.domain.dto.UserRequest;
import com.example.whatsappbackendtest.domain.exception.UserNotFoundException;
import com.example.whatsappbackendtest.domain.model.User;
import com.example.whatsappbackendtest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;

import static com.example.whatsappbackendtest.util.DecodeImageAsBase64.decodeAsByteArray;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User getUser(BigInteger number) {
        return repository.findById(number)
                .orElseThrow(() -> new UserNotFoundException(number));
    }


    public User create(UserRequest user) {
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setNumber(user.getNumber());
        newUser.setPhoto(decodeAsByteArray(user.getPhoto()));
        return repository.save(newUser);
    }

    public User update(UserRequest request) {
        User user = getUser(request.getNumber());
        user.setName(request.getName());
        return repository.save(user);
    }

    public void uploadPhoto(BigInteger number, MultipartFile photo) throws IOException {
        User user = getUser(number);
        user.setPhoto(photo.getBytes());
        repository.save(user);
    }
}
