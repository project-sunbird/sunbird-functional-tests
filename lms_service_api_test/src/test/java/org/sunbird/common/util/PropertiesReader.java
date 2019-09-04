package org.sunbird.common.util;


import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class PropertiesReader {

    private final String[] fileName = {
           "citrus-application.properties"
    };
    private final Properties configProp = new Properties();
    public final Map<String, Float> attributePercentageMap = new ConcurrentHashMap<>();
    private static PropertiesReader propertiesReader = null;

    /** private default constructor */
    private PropertiesReader() {
        for (String file : fileName) {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(file);
            try {
                configProp.load(in);
            } catch (IOException e) {
                System.out.println("Error in properties cache");
            }
        }
        loadWeighted();
    }

    public static PropertiesReader getInstance() {

        // change the lazy holder implementation to simple singleton implementation ...
        if (null == propertiesReader) {
            synchronized (PropertiesReader.class) {
                if (null == propertiesReader) {
                    propertiesReader = new PropertiesReader();
                }
            }
        }

        return propertiesReader;
    }

    public void saveConfigProperty(String key, String value) {
        configProp.setProperty(key, value);
    }

    public String getProperty(String key) {
        String value = System.getenv(key);
        if (StringUtils.isNotBlank(value)) return value;
        return configProp.getProperty(key) != null ? configProp.getProperty(key) : key;
    }

    private void loadWeighted() {
        String key = configProp.getProperty("user.profile.attribute");
        String value = configProp.getProperty("user.profile.weighted");
        if (StringUtils.isBlank(key)) {
            System.out.println("Profile completeness value is not set");
        } else {
            String keys[] = key.split(",");
            String values[] = value.split(",");
            if (keys.length == value.length()) {
                // then take the value from user
                System.out.println("weighted value is provided by user.");
                for (int i = 0; i < keys.length; i++)
                    attributePercentageMap.put(keys[i], new Float(values[i]));
            } else {
                // equally divide all the provided field.
                System.out.println("weighted value is not provided  by user.");
                float perc = (float) 100.0 / keys.length;
                for (int i = 0; i < keys.length; i++) attributePercentageMap.put(keys[i], perc);
            }
        }
    }

    /**
     * Method to read value from resource file .
     *
     * @param key
     * @return
     */
    public String readProperty(String key) {
        String value = System.getenv(key);
        if (StringUtils.isNotBlank(value)) return value;
        return configProp.getProperty(key);
    }
}
