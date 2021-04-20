package com.tanner.planner.data;

import com.tanner.planner.controllers.HomeController;
import com.tanner.planner.models.Panel;
import com.tanner.planner.utils.Inflatable;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PanelDAO {

    public static String
            TABLE_NAME = "panel",
            COLUMN_ID = "id",
            COLUMN_TITLE = "title",
            COLUMN_USER_ID = "user_id",
            COLUMN_DESC = "description",
            COLUMN_CATEGORY = "category",
            COLUMN_COLOR = "color";

    public int addPanel(Panel panel) {
        String query = "insert into panel(" + COLUMN_USER_ID + ", " + COLUMN_TITLE + ", " + COLUMN_DESC + ", " + COLUMN_CATEGORY + ", " + COLUMN_COLOR + ")" +
                "value (" + panel.getUserId() + ", '" + panel.getTitle() + "', '" + panel.getDescription() + "', '" + panel.getCategory() + "', '" + panel.getColorConfig() + "');";
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

    public void addPanelConcurrently(Panel panel) {
        Thread newPanelThread = new Thread(() -> {
            String query = "insert into panel(" + COLUMN_ID + ", " + COLUMN_USER_ID + ", " + COLUMN_TITLE + ", " + COLUMN_DESC + ", " + COLUMN_CATEGORY + ", " + COLUMN_COLOR + ")" +
                    "value ('" + panel.getId() + "', " + panel.getUserId() + ", '" + panel.getTitle() + "', '" + panel.getDescription() + "', '" + panel.getCategory() + "', '" + panel.getColorConfig() + "');";
            try (Connection con = DBConnection.getConnection(); Statement statement = con.createStatement()) {
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, "new-panel-thread");
        newPanelThread.start();
    }

    public void getPanelsConcurrently(String category, Inflatable<Panel> callback) {
        Thread listPanelsThread = new Thread(() -> {
            String query = "SELECT * FROM " + TABLE_NAME;
                    switch (category) {
                case "all" -> query += " where " + COLUMN_USER_ID + " = " + HomeController.getUser().getId() + "";
                case "inp", "com" -> query +=" where " + COLUMN_CATEGORY + " = '" + category + "'";
                default -> query += " where " + COLUMN_TITLE + " = '" + category + "'";
            }
            query += " and " + COLUMN_USER_ID + " = " + HomeController.getUser().getId() + ";";

            try (Connection con = DBConnection.getConnection();
                 Statement statement = con.createStatement();
                 ResultSet resultSet = statement.executeQuery(query);) {
                List<Panel> list = new ArrayList<>();
                while(resultSet.next())
                    list.add(new Panel(
                            resultSet.getString(1),
                            resultSet.getInt(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6)
                    ));
                Platform.runLater(() -> {
                    callback.inflate(list);
                });
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, "list-panels-thread");
        listPanelsThread.start();
    }
    public void changeCategory(Panel panel, Boolean favourite){
        String query;
        if(favourite)
            query= "UPDATE panel SET category = '"+ "inp" +"' WHERE id = '"+panel.getId()+"' ";
        else
            query= "UPDATE panel SET category = '"+ "nor" +"' WHERE id = '"+panel.getId()+"' ";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            Alert alert =  new Alert(Alert.AlertType.NONE);
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText(String.valueOf(e));
            alert.show();
        }
    }

}
