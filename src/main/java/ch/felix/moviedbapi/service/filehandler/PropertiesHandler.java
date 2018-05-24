package ch.felix.moviedbapi.service.filehandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @author Felix
 * @project Menthe2.0
 */
public class PropertiesHandler {
    public Properties properties;
    private String path;

    /**
     * @param file property file to be handled
     */
    public PropertiesHandler(File file) {
        this.path = file.getPath();
        try {
            FileHandler fileHandler = new FileHandler(path);
            FileInputStream fileInput = new FileInputStream(fileHandler.getFile());
            properties = new Properties();
            properties.load(fileInput);
            fileInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PropertiesHandler(FileHandler fileHandler) {
        this.path = fileHandler.getPath();
        try {
            FileInputStream fileInput = new FileInputStream(fileHandler.getFile());
            properties = new Properties();
            properties.load(fileInput);
            fileInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param path to the file to be handled
     */
    public PropertiesHandler(String path) {
        this.path = path;
        try {
            FileHandler fileHandler = new FileHandler(path);
            FileInputStream fileInput = new FileInputStream(fileHandler.getFile());
            properties = new Properties();
            properties.load(fileInput);
            fileInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return all key in File
     */
    public ArrayList<String> getKeys() {
        FileHandler file = new FileHandler(path);
        ArrayList<String> keys = new ArrayList<String>();
        int counter = 0;
        for (String line : file.readRows()) {
            if (!line.startsWith("#")) {
                keys.add(line.split("=")[0]);
            }
            counter++;
        }
        return keys;
    }

    /**
     * @param key to get property
     * @return property of key
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * @param key   to be changed
     * @param value new value of property
     */
    public void setValue(String key, String value) {
        try {
            FileOutputStream output = new FileOutputStream(path);
            properties.setProperty(key, value);
            properties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}