package org.michaelalbert.game;

import org.michaelalbert.networking.ClientHandler;

public abstract class ServerRequestHandler {
    public abstract void onMessage(String action, String data, String rawMessage, ClientHandler callback);
}
