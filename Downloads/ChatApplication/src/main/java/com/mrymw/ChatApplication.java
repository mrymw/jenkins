package com.mrymw;

import com.mrymw.chat.ChatServer;
import com.mrymw.frontend.ChatClientGUI;
import com.mrymw.repository.MessageRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.swing.*;
import java.io.IOException;

@SpringBootApplication
public class ChatApplication {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ChatApplication.class, args);
	}
}
