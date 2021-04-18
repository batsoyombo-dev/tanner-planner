package com.tanner.planner.data;


import com.tanner.planner.models.Bucket;
import com.tanner.planner.models.Panel;
import com.tanner.planner.models.Task;
import com.tanner.planner.utils.Inflatable;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
            COLUMN_STATE = "state";

    public void addTask(Task task) {
        String query = "insert into task(" + COLUMN_ID + ", " + COLUMN_BUCKET_ID + ", " + COLUMN_TITLE  +", " + COLUMN_DESCRIPTION + ", " + COLUMN_STATE +")" +
                "value ('" + task.getId() + "', '" + task.getBucket_id() + "', '" + task.getTitle()  + "', '" + task.getDescription()+ "', '" + task.getState()+"');";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.execute();
            JOptionPane.showMessageDialog(null, "Task Added successfully");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public void deleteTask(Task task) {
        String query = "delete from task where id = '" + task.getId() +"' ";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.execute();
            JOptionPane.showMessageDialog(null, "Task Deleted successfully");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public void updateTask(Task task, String title, String description, String state) {
        String query = "update task set title = '"+title +"', description = '"+ description+"', state = '"+ state+"' where id = '"+task.getId() +"'";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.execute();
            JOptionPane.showMessageDialog(null, "Task Updated successfully");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
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
                list.add(new Task(rs.getString("id"), rs.getString("bucket_id"), rs.getString("title"), rs.getString("description"), rs.getString("state")));
            }
        }
        catch(Exception e){

        }
        return list;

    }


}
