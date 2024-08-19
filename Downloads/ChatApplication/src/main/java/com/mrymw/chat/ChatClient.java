package com.mrymw.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import java.util.function.Consumer;

public class ChatClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Consumer<String> onMessageReceived;
    public ChatClient(String serverAddress, int severPort, Consumer<String> onMessageReceived) throws IOException {
        this.socket = new Socket(serverAddress, severPort);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.onMessageReceived = onMessageReceived;
    }
    public void sendMessage(String msg) {
        out.println(msg);
    }
    public void sendPrivateMessage(String clientName, String privateMessage) {
        String formattedMessage = "/msg " + clientName + " " + privateMessage;
        sendMessage(formattedMessage);
    }
    public void startClient() {
        new Thread(() -> {
            try {
                String line;
                while ((line = in.readLine()) != null){
                    onMessageReceived.accept(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
