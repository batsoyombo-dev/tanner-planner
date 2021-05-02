package com.tanner.planner.controllers;

import com.tanner.planner.controllers.partials.HomeHeaderController;
import com.tanner.planner.controllers.partials.HomePanelContainerController;
import com.tanner.planner.controllers.partials.SidebarController;
import com.tanner.planner.models.Activity;
import com.tanner.planner.models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Main class for Tanner Planner Application
 * Inherits Application class and instantiates the application
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
public class HomeController {

    public static User user = null;

    private final BorderPane root;
    private final HomePanelContainerController panelContainerController;
    private final SidebarController sidebarController;
    private final Stage stage;

    /**
     * Constructor method of the HomeController class
     * @param stage the stage to contain the UI
     * @param authenticatedUser the User object of authenticated user
     * @throws IOException If the FXML file is not properly loaded
     */
    public HomeController(Stage stage, User authenticatedUser) throws IOException {
        user = authenticatedUser;
        this.stage = new Stage();
        this.root = new BorderPane();
        this.panelContainerController = new HomePanelContainerController(this);
        this.sidebarController = new SidebarController(this.panelContainerController);
        BorderPane bp_mainContainer = new BorderPane();
        FXMLLoader sidebarLoader = new FXMLLoader(super.getClass().getResource("/partials/sidebar_partial.fxml"));
        FXMLLoader headerLoader = new FXMLLoader(super.getClass().getResource("/partials/home_header_partial.fxml"));
        FXMLLoader panelContainerLoader = new FXMLLoader(super.getClass().getResource("/partials/home_panel_container_partial.fxml"));
        sidebarLoader.setController(this.sidebarController);
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
        this.stage.getIcons().add(new Image("/images/app_icon.png"));
        this.stage.show();
    }

    /**
     * Getter method for the user
     * @return User This returns instance of User class
     */
    public static User getUser() {
        return user;
    }

    /**
     * Adds new activity to the sidebar
     * @param activity  an Activity object to be added to the sidebar container
     */
    public void addActivity(Activity activity) {
        this.sidebarController.addActivityToContainer(activity);
    }

    /**
     * Toggles the visibility of the stage
     * @param shouldShow boolean value to be used to toggle the stage
     */
    public void toggleStage(boolean shouldShow) {
        if (shouldShow) {
            this.panelContainerController.inflatePanelItemContainer(this.panelContainerController.getCurrentCategory());
            this.stage.show();
        }
        else
            this.stage.hide();
    }

}
