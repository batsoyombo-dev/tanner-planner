package com.tanner.planner.controllers;

import com.tanner.planner.controllers.partials.LoginPartialController;
import com.tanner.planner.data.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * AuthController class for Authentication management
 * Manages both authentication and registration
 *
 *#########     #      ##     ##  ##     ##  ########  #######
 *   ##        # #     ## #   ##  ## #   ##  ##        ##    ##
 *   ##       #   #    ##  #  ##  ##  #  ##  ########  #######
 *   ##      #######   ##   # ##  ##   # ##  ##        ## ##
 *   ##     #       #  ##     ##  ##     ##  ########  ##   ##
 *
 * @author Tanner Team
 * @version 1.0
 * @since 2021/05/07
 * @link https://github.com/batsoyombo-dev/tanner-planner
 */
public class AuthController {

    @FXML
    private VBox vbox_formContainer;

    private final HBox root;
    private final Stage stage;
    /**
     * Constructor method of AuthController class
     * @param stage instance of Stage class to contain the UI
     * @throws IOException If fxml file is not properly loaded
     */
    public AuthController(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(super.getClass().getResource("/controllers/auth_layout.fxml"));
        loader.setController(this);
        this.root = loader.load();
        this.navigateTo(new LoginPartialController(this), "login_form_partial.fxml");
        Scene scene = new Scene(this.root);
        stage.setScene(scene);
        stage.setTitle("Authentication");
        this.stage.getIcons().add(new Image("/images/app_icon.png"));
        stage.setMaximized(true);
        stage.show();
    }
    /**
     * Navigates between partial screens
     * @param controller Controller object of the desired FXML
     * @param path Path of the screen's FXML file
     * @throws IOException If FXML file is not properly loaded
     */
    public void navigateTo(Object controller, String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(super.getClass().getResource("/partials/" + path));
        loader.setController(controller);
        this.vbox_formContainer.getChildren().clear();
        this.vbox_formContainer.getChildren().add(loader.load());
    }

    /**
     * Closes the stage
     */
    public void close() {
        this.stage.close();
    }

}
