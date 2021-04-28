package com.tanner.planner.utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerHandler {

    private static SimpleFormatter formatter = new SimpleFormatter();

    public static void error(String msg, String name) {
        try {
            FileHandler fileHandler = new FileHandler("error.log", true);
            Logger logger = Logger.getLogger(name);
            logger.addHandler(fileHandler);
            fileHandler.setFormatter(formatter);
            logger.severe(msg);
            fileHandler.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void info(String msg, String name) {
        try {
            FileHandler fileHandler = new FileHandler("error.log", true);
            Logger logger = Logger.getLogger(name);
            logger.addHandler(fileHandler);
            fileHandler.setFormatter(formatter);
            logger.info(msg);
            fileHandler.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
