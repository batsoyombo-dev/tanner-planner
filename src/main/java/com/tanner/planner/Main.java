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

/**
 * Main class for Tanner Planner Application
 * Inherits Application class and instantiates the application
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
 * */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new AuthController(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
