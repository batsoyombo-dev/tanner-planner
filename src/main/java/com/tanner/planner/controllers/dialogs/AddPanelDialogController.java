package com.tanner.planner.controllers.dialogs;

import com.tanner.planner.controllers.HomeController;
import com.tanner.planner.controllers.partials.ColorBtnContainerController;
import com.tanner.planner.models.Panel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.UUID;

public class AddPanelDialogController {

    @FXML
    private TextField
            inp_panelTitleField,
            inp_panelDescField,
            inp_panelColorField;
    @FXML
    private ChoiceBox<String> cb_categoryChoice;
    @FXML
    private ColorBtnContainerController colorBtnContainerController;
    private final Stage stage;

    private Panel createdPanel = null;


    public AddPanelDialogController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dialogs/add_panel_dialog.fxml"));
        loader.setController(this);
        Scene scene = new Scene(loader.load());
        this.stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Panel");
        stage.initModality(Modality.APPLICATION_MODAL);

        this.cb_categoryChoice.getItems().add("Normal");
        this.cb_categoryChoice.getItems().add("Important");
        this.cb_categoryChoice.setValue("Normal");
        this.inp_panelColorField.textProperty().addListener((e, a, d) -> {
            String colorValue = this.inp_panelColorField.getText();
            if (colorValue.length() > 7)
                inp_panelColorField.setText(a);
            if (!d.matches("^#([a-fA-F0-9]*)$")) {
                inp_panelColorField.setText(a);
            }
        });
        stage.showAndWait();
    }

    public Panel getCreatedPanel() {
        return this.createdPanel;
    }

    public void handlePanelSubmit(MouseEvent e) {
        String titleField = this.inp_panelTitleField.getText(),
                descField = this.inp_panelDescField.getText(),
                categoryField = this.cb_categoryChoice.getValue(),
                colorField = this.colorBtnContainerController.getSelectedColor();

        if(titleField.isEmpty()
                || descField.isEmpty()
                || categoryField.isEmpty()
                || colorField.isEmpty())
            return;

        String category = "nor";
        if (categoryField.equals("Important"))
            category = "inp";
        this.createdPanel = new Panel(UUID.randomUUID().toString(), HomeController.getUser().getId(), titleField, descField, category, colorField);
        this.stage.close();
    }

}
