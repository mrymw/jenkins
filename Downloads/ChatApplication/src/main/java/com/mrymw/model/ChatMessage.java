package com.mrymw.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "messages")
public class ChatMessage {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates ID
    private Long id;

    @Column
    private String sender;
    @Column
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageType messageType;
}
