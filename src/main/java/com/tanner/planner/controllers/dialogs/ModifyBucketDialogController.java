package com.tanner.planner.controllers.dialogs;

import com.tanner.planner.data.BucketDAO;
import com.tanner.planner.models.Bucket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyBucketDialogController implements Initializable {

    @FXML
    private TextField txt_bucketTitle;

    private final Bucket bucket;
    private final BucketDAO bucketDAO;
    private final Stage stage;

    private final VBox root;
    private final HBox bucketContainer;
    private final VBox bucketWrapper;

    public ModifyBucketDialogController(Bucket bucket, HBox bucketContainer, VBox bucketWrapper) throws IOException {
        this.bucket = bucket;
        this.bucketContainer = bucketContainer;
        this.bucketWrapper = bucketWrapper;
        bucketDAO = new BucketDAO();
        FXMLLoader loader = new FXMLLoader(super.getClass().getResource("/dialogs/modify_bucket_dialog.fxml"));
        loader.setController(this);
        this.root = loader.load();
        Scene scene = new Scene(this.root, 500, 150);
        this.stage = new Stage();
        this.stage.setScene(scene);
        this.stage.setTitle(bucket.getTitle());
        this.stage.setResizable(false);
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.show();
    }

    @FXML
    void handleCancelClick(ActionEvent event) {
        this.stage.close();
    }

    @FXML
    void handleDeleteClick(ActionEvent event) {
        this.bucketDAO.deleteBucket(bucket);
        int index = bucketWrapper.getParent().getChildrenUnmodifiable().indexOf(bucketWrapper);
        this.bucketContainer.getChildren().remove(index);
        this.stage.close();
    }
    @FXML
    void handleSaveClick(ActionEvent event) {
        String bucketTitle = txt_bucketTitle.getText();
        if(bucketTitle.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Bucket Name empty!");
            alert.show();
            return;
        }
        bucketDAO.updateBucket(bucket, txt_bucketTitle.getText());
        VBox vbox = (VBox) bucketWrapper.getChildren().get(0);
        Label title = (Label) vbox.getChildren().get(0);
        title.setText(txt_bucketTitle.getText());
        this.stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txt_bucketTitle.setText(bucket.getTitle());
    }
}
