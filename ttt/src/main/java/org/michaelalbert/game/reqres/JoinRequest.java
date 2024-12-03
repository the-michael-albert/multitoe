package org.michaelalbert.game.reqres;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class JoinRequest {
    public static final String ACTION = "join";


    private String gameId;

    public static JoinRequest fromString(String message) {
        String[] parts = message.split("#");
        return JoinRequest.builder()
                .gameId(parts[1])
                .build();
    }

    @Override
    public String toString() {
        return ACTION + "#" + gameId;
    }
}
