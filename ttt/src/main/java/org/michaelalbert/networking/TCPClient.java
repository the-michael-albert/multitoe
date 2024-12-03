package org.michaelalbert.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public abstract class TCPClient implements ClientInterface{
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    @Override
    public void start(String host, int port) {
        try {
            socket = new Socket(host, port);
            System.out.println("CONNECTED!" + socket);

            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            new Thread(() -> { // Start a new thread to read messages
                while (true) {
                    try {
                        int length = in.readInt();
                        if (length > 0) {
                            byte[] message = new byte[length];
                            in.readFully(message, 0, message.length);
                            onMessage(message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }).start();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(byte[] message) {
        try {
            out.writeInt(message.length);
            out.write(message);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}