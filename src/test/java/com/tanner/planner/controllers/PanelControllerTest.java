package com.tanner.planner.controllers;

import com.tanner.planner.controllers.dialogs.AddPanelDialogController;
import com.tanner.planner.models.Panel;
import com.tanner.planner.models.User;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

public class PanelControllerTest extends ApplicationTest {

    @Override
    public void start(Stage primaryStage) throws Exception{
        User user = new User(1, "erhmee", "123");
        HomeController homeController = new HomeController(null, user);
        Panel panel = new Panel("fd03d645-805a-49ba-96ee-080d0be1bf36", 1, "TestPanel", "arere", "inp", "#DB5461");
        new PanelController(homeController, panel);
    }


    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void testBucket() {
        clickOn("#btn_addBucket");
        write("TestTestTest");
        clickOn("#btnCreate");
    }
}