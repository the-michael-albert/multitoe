package org.michaelalbert.utils;


public class GameConfig {
    public static int getGamePort() {
        return ReadOnlyPropertiesMap.INSTANCE.containsKey("game.port") ? Integer.parseInt(ReadOnlyPropertiesMap.INSTANCE.get("game.port")) : 8888;
    }

    public static String getGameHost() {
        return ReadOnlyPropertiesMap.INSTANCE.containsKey("game.host") ? ReadOnlyPropertiesMap.INSTANCE.get("game.host") : "localhost";
    }
}
