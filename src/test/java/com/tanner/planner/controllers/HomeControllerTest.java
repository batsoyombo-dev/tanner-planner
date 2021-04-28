package com.tanner.planner.controllers;

import com.tanner.planner.models.User;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

public class HomeControllerTest extends ApplicationTest {

    @Override
    public void start(Stage primaryStage) throws Exception{
        User user = new User(1, "erhmee", "123");
        new HomeController(primaryStage, user);
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void addPanel() throws Exception{
        clickOn("#btn_addPanel");
    }
}