package org.michaelalbert.networking;

public interface ClientInterface {

    void start(String host, int port);

    void sendMessage(byte[] message);

    void onMessage(byte[] message);
}
