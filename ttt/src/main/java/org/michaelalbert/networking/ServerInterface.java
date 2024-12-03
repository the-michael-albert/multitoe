package org.michaelalbert.networking;

public interface ServerInterface {

    void start(int port);

    void broadcast(byte[] message);

    void onMessage(byte[] message, ClientHandler clientHandler);

    void removeClient(ClientHandler clientHandler);

}
