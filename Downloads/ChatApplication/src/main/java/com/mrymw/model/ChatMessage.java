package com.mrymw.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
    private String sender;
    private String content;
    private MessageType messageType;
}
