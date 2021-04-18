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
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

public class AddBucketDialogController {

    @FXML
    private Button btnCreate;
    @FXML
    private TextField txtTitle;

    private final Panel currentPanel;
    private final BucketDAO bucketDAO;
    private final Stage stage;
    private final PanelController panelController;

    private VBox root;


    @FXML
    private Button btnCancel;
    public AddBucketDialogController(PanelController panelController, Panel currentPanel) throws IOException {
        this.currentPanel = currentPanel;
        this.panelController = panelController;
        this.bucketDAO = new BucketDAO();
        FXMLLoader loader = new FXMLLoader(super.getClass().getResource("/dialogs/add_bucket_dialog.fxml"));
        loader.setController(this);
        this.root = loader.load();
        Scene scene = new Scene(this.root, 400, 100);
        this.stage = new Stage();
        this.stage.setScene(scene);
        this.stage.setTitle(currentPanel.getTitle());
        this.stage.setResizable(false);
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.show();
    }

    @FXML
    void handleCancelClick(ActionEvent event) {
        ((Stage) btnCancel.getScene().getWindow()).close();
    }

    @FXML
    void handleSubmitClick(ActionEvent event) throws SQLException {
        Bucket bucket = new Bucket( UUID.randomUUID().toString(), currentPanel.getId(),txtTitle.getText());
        bucketDAO.addBucket(bucket);
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
        this.panelController.addBucketToPanel(bucket);
    }

}
