package com.mrymw.controller;

import com.mrymw.model.ChatMessage;
import com.mrymw.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageRestController {

    @Autowired
    private ChatMessageService chatMessageService;

    @GetMapping("/sender/{sender}")
    public List<ChatMessage> getMessagesBySender(@PathVariable String sender) {
        return chatMessageService.getMessagesBySender(sender);
    }

    @GetMapping("/receiver/{receiver}")
    public List<ChatMessage> getMessagesByReceiver(@PathVariable String receiver) {
        return chatMessageService.getMessagesByReceiver(receiver);
    }
}
