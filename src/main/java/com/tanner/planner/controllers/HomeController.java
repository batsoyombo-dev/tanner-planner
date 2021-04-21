package com.tanner.planner.controllers;

import com.tanner.planner.controllers.partials.HomeHeaderController;
import com.tanner.planner.controllers.partials.HomePanelContainerController;
import com.tanner.planner.controllers.partials.SidebarController;
import com.tanner.planner.models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * @author Batsyombo
 * */

public class HomeController {

    private final BorderPane root;
    private final HomePanelContainerController panelContainerController;
    private final Stage stage;

    private static User user = null;

    public HomeController(Stage stage, User authenticatedUser) throws IOException {
        user = authenticatedUser;
        this.stage = stage;
        this.root = new BorderPane();
        this.panelContainerController = new HomePanelContainerController(this);
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
        this.stage.setTitle("Tanner");
        Scene scene = new Scene(this.root);
        this.stage.setScene(scene);
        this.stage.setMaximized(true);
        this.stage.show();
    }

    public static User getUser() {
        return user;
    }

    public void toggleStage(boolean shouldShow) {
        if (shouldShow) {
            this.panelContainerController.inflatePanelItemContainer(this.panelContainerController.getCurrentCategory());
            this.stage.show();
        }
        else
            this.stage.hide();
    }

}
