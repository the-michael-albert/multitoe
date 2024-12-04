package org.michaelalbert.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ReadOnlyPropertiesMap {

    public static final String APPLICATION_PROPERTIES = "application.properties";

    public static final ReadOnlyPropertiesMap INSTANCE;

    static {
        try {
            //use classloader to load properties file from resources
            INSTANCE = new ReadOnlyPropertiesMap(APPLICATION_PROPERTIES);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final Map<String, String> propertiesMap;

    public ReadOnlyPropertiesMap(String propertiesFileName) throws IOException {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(propertiesFileName)) {
            if (input == null) {
                throw new IOException("Properties file not found: " + propertiesFileName);
            }
            properties.load(input);
        }

        // Convert Properties to a Map and wrap in an unmodifiable map
        Map<String, String> tempMap = new HashMap<>();
        for (String key : properties.stringPropertyNames()) {
            tempMap.put(key, properties.getProperty(key));
        }
        this.propertiesMap = Collections.unmodifiableMap(tempMap);
    }

    public String get(String key) {
        return propertiesMap.get(key);
    }

    public boolean containsKey(String key) {
        return propertiesMap.containsKey(key);
    }

    public Map<String, String> asMap() {
        return propertiesMap;
    }

    public static void main(String[] args) {
        try {
            ReadOnlyPropertiesMap propertiesMap = new ReadOnlyPropertiesMap("application.properties");
            System.out.println("Property value: " + propertiesMap.get("example.key"));
            System.out.println("All properties: " + propertiesMap.asMap());
        } catch (IOException e) {
            System.err.println("Error reading properties file: " + e.getMessage());
        }
    }
}
