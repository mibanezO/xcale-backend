package com.example.whatsappbackendtest.controller;

import com.example.whatsappbackendtest.domain.dto.GroupRequest;
import com.example.whatsappbackendtest.domain.dto.UserGroupRequest;
import com.example.whatsappbackendtest.domain.model.Group;
import com.example.whatsappbackendtest.domain.model.UserGroup;
import com.example.whatsappbackendtest.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@Validated
@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService service;

    @GetMapping("/{groupId}")
    public Group findGroup(@PathVariable @NotNull UUID groupId) {
        return service.findById(groupId);
    }

    @PostMapping
    public Group createGroup(@RequestBody @NotNull @Valid GroupRequest request) {
        return service.createGroup(request);
    }

    @GetMapping("/user/{number}")
    public List<Group> findForUser(@PathVariable @NotNull BigInteger number) {
        return service.findForUser(number);
    }

    @PutMapping("/user")
    public UserGroup addUser(@RequestBody @NotNull @Valid UserGroupRequest request) {
        return service.addUser(request);
    }

    @PostMapping("/{groupId}/photo")
    public void uploadPhoto(@PathVariable @NotNull UUID groupId, @RequestPart @NotNull MultipartFile photo) throws IOException {
        service.uploadPhoto(groupId, photo);
    }

}
