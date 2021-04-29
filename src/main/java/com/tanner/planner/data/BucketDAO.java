package com.tanner.planner.data;

import com.tanner.planner.models.Bucket;
import com.tanner.planner.models.Panel;
import com.tanner.planner.utils.Inflatable;
import com.tanner.planner.utils.LoggerHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * BucketDAO manages bucket model databse access
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
public class BucketDAO {

    public static String
            TABLE_NAME = "bucket",
            COLUMN_ID = "id",
            COLUMN_PANEL_ID = "panel_id",
            COLUMN_TITLE = "title";
    private Alert alert = new Alert(Alert.AlertType.NONE);

    /**
     * Adds a bucket to the bucket table
     * @param bucket object of Bucket class
     */
    public void addBucket(Bucket bucket) {
        String query = "insert into bucket(" + COLUMN_ID + ", " + COLUMN_PANEL_ID + ", " + COLUMN_TITLE  + ")" +
                "value ('" + bucket.getID() + "', '" + bucket.getPanel_id() + "', '" + bucket.getTitle()  + "');";
        DBConnection.executeQuery(query, this.getClass().getName());
    }

    /**
     * Deletes bucket from database
     * @param bucket Bucket to be deleted from database
     */
    public void deleteBucket(Bucket bucket) {
        String query1 = "delete from task where bucket_id = '" + bucket.getID() +"';";
        String query2 = " delete from bucket where id = '"+ bucket.getID()+"';";
        DBConnection.executeQuery(query1, this.getClass().getName());
        DBConnection.executeQuery(query2, this.getClass().getName());
    }

    /**
     * Updates a bucket and writes to the table
     * @param bucket object of Bucket class
     * @param new_Title String variable contains new title of bucket
     */
    public void updateBucket(Bucket bucket, String new_Title) {
        String query = "update bucket set title = '" +new_Title +"' where id = '" + bucket.getID() +"' ";
        DBConnection.executeQuery(query, this.getClass().getName());
    }

    /**
     * Fetches all the buckets of the panel
     * @param panel A panel object to be fetched
     * @return Selection result
     */
    public ObservableList<Bucket> getBuckets(Panel panel) {
        ObservableList<Bucket> list = FXCollections.observableArrayList();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM bucket WHERE panel_id = '"+ panel.getId()+"'");
             ResultSet rs = ps.executeQuery();) {
            while(rs.next()){
                list.add(new Bucket(rs.getString("id"),rs.getString("panel_id") , rs.getString("title")));
            }
        }
        catch(Exception e){
            LoggerHandler.error(e.getMessage(), this.getClass().getName());
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText(String.valueOf(e));
            alert.show();
        }
        return list;
    }

}
