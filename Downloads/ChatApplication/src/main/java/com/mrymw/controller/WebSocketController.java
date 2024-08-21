package com.mrymw.controller;

import com.mrymw.model.ChatMessage;
import com.mrymw.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;


@Controller
public class WebSocketController {


    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate, ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/send")
    public ChatMessage sendMessage(@Payload ChatMessage message) {
        if(message.getReceiver() == null || message.getReceiver().isEmpty()) {
            messagingTemplate.convertAndSend("/topic/public", message);
        } else {
            messagingTemplate.convertAndSendToUser(message.getReceiver(), "/queue/messages", message);
        }
        chatMessageService.saveMessage(message);
        return message;
    }
    @MessageMapping("/add")
    @SendTo("/topic/public")
    public ChatMessage add(@Payload ChatMessage message, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        simpMessageHeaderAccessor.getSessionAttributes().put("username", message.getSender());
        return message;
    }

}
