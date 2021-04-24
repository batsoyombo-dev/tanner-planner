package com.tanner.planner.controllers.dialogs;

import com.tanner.planner.controllers.PanelController;
import com.tanner.planner.controllers.partials.ColorBtnContainerController;
import com.tanner.planner.data.BucketDAO;
import com.tanner.planner.data.PanelDAO;
import com.tanner.planner.models.Panel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class BucketModifyColorDialogController {
    private Panel panel;
    private PanelController panelController;
    private VBox root;
    private Stage stage;
    private BorderPane borderPane;
    private PanelDAO panelDAO;
    @FXML
    private ColorBtnContainerController colorBtnContainerController;

    public BucketModifyColorDialogController(Panel panel, BorderPane borderPane) throws IOException {
        this.panel = panel;
        this.panelDAO = new PanelDAO();
        this.borderPane = borderPane;
        FXMLLoader loader = new FXMLLoader(super.getClass().getResource("/dialogs/panel_controller_modify_color_dialog.fxml"));
        loader.setController(this);
        this.root = loader.load();
        Scene scene = new Scene(this.root, 400, 400);
        this.stage = new Stage();
        this.stage.setScene(scene);
        this.stage.setTitle("Modify Color");
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.show();
    }
    @FXML
    public void handleSubmitClick(ActionEvent event) {
        String colorField = this.colorBtnContainerController.getSelectedColor();
        if(colorField.isEmpty())
            return;
        this.panel.setColorConfig(colorField);
        panelDAO.changeColor(panel);
        borderPane.setStyle("-fx-background-color:  " +  panel.getColorConfig());
        stage.close();
    }
    @FXML
    public void handleCancelClick(ActionEvent event) {
        stage.close();
    }

}
