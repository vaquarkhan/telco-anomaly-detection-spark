package com.mapr.cell.spark;

import com.google.common.io.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Config {
    public static final String CONFIG_CONF = "config.conf";

    private Properties properties = new Properties();

    private static Config instance;
    private Config() {
        try (InputStream props = Resources.getResource(CONFIG_CONF).openStream()) {
            properties.load(props);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized Config getConfig() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public HashMap<String, String> getPrefixedMap(String prefix) {
        HashMap<String, String> props = new HashMap<>();
        for (final String name: properties.stringPropertyNames()) {
            if (name.startsWith(prefix)) {
                props.put(name.substring(prefix.length()), properties.getProperty(name));
            }
        }
        return props;
    }

    public Properties getPrefixedProps(String prefix) {
        Properties props = new Properties();
        for (final String name: properties.stringPropertyNames()) {
            if (name.startsWith(prefix)) {
                props.put(name.substring(prefix.length()), properties.getProperty(name));
            }
        }
        return props;
    }

    public Properties getProperties() {
        return properties;
    }
}