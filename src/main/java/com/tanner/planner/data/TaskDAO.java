package com.tanner.planner.data;


import com.tanner.planner.models.Bucket;
import com.tanner.planner.models.Panel;
import com.tanner.planner.models.Task;
import com.tanner.planner.utils.Inflatable;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * TaskDAO manages task model's database access
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
public class TaskDAO {

    public static String
            TABLE_NAME = "task",
            COLUMN_ID = "id",
            COLUMN_BUCKET_ID = "bucket_id",
            COLUMN_TITLE = "title",
            COLUMN_DESCRIPTION = "description",
            COLUMN_STATE = "state",
            COLUMN_DATE = "date";
    private Alert alert = new Alert(Alert.AlertType.NONE);

    /**
     * Adds task to the database
     * @param task object of Task class
     */
    public void addTask(Task task) {
        String query = "insert into task(" + COLUMN_ID + ", " + COLUMN_BUCKET_ID + ", " + COLUMN_TITLE  +", " + COLUMN_DESCRIPTION + ", " + COLUMN_STATE +", " + COLUMN_DATE +")" +
                "value ('" + task.getId() + "', '" + task.getBucket_id() + "', '" + task.getTitle()  + "', '" + task.getDescription()+ "', '" + task.getState()+ "', '" + task.getDate()+"');";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText(String.valueOf(e));
            alert.show();
        }
    }

    /**
     * Deletes the task from database
     * @param task A task object to be deleted
     */
     public void deleteTask(Task task) {
        String query = "delete from task where id = '" + task.getId() +"' ";
        try (Connection con = DBConnection.getConnection();
                 PreparedStatement statement = con.prepareStatement(query);) {
            statement.execute();
        } catch (SQLException e) {
             alert.setAlertType(Alert.AlertType.ERROR);
             alert.setContentText(String.valueOf(e));
             alert.show();
         }
     }

    /**
     * Updates task and writes to the database
     * @param task object of Task class
     * @param title String type of variable that indicates new title of task
     * @param description String type of variable that indicates new description of task
     * @param state String type of variable that indicates new state of task
     */
    public void updateTask(Task task, String title, String description, String state) {
        String query = "update task set title = '"+title +"', description = '"+ description+"', state = '"+ state+"' where id = '"+task.getId() +"'";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText(String.valueOf(e));
            alert.show();
        }
    }

    /**
     * Fetches certain buckets's tasks from database
     * @param bucket A bucket to be fetched
     * @return List of tasks
     */
    public ObservableList<Task> getTasks(Bucket bucket) {
        ObservableList<Task> list = FXCollections.observableArrayList();
        String bucketId = bucket.getID();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM task WHERE bucket_id = '"+ bucketId+"' ");
             ResultSet rs = ps.executeQuery();){
            while(rs.next()){
                list.add(new Task(rs.getString("id"), rs.getString("bucket_id"), rs.getString("title"), rs.getString("description"), rs.getString("state"), rs.getString("date")));
            }
        }
        catch(SQLException e){
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText(String.valueOf(e));
            alert.show();
        }
        return list;

    }

    /**
     * Gets tasks of the panel that are past due
     * @param panel object of Panel class
     * @return Past due task list
     */
    public ObservableList<Task> getNotification(Panel panel){


        ObservableList<Task> list = FXCollections.observableArrayList();
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM task WHERE state = 'pd' and bucket_id = ANY(SELECT id from bucket WHERE panel_id = '"+panel.getId()+"') ");
            ResultSet rs = ps.executeQuery();){

            while(rs.next()){
                list.add(new Task(rs.getString("id"), rs.getString("bucket_id"), rs.getString("title"), rs.getString("description"), rs.getString("state"), rs.getString("date")));
            }
        }
        catch(Exception e){
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText(String.valueOf(e));
            alert.show();
        }
        return list;

    }


}
