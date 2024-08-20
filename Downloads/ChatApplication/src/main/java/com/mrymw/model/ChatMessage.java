package com.mrymw.model;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "messages")
public class ChatMessage {
    @Column
    private String sender;
    @Column
    private String content;
    @Column
    private MessageType messageType;
}
