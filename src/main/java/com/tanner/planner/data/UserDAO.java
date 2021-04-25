package com.tanner.planner.data;

import com.tanner.planner.models.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * UserDAO manages activity models database access
 *
 * #########     #      ##     ##  ##     ##  ########  #######
 *    ##        # #     ## #   ##  ## #   ##  ##        ##    ##
 *    ##       #   #    ##  #  ##  ##  #  ##  ########  #######
 *    ##      #######   ##   # ##  ##   # ##  ##        ## ##
 *    ##     #       #  ##     ##  ##     ##  ########  ##   ##
 *
 * @author Tanner Team
 * @version 1.0
 * @since 2021/05/07
 * @link https://github.com/batsoyombo-dev/tanner-planner
 */
public class UserDAO {

    public String
            TABLE_NAME = "user",
            COLUMN_USERNAME = "username",
            COLUMN_PASSWORD = "password";

    /**
     * Registers new user
     * @param user A user object to be registered
     * @return If registration is sucessful returns true otherwise false
     */
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

    /**
     * Checks if the user exist in the user table
     * @param user object of User class
     * @return boolean type variable that indicates if the user exists
     */
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
