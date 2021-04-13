package com.tanner.planner.controllers.dialogs;

import com.tanner.planner.controllers.HomeController;
import com.tanner.planner.models.Panel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class AddPanelDialogController implements Initializable {

    @FXML
    private TextField
            inp_panelTitleField,
            inp_panelDescField,
            inp_panelCategoryField,
            inp_panelColorField;

    private Panel createdPanel = null;
    private Stage stage;

    public AddPanelDialogController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dialogs/add_panel_dialog.fxml"));
        loader.setController(this);
        Scene scene = new Scene(loader.load());
        this.stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Panel");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public Panel getCreatedPanel() {
        return this.createdPanel;
    }

    public void handlePanelSubmit(MouseEvent e) {
        String titleField = this.inp_panelTitleField.getText(),
                descField = this.inp_panelDescField.getText(),
                categoryField = this.inp_panelCategoryField.getText(),
                colorField = this.inp_panelColorField.getText();

        if(titleField.isEmpty()
                || descField.isEmpty()
                || categoryField.isEmpty()
                || colorField.isEmpty())
            return;

        this.createdPanel = new Panel(UUID.randomUUID().toString(), HomeController.authenticatedUserId, titleField, descField, categoryField, colorField);
        this.stage.close();
    }

}
