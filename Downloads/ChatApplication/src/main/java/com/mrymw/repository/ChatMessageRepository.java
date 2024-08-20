package com.mrymw.repository;

import com.mrymw.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByContentContainingIgnoreCase(String keyword);
    long countByRecipientNameAndIsRead(String recipientName, boolean isRead);
}

