package com.mrymw.repository;

import com.mrymw.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    //List<ChatMessage> findByContentContainingIgnoreCase(String keyword);
    //long countByReceiverNameAndIsRead(String receiverName, boolean isRead);
    List<ChatMessage> findBySenderOrReceiver(String sender, String receiver);
    List<ChatMessage> findBySender(String sender);
    List<ChatMessage> findByReceiver(String receiver);
}

