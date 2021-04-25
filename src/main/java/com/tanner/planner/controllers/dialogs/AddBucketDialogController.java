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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

/**
 * AddBucketDialog manages creating Add bucket dialog
 *
 * #########     #      ##     ##  ##     ##  ########  #######
 *    ##        # #     ## #   ##  ## #   ##  ##        ##    ##
 *    ##       #   #    ##  #  ##  ##  #  ##  ########  #######
 *    ##      #######   ##   # ##  ##   # ##  ##        ## ##
 *    ##     #       #  ##     ##  ##     ##  ########  ##   ##
 *
 * @author Tanner Team
 * @version 1.0
 * @since 2021/05/07
 * @link https://github.com/batsoyombo-dev/tanner-planner
 */
public class AddBucketDialogController {

    @FXML
    private Button btnCreate;
    @FXML
    private Button btnCancel;
    @FXML
    private final VBox root;

    private TextField txtTitle;
    private final Panel currentPanel;
    private final Stage stage;

    private final BucketDAO bucketDAO;
    private final PanelController panelController;

    /**
     * Constructor method of the AddBucketDialogController class
     * @param panelController a controller object of specific panel layout
     * @param currentPanel a main panel object
     * @throws IOException if FXML file is not properly loaded
     */
    public AddBucketDialogController(PanelController panelController, Panel currentPanel) throws IOException {
        this.currentPanel = currentPanel;
        this.panelController = panelController;
        this.bucketDAO = new BucketDAO();
        FXMLLoader loader = new FXMLLoader(super.getClass().getResource("/dialogs/add_bucket_dialog.fxml"));
        loader.setController(this);
        this.root = loader.load();
        Scene scene = new Scene(this.root, 400, 140);
        this.stage = new Stage();
        this.stage.setScene(scene);
        this.stage.setTitle(currentPanel.getTitle());
        this.stage.setResizable(false);
        this.stage.setOnCloseRequest(e -> panelController.getRoot().setEffect(new BoxBlur(0, 0, 0)));
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.show();
        panelController.getRoot().setEffect(new BoxBlur(5, 5, 10));
    }

    /**
     * Handles cancel button action
     * @param event object of ActionEvent class
     */
    @FXML
    void handleCancelClick(ActionEvent event) {
        panelController.getRoot().setEffect(new BoxBlur(0, 0, 0));
        ((Stage) btnCancel.getScene().getWindow()).close();
    }

    /**
     * Handles submit button action
     * @param event object of ActionEvent class
     * @throws SQLException error occurs when calling addBucket method of bucketDAO fails
     */
    @FXML
    public void handleSubmitClick(ActionEvent event) throws SQLException {
        String bucketTitle = txtTitle.getText();
        if(bucketTitle.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Bucket Name empty!");
            alert.show();
            return;
        }
        Bucket bucket = new Bucket( UUID.randomUUID().toString(), currentPanel.getId(),txtTitle.getText());
        bucketDAO.addBucket(bucket);
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
        panelController.getRoot().setEffect(new BoxBlur(0, 0, 0));
        this.panelController.addBucketToPanel(bucket);
    }

}
