package com.tanner.planner.controllers.dialogs;

import com.tanner.planner.controllers.HomeController;
import com.tanner.planner.controllers.PanelController;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.UUID;

public class AddTaskDialogController {
    private GridPane root;
    private Stage stage;
    private Bucket bucket;
    private VBox vBox;
    TaskDAO taskDAO;

    @FXML
    private TextField txtTitle;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtState;

    @FXML
    private Button btnAddTask;

    @FXML
    private Button btnCancel;
    public AddTaskDialogController(Bucket bucket, VBox vBox) throws IOException {
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
        this.stage.show();

    }
    @FXML
    void taskAdded(ActionEvent event) {
        Task task = new Task( UUID.randomUUID().toString(),bucket.getID(),txtTitle.getText(),txtDescription.getText(), txtState.getText());
        taskDAO.addTask(task);
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
        addTask(vBox, task);
    }

    @FXML
    void taskCanceled(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();

    }
    private void addTask(VBox vBox, Task task){
        Button taskTitle = new Button();
        taskTitle.setText(task.getTitle());
        taskTitle.getStyleClass().add("task");
        ((VBox)(vBox.getChildren().get(0))).getChildren().add(1, taskTitle);
    }

}
