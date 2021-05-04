package com.tanner.planner.data;

import com.tanner.planner.utils.LoggerHandler;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection {

//    public static String URL = "jdbc:mysql://52.141.57.195:3306/tanner";
//    public static String USERNAME = "supersuper";
//    public static String PASSWORD = "Super123@";

    public static String URL = "jdbc:mysql://52.141.57.195:3306/tanner";
    public static String USERNAME = "supersuper";
    public static String PASSWORD = "Super123@";


    public static Connection getConnection() throws SQLException {
         return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static boolean executeQuery(String query, String name) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.execute();
            return true;
        } catch (SQLException e) {
            LoggerHandler.error(e.getMessage(), name);
            return false;
        }
    }

}
