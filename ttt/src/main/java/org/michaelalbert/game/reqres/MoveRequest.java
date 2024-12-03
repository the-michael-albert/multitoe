package org.michaelalbert.game.reqres;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MoveRequest {
    public static final String ACTION = "move";
    private int x;
    private int y;
    private String gameId;
    private char player;

    public MoveRequest(int x, int y, String gameId, char player) {
        this.x = x;
        this.y = y;
        this.gameId = gameId;
        this.player = player;
    }

    public static MoveRequest fromString(String message) {
        String[] parts = message.split("#");
        String[] args = parts[1].split(",");
        return new MoveRequest(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2], args[3].charAt(0));
    }

    @Override
    public String toString() {
        return ACTION + "#" + x + "," + y + "," + gameId + "," + player;
    }

}


