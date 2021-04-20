package com.tanner.planner.data;

import com.tanner.planner.models.Bucket;
import com.tanner.planner.models.Panel;
import com.tanner.planner.utils.Inflatable;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BucketDAO {

    public static String
            TABLE_NAME = "bucket",
            COLUMN_ID = "id",
            COLUMN_PANEL_ID = "panel_id",
            COLUMN_TITLE = "title";
    private Alert alert = new Alert(Alert.AlertType.NONE);

    public void addBucket(Bucket bucket) {
        String query = "insert into bucket(" + COLUMN_ID + ", " + COLUMN_PANEL_ID + ", " + COLUMN_TITLE  + ")" +
                "value ('" + bucket.getID() + "', '" + bucket.getPanel_id() + "', '" + bucket.getTitle()  + "');";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.execute();
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Bucket Added successfully");
            alert.show();
        } catch (SQLException e) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText(String.valueOf(e));
            alert.show();
        }
    }
    public void deleteBucket(Bucket bucket) {
        String query = "delete from task where bucket_id = '" + bucket.getID() +"' ";
        String query2 = "delete from bucket where id = '"+ bucket.getID()+"' ";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            PreparedStatement statement2 = con.prepareStatement(query2);
            statement.execute();
            statement2.execute();
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Bucket Deleted Successfully");
            alert.show();
        } catch (SQLException e) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText(String.valueOf(e));
            alert.show();
        }
    }
    public void updateBucket(Bucket bucket, String new_Title) {
        String query = "update bucket set title = '" +new_Title +"' where id = '" + bucket.getID() +"' ";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.execute();
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Bucket Updated Successfully");
            alert.show();
        } catch (SQLException e) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText(String.valueOf(e));
            alert.show();
        }
    }


    public ObservableList<Bucket> getBuckets(Panel panel) throws SQLException {

        Connection conn = DBConnection.getConnection();
        ObservableList<Bucket> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM bucket WHERE panel_id = '"+ panel.getId()+"'");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(new Bucket(rs.getString("id"),rs.getString("panel_id") , rs.getString("title")));
            }
        }
        catch(Exception e){

        }
        return list;

    }

}
