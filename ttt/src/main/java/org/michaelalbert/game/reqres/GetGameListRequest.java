package org.michaelalbert.game.reqres;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class GetGameListRequest {
    public static final String ACTION = "getgamelist";

    public GetGameListRequest() {
    }

    public static GetGameListRequest fromString(String message) {
        return new GetGameListRequest();
    }

    @Override
    public String toString() {
        return ACTION + "#na";
    }

}
