package org.michaelalbert.networking;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public abstract class TCPServer implements ServerInterface {

    private final List<ClientHandler> clients = new Vector<>(); // Vector is thread safe
    private final Map<String, ClientHandler> users = new TreeMap<>(); // TreeMap is thread safe
    boolean running = true;

    protected abstract void onConnection(ClientHandler clientHandler);

    private static String genUUID() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void start(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (running) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket, this);

                // Generate a UUID for the client
                final String uuid = genUUID();
                clientHandler.setId(uuid);
                users.put(uuid, clientHandler);
                clients.add(clientHandler);

                // Call abstract method to allow for custom behavior on connection
                onConnection(clientHandler);

                System.out.println("=====================================");
                System.out.println("Client connected: " + socket);
                System.out.println("UUID: " + uuid);
                System.out.println("Clients: " + clients.size());
                System.out.println("=====================================");

                // Start a new thread for each client
                new Thread(clientHandler).start(); // Start a new thread for each client
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void broadcast(byte[] message) {
        synchronized (clients) { // Synchronize to avoid concurrent modification exception
            for (ClientHandler client : clients) {
                client.sendMessage(message);
            }
        }
    }



    @Override
    public void removeClient(ClientHandler clientHandler) {
        final String uuid = clientHandler.getId();
        synchronized (clients) {
            clients.remove(clientHandler);
        }
        synchronized (users) {
            users.remove(uuid);
        }
    }
}
