package org.michaelalbert.game.reqres;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Builder
@Getter
@Setter
public class GameListInfoRequest {
    public static final String ACTION = "gamelistinfo";

    public ArrayList<GameTuple> gameList;

    public GameListInfoRequest(ArrayList<GameTuple> gameList) {
        this.gameList = gameList;
    }

    public static GameListInfoRequest fromString(String message) {
        String[] parts = message.split("#");
        String[] gameListParts = parts[1].split(",");
        ArrayList<GameTuple> gameList = new ArrayList<>();
        for (String game : gameListParts) {
            String[] gameParts = game.split("&");
            gameList.add(new GameTuple(gameParts[0], Integer.parseInt(gameParts[1])));
        }
        return new GameListInfoRequest(gameList);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ACTION).append("#");
        for (int i = 0; i < gameList.size(); i++) {
            sb.append(gameList.get(i).toString());
            if (i < gameList.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}

