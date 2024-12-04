package org.michaelalbert.utils;


import java.io.File;
import java.util.logging.FileHandler;

public class GameConfig {
    public static int getGamePort() {
        return ReadOnlyPropertiesMap.INSTANCE.containsKey("game.port") ? Integer.parseInt(ReadOnlyPropertiesMap.INSTANCE.get("game.port")) : 8888;
    }

    public static String getGameHost() {
        String host = ReadOnlyPropertiesMap.INSTANCE.containsKey("game.host") ? ReadOnlyPropertiesMap.INSTANCE.get("game.host") : "localhost";
        Slug.log("Game host: " + host);
        return host;
    }

    public static File getLogFile() {
        String filePath = ReadOnlyPropertiesMap.INSTANCE.get("game.logFile");
        //create file and parent directories if they don't exist
        File file = new File(filePath);
        if (!file.exists()) {
            if (!createFileParents(file)) {
                throw new RuntimeException("Failed to create log file parent directories: " + file.getAbsolutePath());
            }
        }
        return file;
    }

    static boolean createFileParents(File file) {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            return parent.mkdirs();
        }
        return true;
    }

    private static class NetworkUtils {

        public static String resolveIP(String host) {
            try {
                return java.net.InetAddress.getByName(host).getHostAddress();
            } catch (java.net.UnknownHostException e) {
                return "localhost";
            }
        }

    }
}
