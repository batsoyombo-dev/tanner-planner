package com.tanner.planner.controllers;

import com.tanner.planner.controllers.partials.LoginPartialController;
import com.tanner.planner.data.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthController {

    @FXML
    private VBox vbox_formContainer;

    private HBox root;
    private Stage stage;

    public AuthController(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(super.getClass().getResource("/controllers/auth_layout.fxml"));
        loader.setController(this);
        this.root = loader.load();
        this.navigateTo(new LoginPartialController(this), "login_form_partial.fxml");
        Scene scene = new Scene(this.root);
        stage.setScene(scene);
        stage.setTitle("Authentication");
        stage.setMaximized(true);
        stage.show();
    }

    public void navigateTo(Object controller, String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(super.getClass().getResource("/partials/" + path));
        loader.setController(controller);
        this.vbox_formContainer.getChildren().clear();
        this.vbox_formContainer.getChildren().add(loader.load());
    }

    public void close() {
        this.stage.close();
    }

}
