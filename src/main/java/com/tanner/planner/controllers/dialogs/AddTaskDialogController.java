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
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class AddTaskDialogController {

    @FXML
    private TextField inp_taskTitleField;
    @FXML
    private TextField inp_taskDescField;
    @FXML
    private DatePicker dueDatePicker;
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
        Scene scene = new Scene(this.root, 380, 300);
        this.stage = new Stage();
        this.stage.setScene(scene);
        this.stage.setTitle(bucket.getTitle());
        this.stage.setResizable(false);
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.show();

    }
    @FXML
    public void taskAdded(ActionEvent event) {
        if(inp_taskTitleField.getText().isEmpty() || dueDatePicker.getValue()==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fill title and date!");
            alert.show();
            return;
        }
        String taskTitle = inp_taskTitleField.getText();
        String dueDate = dueDatePicker.getValue().toString();
        LocalDate taskDueDate = LocalDate.parse(dueDate);
        LocalDate datePresent = LocalDate.now();
        String state;
        if(datePresent.compareTo(taskDueDate) <= 0){
            state = "nm";
        }
        else
            state = "pd";

        Task task = new Task( UUID.randomUUID().toString(),bucket.getID(),inp_taskTitleField.getText(),inp_taskDescField.getText(), state, dueDate);
        taskDAO.addTask(task);
        this.stage.close();
        this.panelController.newTaskContainer(task, vBox);
    }

    @FXML
    void taskCanceled(ActionEvent event) {
        this.stage.close();
    }

}
