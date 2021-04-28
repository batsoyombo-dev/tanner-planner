package com.tanner.planner.controllers.dialogs;

import com.tanner.planner.controllers.HomeController;
import com.tanner.planner.models.User;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

public class AddPanelDialogControllerTest extends ApplicationTest {

    @Override
    public void start(Stage primaryStage) throws Exception{
        HomeController.user = new User(1, "erhmee", "123");
         AddPanelDialogController addPanelDialogController = new AddPanelDialogController();
         primaryStage.setScene(addPanelDialogController.getScene());
         FxToolkit.showStage();
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void addPanel() throws Exception{
        clickOn("#inp_panelTitleField");
        write("PanelDefault");
        clickOn("#inp_panelDescField");
        write("DescriptionFault");
        //clickOn("#btn_testColor");
        clickOn("#btn_submit");
    }

}