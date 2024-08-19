package com.mrymw.configuration;

import com.mrymw.model.ChatMessage;
import com.mrymw.model.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.awt.*;
import java.util.Objects;



@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messageTemplate;
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (Objects.nonNull(username)) {
            log.info("User disconnected {}", username);
            var chatMessage = ChatMessage.builder()
                    .messageType(MessageType.LEAVE)
                    .sender(username)
                    .build();
            messageTemplate.convertAndSend("/topic/public", chatMessage);

        }

    }

}
