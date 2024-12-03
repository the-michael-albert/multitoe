package org.michaelalbert.game.reqres;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class GameFinishedRequest {
    public static final String ACTION = "gamefinished";
    private String winner;

    public GameFinishedRequest(String winner) {
        this.winner = winner;
    }

    public static GameFinishedRequest fromString(String message) {
        String[] parts = message.split("#");
        return new GameFinishedRequest(parts[1]);
    }

    @Override
    public String toString() {
        return ACTION + "#" + winner;
    }
}
