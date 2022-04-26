package com.example.whatsappbackendtest.controller;

import com.example.whatsappbackendtest.domain.dto.MessageResponse;
import com.example.whatsappbackendtest.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@CrossOrigin(originPatterns = "*")
@Controller
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService service;

    @GetMapping("/{groupId}")
    @ResponseBody
    public MessageResponse fetchMessages(@PathVariable @NotNull UUID groupId, @RequestParam @NotNull Integer page) {
        return service.fetchMessages(groupId, page);
    }
}
