package org.michaelalbert.game.reqres;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class InvalidMoveRequest {
    public static final String ACTION = "invalidmove";
    private String message;

    public InvalidMoveRequest(String message) {
        this.message = message;
    }

    public static InvalidMoveRequest fromString(String message) {
        String[] parts = message.split("#");
        return new InvalidMoveRequest(parts[1]);
    }

    @Override
    public String toString() {
        return ACTION + "#" + message;
    }
}
