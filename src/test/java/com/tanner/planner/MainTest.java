package com.tanner.planner;

import com.tanner.planner.controllers.AuthController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

public class MainTest extends ApplicationTest {

    @Override
    public void start(Stage primaryStage) throws Exception{
        new AuthController(primaryStage);
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void start() {
        clickOn("#inp_usernameField");
        write("erhmee");
        clickOn("#inp_passwordField");
        write("123");
        clickOn("#loginButton");
    }

}