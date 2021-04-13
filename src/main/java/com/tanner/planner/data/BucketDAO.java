package com.tanner.planner.data;

import com.tanner.planner.models.Bucket;
import com.tanner.planner.models.Panel;
import com.tanner.planner.utils.Inflatable;
import javafx.application.Platform;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BucketDAO {

    public static String
            TABLE_NAME = "bucket",
            COLUMN_ID = "id",
            COLUMN_PANEL_ID = "panel_id",
            COLUMN_TITLE = "title";

    public int addBucket(Bucket bucket) {
        String query = "insert into bucket(" + COLUMN_ID + ", " + COLUMN_PANEL_ID + ", " + COLUMN_TITLE  + ")" +
                "value (" + bucket.getID() + ", '" + bucket.getPanel_id() + "', '" + bucket.getTitle()  + "');";
        try (Connection con = DBConnection.getConnection(); PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return -1;
        } catch (SQLException e) {
            return -1;
        }
    }

    public void addBucketConcurrently(Bucket bucket) {
        Thread newBucketThread = new Thread(() -> {
            String query = "insert into bucket(" + COLUMN_ID + ", " + COLUMN_PANEL_ID + ", " + COLUMN_TITLE + ")" +
                    "value ('" + bucket.getID() + "', " + bucket.getPanel_id() + ", '" + bucket.getTitle() + "');";
            try (Connection con = DBConnection.getConnection(); PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                statement.executeUpdate();
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    System.out.println("key " + resultSet.getInt(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, "new-panel-thread");
        newBucketThread.start();
    }

    public void getBucketsConcurrently(String category, Inflatable<Bucket> callback) {
        Thread listBucketsThread = new Thread(() -> {
            String query;
            switch (category) {
                case "all" -> query = "SELECT * FROM " + TABLE_NAME + ";";
                case "inp", "com" -> query = "SELECT * FROM " + TABLE_NAME + " where category = '" + category + "';";
                default -> {
                    return;
                }
            }

            try (Connection con = DBConnection.getConnection();
                 Statement statement = con.createStatement();
                 ResultSet resultSet = statement.executeQuery(query);) {
                List<Bucket> list = new ArrayList<>();
                while(resultSet.next())
                    list.add(new Bucket(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3)
                    ));
                Platform.runLater(() -> {
                    callback.inflate(list);
                });
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, "list-panels-thread");
        listBucketsThread.start();
    }

}
