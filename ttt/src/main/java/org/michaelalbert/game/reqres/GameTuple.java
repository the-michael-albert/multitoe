package org.michaelalbert.game.reqres;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameTuple {
    public final String name;
    public final int userCount;

    public GameTuple(String name, int userCount) {
        this.name = name;
        this.userCount = userCount;
    }

    // fromString and toString methods are missing

    @Override
    public String toString() {
        return name + "&" + userCount;
    }

    public static GameTuple fromString(String message) {
        String[] parts = message.split("&");
        return new GameTuple(parts[0], Integer.parseInt(parts[1]));
    }
}
