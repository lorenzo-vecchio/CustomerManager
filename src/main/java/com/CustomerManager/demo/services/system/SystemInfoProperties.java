package com.CustomerManager.demo.services.system;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

@Service
public class SystemInfoProperties {

    private final String propertiesFilePath = "src/main/resources/info/system_info.properties";
    private Properties properties;

    public SystemInfoProperties() {
        properties = new Properties();
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream input = new FileInputStream(propertiesFilePath)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Unable to load properties file: " + e.getMessage());
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
        saveProperties();
    }

    private void saveProperties() {
        try (OutputStream output = new FileOutputStream(propertiesFilePath)) {
            properties.store(output, null);
        } catch (IOException e) {
            System.err.println("Unable to save properties file: " + e.getMessage());
        }
    }

    public boolean getBooleanProperty(String key) {
        String value = properties.getProperty(key);
        return value != null && Boolean.parseBoolean(value);
    }

    public void setBooleanProperty(String key, boolean value) {
        properties.setProperty(key, Boolean.toString(value));
        saveProperties();
    }
}
