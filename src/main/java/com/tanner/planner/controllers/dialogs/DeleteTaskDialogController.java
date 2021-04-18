package com.tanner.planner.controllers.dialogs;

import com.tanner.planner.data.TaskDAO;
import com.tanner.planner.models.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DeleteTaskDialogController implements Initializable {
        private GridPane root;
        private VBox vBox;
        private Task task;
        private Button taskTitle;
        TaskDAO taskDAO;
        Stage stage;

        @FXML
        private TextField txtTitle;

        @FXML
        private TextField txtDescription;

        @FXML
        private TextField txtState;

        @FXML
        private Button btnDelete;

        @FXML
        private Button btnCancel;

        @FXML
        private Button btnUpdate;


    public DeleteTaskDialogController (VBox vBox, Task task, Button taskTitle) throws IOException {
            taskDAO = new TaskDAO();
            this.vBox = vBox;
            this.task = task;
            this.taskTitle = taskTitle;
            FXMLLoader loader = new FXMLLoader(super.getClass().getResource("/dialogs/delete_task_dialog.fxml"));
            loader.setController(this);
            this.root = loader.load();
            Scene scene = new Scene(this.root, 300, 300);
            this.stage = new Stage();
            this.stage.setScene(scene);
            this.stage.setTitle(task.getTitle());
            this.stage.setResizable(false);
            this.stage.show();

        }

        @FXML
        void actionDelete(ActionEvent event) {
            taskDAO.deleteTask(task);
            int index = taskTitle.getParent().getChildrenUnmodifiable().indexOf(taskTitle);
            vBox.getChildren().remove(index);
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
        }

        @FXML
        void actionUpdate(ActionEvent event) {
        String title = txtTitle.getText();
        String description = txtDescription.getText();
        String state = txtState.getText();
        taskDAO.updateTask(task, title, description, state);
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
        taskTitle.setText(txtTitle.getText());
        }

        @FXML
        void taskCanceled(ActionEvent event) {
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtTitle.setText(task.getTitle());
        txtDescription.setText(task.getDescription());
        txtState.setText(task.getState());
    }
}


