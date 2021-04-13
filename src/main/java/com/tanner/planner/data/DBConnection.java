package com.tanner.planner.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

//    public static String URL = "jdbc:mysql://52.141.57.195:3306/tanner";
//    public static String USERNAME = "supersuper";
//    public static String PASSWORD = "Super123@";

    public static String URL = "jdbc:mysql://localhost:3306/tanner";
    public static String USERNAME = "super";
    public static String PASSWORD = "";


    public static Connection getConnection() throws SQLException {
         return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

}
