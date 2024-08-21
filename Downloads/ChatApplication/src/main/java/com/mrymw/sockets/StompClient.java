package com.mrymw.sockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class StompClient {


    private static String URL = "ws://localhost:9090/ws";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new StringMessageConverter());

        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        StompSession session = stompClient.connect(URL, sessionHandler).get();

        // Sending a test message
        session.send("/app/sendMessage", "Hello, this is a test message!");

        // Keep the session alive to listen for messages
        new Scanner(System.in).nextLine();
    }

    private static class MyStompSessionHandler extends StompSessionHandlerAdapter {

        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            System.out.println("Connected to the WebSocket server");

            // Subscribe to a topic
            session.subscribe("/topic/messages", this);

            // Send a message to a specific endpoint
            session.send("/app/sendMessage", "Hello from the client!");
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            System.out.println("Received message: " + payload);
        }

        @Override
        public Type getPayloadType(StompHeaders headers) {
            return String.class;
        }

        @Override
        public void handleTransportError(StompSession session, Throwable exception) {
            System.err.println("Transport error: " + exception.getMessage());
        }
    }
}
