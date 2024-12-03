package org.michaelalbert.networking;

import lombok.Getter;
import lombok.Setter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final ServerInterface serverInterface;

    private DataInputStream in;
    private DataOutputStream out;

    @Getter
    @Setter
    private String id;

    public ClientHandler(Socket clientSocket, ServerInterface serverInterface) {
        this.clientSocket = clientSocket;
        this.serverInterface = serverInterface;
    }

    @Override
    public void run() {
        try {
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (id!=null) {
            try {
                sendMessage(id.getBytes("Latin1"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

        while (true) {
            try {
                int length = in.readInt();
                if (length > 0) {
                    byte[] message = new byte[length];
                    in.readFully(message, 0, message.length);
                    serverInterface.onMessage(message, this);
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void sendMessage(String message) {
        try {
            sendMessage(message.getBytes("Latin1"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

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
