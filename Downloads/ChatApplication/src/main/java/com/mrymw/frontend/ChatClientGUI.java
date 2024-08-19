package com.mrymw.frontend;

import com.mrymw.chat.ChatClient;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class ChatClientGUI extends JFrame {
    private JTextArea messageArea;
    private JTextField textField;
    private JTextField recipientField;
    private JButton privateMessageButton;
    private ChatClient client;
    private JButton exitButton;
    private boolean isPrivateMessage = false;
    public ChatClientGUI() {
        super("Chat Application");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Color backgroundColor = new Color(240, 240, 240);
        Color buttonColor = new Color(75,75, 75);
        Color textColor = new Color(50, 50, 50);
        Font textFont = new Font("Arial", Font.PLAIN, 14);
        Font buttonFont = new Font("Arial", Font.BOLD, 12);
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setBackground(backgroundColor);
        messageArea.setForeground(textColor);
        messageArea.setFont(textFont);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        add(scrollPane, BorderLayout.CENTER);

        String name = JOptionPane.showInputDialog(this, "Enter your name: ", "Name Entry", JOptionPane.PLAIN_MESSAGE);
        this.setTitle("Chat Application - " + name);

        textField = new JTextField();
        textField.setFont(textFont);
        textField.setForeground(textColor);
        textField.setBackground(backgroundColor);
        textField.addActionListener(e -> {
            String message = "[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "]" + name + ": " + textField.getText();
            if(isPrivateMessage) {
                String recipient = recipientField.getText().trim();
                if (!recipient.isEmpty()) {
                    client.sendPrivateMessage(recipient, message);
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter a recipient for the private message.", "Error",JOptionPane.ERROR_MESSAGE);
                }
            } else {
                client.sendMessage(message);
            }
            textField.setText("");
        });

        recipientField = new JTextField("Recipient Name");
        recipientField.setFont(textFont);
        recipientField.setForeground(textColor);
        recipientField.setBackground(backgroundColor);

        privateMessageButton = new JButton("Private Message");
        privateMessageButton.setFont(buttonFont);
        privateMessageButton.setBackground(buttonColor);
        privateMessageButton.setForeground(textColor);
        privateMessageButton.addActionListener(e -> {
            isPrivateMessage =!isPrivateMessage;
            if (isPrivateMessage) {
                privateMessageButton.setText("Private Message: ON");
                recipientField.setEnabled(true);
            } else {
                privateMessageButton.setText("Private Message: OFF");
                recipientField.setEnabled(false);
            }
        });

        exitButton = new JButton("Exit");
        exitButton.setFont(buttonFont);
        exitButton.setBackground(buttonColor);
        exitButton.setForeground(textColor);
        exitButton.addActionListener(e -> {
            String departureMessage = name + " has left the chat.";
            client.sendMessage(departureMessage);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
            System.exit(0);
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(backgroundColor);
        bottomPanel.add(recipientField, BorderLayout.NORTH);
        bottomPanel.add(textField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(privateMessageButton, BorderLayout.CENTER);
        buttonPanel.add(exitButton, BorderLayout.EAST);
        buttonPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
        recipientField.setEnabled(false);

        try {
            this.client = new ChatClient("127.0.0.1", 2000, this::onMessageReceived);
            client.startClient();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the server.", "Connection error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);

        }
    }
    private void onMessageReceived(String message) {
        SwingUtilities.invokeLater(() -> {
            messageArea.append(message + "\n");
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ChatClientGUI().setVisible(true);
        });
    }

}
