package org.michaelalbert.game.reqres;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TurnNotificationRequest {
    public static final String ACTION = "turnnotification";
    private char player;

    public TurnNotificationRequest(char player) {
        this.player = player;
    }

    public static TurnNotificationRequest fromString(String message) {
        String[] parts = message.split("#");
        return new TurnNotificationRequest(parts[1].charAt(0));
    }

    @Override
    public String toString() {
        return ACTION + "#" + player;
    }
}
