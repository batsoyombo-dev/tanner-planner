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
    public void deleteTask(Task task) {
        String query = "delete from task where id = '" + task.getId() +"' ";
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

    public ObservableList<Task> getTasks(Bucket bucket) throws SQLException {

        Connection conn = DBConnection.getConnection();
        ObservableList<Task> list = FXCollections.observableArrayList();
        String bucketId = bucket.getID();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM task WHERE bucket_id = '"+ bucketId+"' ");
            ResultSet rs = ps.executeQuery();
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
    public ObservableList<Task> getNotification(Panel panel) throws SQLException {

        Connection conn = DBConnection.getConnection();
        ObservableList<Task> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM task WHERE state = 'pd' and bucket_id = ANY(SELECT id from bucket WHERE panel_id = '"+panel.getId()+"') ");
            ResultSet rs = ps.executeQuery();
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
