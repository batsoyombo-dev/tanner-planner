package com.tanner.planner.controllers;

import com.tanner.planner.controllers.partials.HomeHeaderController;
import com.tanner.planner.controllers.partials.HomePanelContainerController;
import com.tanner.planner.controllers.partials.SidebarController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    private BorderPane root;
    private HomePanelContainerController panelContainerController;

    public static int authenticatedUserId = 1;
//    @FXML
//    private ScrollPane sp_

    public HomeController(Stage stage) throws IOException {
        this.root = new BorderPane();
        this.panelContainerController = new HomePanelContainerController();
        BorderPane bp_mainContainer = new BorderPane();
        FXMLLoader sidebarLoader = new FXMLLoader(super.getClass().getResource("/partials/sidebar_partial.fxml"));
        FXMLLoader headerLoader = new FXMLLoader(super.getClass().getResource("/partials/home_header_partial.fxml"));
        FXMLLoader panelContainerLoader = new FXMLLoader(super.getClass().getResource("/partials/home_panel_container_partial.fxml"));
        sidebarLoader.setController(new SidebarController(this.panelContainerController));
        headerLoader.setController(new HomeHeaderController(this.panelContainerController));
        panelContainerLoader.setController(this.panelContainerController);
        bp_mainContainer.setTop(headerLoader.load());
        bp_mainContainer.setCenter(panelContainerLoader.load());
        this.root.setLeft(sidebarLoader.load());
        this.root.setCenter(bp_mainContainer);
        stage.setTitle("Tanner");
        Scene scene = new Scene(this.root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
