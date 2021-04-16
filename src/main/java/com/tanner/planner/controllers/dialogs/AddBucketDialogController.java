package com.tanner.planner.controllers.dialogs;

import com.tanner.planner.controllers.PanelController;
import com.tanner.planner.data.BucketDAO;
import com.tanner.planner.data.TaskDAO;
import com.tanner.planner.models.Bucket;
import com.tanner.planner.models.Panel;
import com.tanner.planner.models.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.UUID;

public class AddBucketDialogController {
    private Panel panel;
    private BucketDAO bucketDAO;
    private VBox root;
    private PanelController panelController;
    Stage stage;

    @FXML
    private Button btnCreate;
    @FXML
    private TextField txtTitle;

    @FXML
    private Button btnCancel;
    public AddBucketDialogController(PanelController panelController, Panel panel) throws IOException {
        this.panel = panel;
        this.panelController = panelController;
        this.bucketDAO = new BucketDAO();
        FXMLLoader loader = new FXMLLoader(super.getClass().getResource("/dialogs/add_bucket_dialog.fxml"));
        loader.setController(this);
        this.root = loader.load();
        Scene scene = new Scene(this.root, 400, 100);
        this.stage = new Stage();
        this.stage.setScene(scene);
        this.stage.setTitle(panel.getTitle());
        this.stage.setResizable(false);
        this.stage.show();

    }

    @FXML
    void clickedCancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void clickedCreate(ActionEvent event) {
        Bucket bucket = new Bucket( UUID.randomUUID().toString(), panel.getId(),txtTitle.getText());
        bucketDAO.addBucket(bucket);
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
        panelController.getTile_Pane().getChildren().clear();
        panelController.getTile_Pane().getChildren().add(panelController.getBtnAddBucket());
        panelController.initialize(null, null);
    }

}
