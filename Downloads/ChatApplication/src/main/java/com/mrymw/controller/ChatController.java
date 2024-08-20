package com.mrymw.controller;

import com.mrymw.model.ChatMessage;
import com.mrymw.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatMessageService chatMessageService;

    @Autowired
    public ChatController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }
    // WebSocket Endpoint
    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessageService.saveMessage(chatMessage);
    }

    // REST Endpoint
    @PostMapping("/sendMessage")
    public ChatMessage sendMessageHttp(@RequestBody ChatMessage chatMessage) {
        return chatMessageService.saveMessage(chatMessage);
    }

    // WebSocket Endpoint
    @MessageMapping("/addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessageService.saveMessage(chatMessage);
    }
}
