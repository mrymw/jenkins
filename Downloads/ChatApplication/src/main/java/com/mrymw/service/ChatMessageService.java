package com.mrymw.service;

import com.mrymw.model.ChatMessage;
import com.mrymw.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public ChatMessage saveMessage(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

    public void unsentMessage(Long messageId) {
        ChatMessage message = chatMessageRepository.findById(messageId).orElse(null);
        if (message != null && !message.isRead()) {
            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(message.getSendAt(), now);
            if (duration.toMinutes() <= 10) {
                chatMessageRepository.delete(message);
            } else {
                throw new RuntimeException("Message cannot be unsent after 10 minutes.");
            }
        }
    }

    public List<ChatMessage> searchMessages(String keyword) {
        return chatMessageRepository.findByContentContainingIgnoreCase(keyword);
    }

    public void editMessage(Long messageId, String newContent) {
        ChatMessage message = chatMessageRepository.findById(messageId).orElse(null);
        if (message != null && !message.isRead()) {
            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(message.getSendAt(), now);
            if (duration.toMinutes() <= 10) {
                message.setContent(newContent);
                chatMessageRepository.save(message);
            }
        }
    }

    public long countUnreadMessages(String recipientName) {
        return chatMessageRepository.countByRecipientNameAndIsRead(recipientName, false);
    }
}
