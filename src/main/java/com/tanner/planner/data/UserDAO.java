package com.tanner.planner.data;

import com.tanner.planner.models.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {

    public String
            TABLE_NAME = "user",
            COLUMN_USERNAME = "username",
            COLUMN_PASSWORD = "password";

    public boolean register(User user) {
        String query = "insert into " + TABLE_NAME + " (" + COLUMN_USERNAME + ", " + COLUMN_PASSWORD + ")" +
                "value ('" + user.getUsername() + "', '" + user.getPassword() + "');";
        try (Connection con = DBConnection.getConnection(); Statement statement = con.createStatement();) {
            statement.execute(query);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean authenticate(User user) {
        String query = "select * from " + TABLE_NAME + " where " + COLUMN_USERNAME + "='" + user.getUsername() + "'";
        try (Connection con = DBConnection.getConnection();
             Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(query);) {
            if (resultSet.next() && user.getPassword().equals(resultSet.getString(3))) {
                user.setId(resultSet.getInt(1));
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
