package org.michaelalbert.networking.usage;

import org.michaelalbert.networking.ClientHandler;
import org.michaelalbert.networking.TCPServer;
import org.michaelalbert.utils.GameConfig;

public class ChatServer extends TCPServer {
    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        System.out.println("Starting chat server on port 8888...");
        server.start(GameConfig.getGamePort());
    }

    @Override
    public void onMessage(byte[] message, ClientHandler clientHandler) {
        // When we receive a message, broadcast it to all clients
        String receivedMessage = new String(message);
        System.out.println("Received message: " + receivedMessage);
        broadcast(message);
    }

    @Override
    protected void onConnection(ClientHandler clientHandler) {

    }
}