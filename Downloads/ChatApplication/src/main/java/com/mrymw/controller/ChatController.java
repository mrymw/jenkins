package com.mrymw.controller;

import com.mrymw.model.ChatMessage;
import com.mrymw.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/chat")
public class ChatController {

    private final ChatMessageService chatMessageService;

    @Autowired
    public ChatController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    // REST Endpoint for sending messages
    @PostMapping("/sendMessage")
    public ChatMessage sendMessageHttp(@RequestBody ChatMessage chatMessage) {
        return chatMessageService.saveMessage(chatMessage);
    }

    // REST Endpoint to unsend a message
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<String> unsendMessage(@PathVariable Long messageId) {
        try {
            chatMessageService.unsentMessage(messageId);
            return ResponseEntity.ok("Message unsent successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // REST Endpoint to search messages
    @GetMapping("/search")
    public ResponseEntity<List<ChatMessage>> searchMessages(@RequestParam String keyword) {
        List<ChatMessage> messages = chatMessageService.searchMessages(keyword);
        return ResponseEntity.ok(messages);
    }

    // REST Endpoint to edit a message
    @PutMapping("/messages/{messageId}")
    public ResponseEntity<String> editMessage(@PathVariable Long messageId, @RequestBody String newContent) {
        try {
            chatMessageService.editMessage(messageId, newContent);
            return ResponseEntity.ok("Message edited successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // REST Endpoint to get unread message count
    @GetMapping("/unreadCount/{receiver}")
    public ResponseEntity<Long> getUnreadMessageCount(@PathVariable String receiver) {
        long unreadCount = chatMessageService.countUnreadMessages(receiver);
        return ResponseEntity.ok(unreadCount);
    }
}
