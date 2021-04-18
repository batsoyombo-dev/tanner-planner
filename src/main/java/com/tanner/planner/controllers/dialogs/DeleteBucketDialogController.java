package com.tanner.planner.controllers.dialogs;

import com.tanner.planner.data.BucketDAO;
import com.tanner.planner.models.Bucket;
import com.tanner.planner.models.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DeleteBucketDialogController implements Initializable {
    private Bucket bucket;
    private VBox root;
    BucketDAO bucketDAO;
    Stage stage;
    private HBox hBox;
    private VBox vBox;

    @FXML
    private TextField txtTitle;
    @FXML
    private Button btnSave;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnCancel;

    public DeleteBucketDialogController(Bucket bucket, HBox hBox, VBox vBox) throws IOException {
        this.bucket = bucket;
        this.hBox = hBox;
        this.vBox = vBox;
        bucketDAO = new BucketDAO();
        FXMLLoader loader = new FXMLLoader(super.getClass().getResource("/dialogs/delete_bucket_dialog.fxml"));
        loader.setController(this);
        this.root = loader.load();
        Scene scene = new Scene(this.root, 400, 100);
        this.stage = new Stage();
        this.stage.setScene(scene);
        this.stage.setTitle(bucket.getTitle());
        this.stage.setResizable(false);
        this.stage.show();

    }


    @FXML
    void clickedCancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void clickedDelete(ActionEvent event) {
        bucketDAO.deleteBucket(bucket);
        int index = vBox.getParent().getChildrenUnmodifiable().indexOf(vBox);
        hBox.getChildren().remove(index);
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
    @FXML
    void clickedSave(ActionEvent event) {
        //txtTitle.setText(txtTitle.getText());
        bucketDAO.updateBucket(bucket, txtTitle.getText());
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
        VBox vbox = (VBox) vBox.getChildren().get(0);
        Label title = (Label) vbox.getChildren().get(0);
        title.setText(txtTitle.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtTitle.setText(bucket.getTitle());
    }
}
