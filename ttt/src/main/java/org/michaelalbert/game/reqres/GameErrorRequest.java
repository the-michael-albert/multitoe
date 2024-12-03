package org.michaelalbert.game.reqres;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class GameErrorRequest {
    public static final String ACTION = "gameerror";
    private String message;

    public GameErrorRequest(String message) {
        this.message = message;
    }

    public static GameErrorRequest fromString(String message) {
        String[] parts = message.split("#");
        return new GameErrorRequest(parts[1]);
    }

    @Override
    public String toString() {
        return ACTION + "#" + message;
    }
}
