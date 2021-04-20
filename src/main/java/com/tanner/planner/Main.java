package com.tanner.planner;

import com.tanner.planner.controllers.AuthController;
import com.tanner.planner.controllers.HomeController;
import com.tanner.planner.data.UserDAO;
import com.tanner.planner.models.User;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new AuthController(primaryStage);
    }
    public HostServices getServices(){
        return getHostServices();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
