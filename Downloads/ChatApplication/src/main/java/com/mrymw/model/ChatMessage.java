package com.mrymw.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

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
    private String senderName;
    @Column
    private String recipientName;

    @Column
    private boolean isEdited;

    @Column
    private boolean isRead;

    @Column(nullable = false, updatable = false)
    private LocalDateTime sendAt;

    @Column
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageType messageType;

    @PrePersist
    protected void onCreate() {
        this.sendAt = LocalDateTime.now();
    }
}
