package com.tanner.planner.controllers.dialogs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AddBucketController {

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnCancel;

    @FXML
    void clickedCancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();

    }

    @FXML
    void clickedCreate(ActionEvent event) {


    }

}
