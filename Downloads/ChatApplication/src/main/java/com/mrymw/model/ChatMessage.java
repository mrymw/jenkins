package com.mrymw.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "chat_message")
public class ChatMessage {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates ID
    private Long id;

    @Column
    private String sender;
    @Column
    private String receiver;
    @Column
    private MessageType type;

    /*@Column(nullable = false)
    private boolean isEdited;*/

    /*@Column
    private boolean isRead;*/;

    @Column(nullable = false, updatable = false)
    private LocalDateTime sendAt;

    @Column(nullable = false)
    private String content;


    @PrePersist
    protected void onCreate() {
        this.sendAt = LocalDateTime.now();
    }
}
