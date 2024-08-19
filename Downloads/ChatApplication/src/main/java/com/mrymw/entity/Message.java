package com.mrymw.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter @Setter
@Table(name = "message")
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String sender;
    @Column
    private String recipient;
    @Column
    private String message;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;


}
