package com.tanner.planner.controllers.dialogs;

import com.tanner.planner.data.TaskDAO;
import com.tanner.planner.models.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyTaskDialogController implements Initializable {

    @FXML
    private TextField inp_taskTitleField;
    @FXML
    private TextField inp_taskDescField;
    @FXML
    private ChoiceBox<String> inp_taskStateField;

    private final GridPane root;
    private final VBox vBox;
    private final Task task;
    private final Button taskTitle;
    private final TaskDAO taskDAO;
    private final Stage stage;

    public ModifyTaskDialogController(VBox vBox, Task task, Button taskTitle) throws IOException {
            taskDAO = new TaskDAO();
            this.vBox = vBox;
            this.task = task;
            this.taskTitle = taskTitle;
            FXMLLoader loader = new FXMLLoader(super.getClass().getResource("/dialogs/modify_task_dialog.fxml"));
            loader.setController(this);
            this.root = loader.load();
            Scene scene = new Scene(this.root, 400, 300);
            this.stage = new Stage();
            this.stage.setScene(scene);
            this.stage.setTitle(task.getTitle());
            this.stage.setResizable(false);
            this.stage.initModality(Modality.APPLICATION_MODAL);
            this.stage.show();
    }

    @FXML
    void handleDeleteTaskClick(ActionEvent event) {
        taskDAO.deleteTask(task);
        int index = taskTitle.getParent().getChildrenUnmodifiable().indexOf(taskTitle);
        vBox.getChildren().remove(index);
        this.stage.close();
    }

    @FXML
    void handleUpdateTaskClick(ActionEvent event) {
    String title = inp_taskTitleField.getText();
    String description = inp_taskDescField.getText();
    String state = null;
    if(inp_taskStateField.getValue().equals("Completed")) {
        state = "cm";
        taskTitle.setStyle("-fx-border-color: #59ad00");
    }
    if(inp_taskStateField.getValue().equals("Past due")) {
        state = "pd";
        taskTitle.setStyle("-fx-border-color: #be1e2d");
    }
    if(inp_taskStateField.getValue().equals("Normal")) {
        state = "nm";
    }
    if(inp_taskStateField.getValue().equals("None")) {
        state = "None";
    }

    if(title.isEmpty() || state.equals("None")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fill title and state!");
            alert.show();
            return;
    }
    taskDAO.updateTask(task, title, description, state);
    this.stage.close();
    taskTitle.setText(inp_taskTitleField.getText());
    }

    @FXML
    void handleCancelClick(ActionEvent event) {
        this.stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inp_taskTitleField.setText(task.getTitle());
        inp_taskDescField.setText(task.getDescription());
        if(task.getState().equals("cm"))
            inp_taskStateField.setValue("Completed");
        if(task.getState().equals("pd"))
            inp_taskStateField.setValue("Past due");
        if(task.getState().equals("nm"))
            inp_taskStateField.setValue("Normal");
    }
}


