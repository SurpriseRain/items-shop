package com.jlsoft.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesReader {

    private String fieldName = System.getProperty("user.dir") + System.getProperty("file.separator") + "v9.config";
    private static final Properties properties = new Properties();
    private static PropertiesReader reader = null;

    private PropertiesReader() throws Exception { 
        properties.load(new FileInputStream(fieldName));
    }

    public static PropertiesReader getInstance() {
        if (reader == null) {
            try {
                reader = new PropertiesReader();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return reader;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
