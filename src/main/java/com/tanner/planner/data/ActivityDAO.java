package com.tanner.planner.data;

import com.tanner.planner.controllers.HomeController;
import com.tanner.planner.models.Activity;
import com.tanner.planner.models.Panel;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ActivityDAO manages activity models database access
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
public class ActivityDAO {
    /**
     * Gets recent activities from table
     * @return Activity List
     */
    public List<Activity> getActivities() {
        List<Activity> activities = new ArrayList<>();
        String query = "select * from activity, panel " +
                "where panel.user_id = " + HomeController.getUser().getId() + " and panel_id = panel.id limit 10;";
        try (Connection con = DBConnection.getConnection();
             Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(query);) {
            while(resultSet.next())
                activities.add(new Activity(
                        resultSet.getInt(1),
                        resultSet.getString(3),
                        new Panel(
                                resultSet.getString(4),
                                resultSet.getInt(5),
                                resultSet.getString(6),
                                resultSet.getString(7),
                                resultSet.getString(8),
                                resultSet.getString(9)
                        )
                ));
            return activities;
        } catch (SQLException e) {
            return activities;
        }
    }

    /**
     * Adds new activity to the database
     * @param activity An acitivty object to be added to the database
     * @return If execution was successful returns true and false otherwise
     */
    public boolean addActivity(Activity activity) {
        String query = "insert into activity (panel_id, action)" +
                "value ('" + activity.getPanel().getId() + "', '" + activity.getAction() + "')";
        try (Connection con = DBConnection.getConnection();
             Statement statement = con.createStatement();) {
            statement.execute(query);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
