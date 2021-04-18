package com.tanner.planner.controllers.dialogs;

import com.tanner.planner.controllers.PanelController;
import com.tanner.planner.data.TaskDAO;
import com.tanner.planner.models.Bucket;
import com.tanner.planner.models.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.UUID;

public class AddTaskDialogController {

    @FXML
    private TextField inp_bucketTitleField;
    @FXML
    private TextField inp_bucketDescField;
    @FXML
    private TextField inp_bucketStateField;
    @FXML
    private Button btnAddTask;
    @FXML
    private Button btnCancel;

    private final GridPane root;
    private final Stage stage;
    private final Bucket bucket;
    private final VBox vBox;
    private final TaskDAO taskDAO;
    private final PanelController panelController;

    public AddTaskDialogController(PanelController panelController, Bucket bucket, VBox vBox) throws IOException {
        this.panelController = panelController;
        this.bucket = bucket;
        this.vBox = vBox;
        this.taskDAO = new TaskDAO();
        FXMLLoader loader = new FXMLLoader(super.getClass().getResource("/dialogs/add_task_dialog.fxml"));
        loader.setController(this);
        this.root = loader.load();
        Scene scene = new Scene(this.root, 300, 300);
        this.stage = new Stage();
        this.stage.setScene(scene);
        this.stage.setTitle(bucket.getTitle());
        this.stage.setResizable(false);
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.show();

    }
    @FXML
    void taskAdded(ActionEvent event) {
        Task task = new Task( UUID.randomUUID().toString(),bucket.getID(),inp_bucketTitleField.getText(),inp_bucketDescField.getText(), inp_bucketStateField.getText());
        taskDAO.addTask(task);
        this.stage.close();
        this.panelController.newTaskContainer(task, vBox);
    }

    @FXML
    void taskCanceled(ActionEvent event) {
        this.stage.close();
    }

}
