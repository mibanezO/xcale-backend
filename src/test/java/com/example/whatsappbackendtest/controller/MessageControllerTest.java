package com.example.whatsappbackendtest.controller;

import com.example.whatsappbackendtest.domain.dto.MessageResponse;
import config.BaseControllerTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MessageControllerTest extends BaseControllerTest {

    private static final String API_BASE_PATH = "/api/messages";

    @Test
    void shouldReturnBadRequestForMissingParam() throws Exception {
        mockMvc.perform(
                get(API_BASE_PATH + "/" + UUID.randomUUID())
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnOk() throws Exception {
        when(messageService.fetchMessages(any(), any()))
                .thenReturn(new MessageResponse());
        mockMvc.perform(
                get(API_BASE_PATH + "/" + UUID.randomUUID())
                        .param("page", "0")
        ).andExpect(status().isOk());
    }

}