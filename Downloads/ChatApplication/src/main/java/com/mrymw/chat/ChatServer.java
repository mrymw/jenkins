package com.mrymw.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatServer {
    private static List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2000);
        System.out.println("Server started. Waiting for clients...");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket);
            ClientHandler clientThread = new ClientHandler(clientSocket, clients);
            clients.add(clientThread);
            new Thread(clientThread).start();
        }
    }
}
class ClientHandler implements Runnable {
    private Socket clientSocket;
    private List<ClientHandler> clients;
    private PrintWriter out;
    private BufferedReader in;
    private String clientName;
    public ClientHandler (Socket socket, List<ClientHandler> clients) throws IOException {
        this.clientSocket = socket;
        this.clients = clients;
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.clientName = in.readLine();
        broadcastMessage(clientName + " has joined the chat.");
    }
    @Override
    public void run() {
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.startsWith("/msg")) {
                    String[] parts = inputLine.split(" ", 3);
                    if (parts.length==3) {
                        String targetClientName = parts[1];
                        String privateMessage = parts[2];
                        sendPrivateMessage(targetClientName, privateMessage);
                    } else {
                        out.println("Invalid private message format. Use /msg <username> <message>");
                    }
                } else {
                    broadcastMessage(clientName + ": " + inputLine);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            try {
                in.close();
                out.close();
                clientSocket.close();
                clients.remove(this);
                broadcastMessage(clientName + " has left the chat.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void sendPrivateMessage(String targetClientName, String privateMessage) {
        boolean userFound = false;
        for (ClientHandler client : clients) {
            if (client.clientName.equals(targetClientName)) {
                client.out.println("[Private] " + clientName + ": " + privateMessage);
                userFound = true;
                break;
            }
        }
        if (!userFound) {
            out.println("User " + targetClientName + " not found.");
        }
    }
    private void broadcastMessage(String message) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if(client!= this) {
                    client.out.println(message);
                }
            }
        }
    }
}
