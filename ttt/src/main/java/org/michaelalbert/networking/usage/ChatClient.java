package org.michaelalbert.networking.usage;

import org.michaelalbert.networking.TCPClient;

import java.util.Scanner;

public class ChatClient extends TCPClient {
    private String username;

    public ChatClient(String username) {
        this.username = username;
    }

    public static void main(String[] args) {

        // Get username from scanner
        Scanner usernameScanner = new Scanner(System.in);
        System.out.print("username > ");
        String username = usernameScanner.nextLine();

        ChatClient client = new ChatClient(username);
        
        System.out.println("Starting chat client...");
        client.start("192.168.1.28", 8888);

        // Start a thread to read user input and send messages
        Scanner scanner = new Scanner(System.in);
        System.out.println("send messages ('/q' to exit):");
        
        while (true) {
            String message = scanner.nextLine();
            if (message.equalsIgnoreCase("/q")) {
                System.exit(0);
            }
            // Format message with username
            String formattedMessage = username + ": " + message;
            client.sendMessage(formattedMessage.getBytes());
        }
    }

    @Override
    public void onMessage(byte[] message) {
        // Print received messages to console
        String receivedMessage = new String(message);
        // Don't print our own messages again
        if (!receivedMessage.startsWith(username + ": ")) {
            System.out.println(receivedMessage);
        }
    }
}