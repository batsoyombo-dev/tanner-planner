package com.tanner.planner.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static String URL = "jdbc:mysql://52.141.57.195:3306/tanner";
    public static String USERNAME = "supersuper";
    public static String PASSWORD = "Super123@";

    public static Connection getConnection() throws SQLException {
         return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

}
