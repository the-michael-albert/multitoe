package org.michaelalbert.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Slug {
    static final File logFile = GameConfig.getLogFile();

    static int logCount = 0;

    static void logCleanup() {
        logCount++;
        if (logCount > 100) {
            deleteExcessLogs();
            logCount = 0;
        }
    }

    static void deleteExcessLogs() {
        File logDir = logFile.getParentFile();
        File[] logs = logDir.listFiles();
        // delete all files that are not the current log file
        if (logs == null) {
            return;
        }
        for (File log : logs) {
            if (!log.equals(logFile)) {
                log.delete();
            }
        }
    }

    public static void log(String message) {
        try {
            FileHandler fileHandler = new FileHandler(logFile.getAbsolutePath(), true);
            fileHandler.setFormatter(new SimpleFormatter());
            Logger.getLogger("org.michaelalbert").addHandler(fileHandler);
            Logger.getLogger("org.michaelalbert").log(Level.INFO, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logCleanup();
    }

    public static void log(String id, String message) {
        try {
            FileHandler fileHandler = new FileHandler(logFile.getAbsolutePath(), true);
            fileHandler.setFormatter(new SimpleFormatter());
            Logger.getLogger("org.michaelalbert").addHandler(fileHandler);
            Logger.getLogger("org.michaelalbert").log(Level.INFO, id + ": " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logCleanup();
    }

    public static void log(Exception e) {
        try {
            FileHandler fileHandler = new FileHandler(logFile.getAbsolutePath(), true);
            fileHandler.setFormatter(new SimpleFormatter());
            Logger.getLogger("org.michaelalbert").addHandler(fileHandler);
            Logger.getLogger("org.michaelalbert").log(Level.SEVERE, e.getMessage(), e);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        logCleanup();
    }

    public static void logError(String message) {
        try {
            FileHandler fileHandler = new FileHandler(logFile.getAbsolutePath(), true);
            fileHandler.setFormatter(new SimpleFormatter());
            Logger.getLogger("org.michaelalbert").addHandler(fileHandler);
            Logger.getLogger("org.michaelalbert").log(Level.SEVERE, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logCleanup();
    }

    public static void logWarning(String message) {
        try {
            FileHandler fileHandler = new FileHandler(logFile.getAbsolutePath(), true);
            fileHandler.setFormatter(new SimpleFormatter());
            Logger.getLogger("org.michaelalbert").addHandler(fileHandler);
            Logger.getLogger("org.michaelalbert").log(Level.WARNING, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logCleanup();
    }

}
