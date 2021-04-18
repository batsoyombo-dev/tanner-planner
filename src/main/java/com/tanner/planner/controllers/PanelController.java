package com.tanner.planner.controllers;

import com.tanner.planner.controllers.dialogs.AddBucketDialogController;
import com.tanner.planner.controllers.dialogs.AddTaskDialogController;
import com.tanner.planner.controllers.dialogs.ModifyBucketDialogController;
import com.tanner.planner.controllers.dialogs.ModifyTaskDialogController;
import com.tanner.planner.data.BucketDAO;
import com.tanner.planner.data.TaskDAO;
import com.tanner.planner.models.Bucket;
import com.tanner.planner.models.Panel;
import com.tanner.planner.models.Task;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PanelController implements Initializable {

    @FXML
    private BorderPane bp_panelMainContainer;
    @FXML
    private Button btn_hamburgerMenu;
    @FXML
    private Button btn_redirectGithub;
    @FXML
    private Button btn_showNotifications;
    @FXML
    private Button btn_showAnalysis;
    @FXML
    private Label txt_panelTitle;
    @FXML
    private Button btn_makeFavourite;
    @FXML
    private Button btn_showSettings;
    @FXML
    private Button btn_addBucket;
    @FXML
    private HBox hbox_bucketContainer;

    private BorderPane root;
    private final BucketDAO bucketDAO;
    private final TaskDAO taskDAO;
    ObservableList<Task> taskList;
    private final Panel panel;
    private final HomeController homeController;
    private final Stage stage;
    ObservableList<Bucket> listBucket;

    public PanelController(HomeController homeController, Panel panel) throws IOException{
        this.panel = panel;
        this.homeController = homeController;
        this.bucketDAO = new BucketDAO();
        this.taskDAO = new TaskDAO();
        FXMLLoader loader = new FXMLLoader(super.getClass().getResource("/controllers/panel_layout.fxml"));
        loader.setController(this);
        this.root = loader.load();
        Scene scene = new Scene(this.root, 1440, 1024);
        this.stage = new Stage();
        this.stage.setScene(scene);
        this.stage.setTitle(panel.getTitle());
        this.stage.setResizable(false);
        this.stage.setOnCloseRequest(e -> homeController.toggleStage(true));
        this.addIconToControl("/images/menu.png", btn_hamburgerMenu);
        this.addIconToControl("/images/help_white.png", btn_redirectGithub);
        this.addIconToControl("/images/notification_white.png", btn_showNotifications);
        this.addIconToControl("/images/analysis.png", btn_showAnalysis);
        this.addIconToControl("/images/favourite.png", btn_makeFavourite);
        this.addIconToControl("/images/settings.png", btn_showSettings);

        this.stage.show();
        bp_panelMainContainer.setStyle("-fx-background-color:  " +  panel.getColorConfig());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            listBucket = bucketDAO.getBuckets(panel);
            for(Bucket bucket : listBucket){
                this.addBucketToPanel(bucket);
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }

    public void addIconToControl(String path, Control control) {
        Image iconImage = new Image(path, control.getWidth(), control.getHeight(), false, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(
                iconImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(control.getWidth(), control.getHeight(), true, true, true, false));
        Background background = new Background(backgroundImage);
        control.setBackground(background);
    }

    public void handleBackToHomeClick(MouseEvent e) {
        this.stage.close();
        this.homeController.toggleStage(true);
    }
    @FXML
    void handleAddBucketClick(ActionEvent event) {
        try {
            new AddBucketDialogController(this, panel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addBucketToPanel(Bucket bucket) throws SQLException {
        VBox bucketControlWrapper = newBucket(bucket);
        getTasks(bucketControlWrapper, bucket);
        hbox_bucketContainer.getChildren().add(bucketControlWrapper);
    }

    public VBox newBucket(Bucket bucket){
        VBox container = new VBox();
        VBox bucketControl = new VBox();
        Label title = new Label();
        title.setText(bucket.getTitle());
        title.getStyleClass().add("title");
        title.setCursor(Cursor.HAND);
        bucketControl.getChildren().add(title);
        bucketControl.getStylesheets().addAll("/css/bucket.css", "/css/global.css");
        bucketControl.getStyleClass().add("bucket");
        container.getChildren().add(bucketControl);
        title.setOnMouseClicked(e -> {
            try {
                new ModifyBucketDialogController(bucket, hbox_bucketContainer, container);
            }
            catch (IOException e2) {
                e2.printStackTrace();
            }
        });
        Button addTask = new Button("Add Task");
        addTask.setCursor(Cursor.HAND);
        addTask.setOnAction(e -> {
            try {
                new AddTaskDialogController(this, bucket, container);
            }
            catch (IOException e2) {
                e2.printStackTrace();
            }
        });
        addTask.getStyleClass().add("addTask");
        ((VBox)(container.getChildren().get(0))).getChildren().add(addTask);
        return container;
    }

    public void getTasks(VBox container, Bucket bucket) throws SQLException{
        taskList = taskDAO.getTasks(bucket);
        for(Task task: taskList)
            newTaskContainer(task, container);
    }

    public void newTaskContainer(Task task, VBox container) {
        Button btn_taskTitle = new Button(task.getTitle());
        btn_taskTitle.getStyleClass().add("task");
        btn_taskTitle.setCursor(Cursor.HAND);
        btn_taskTitle.setOnMouseClicked(e -> {
            try {
                new ModifyTaskDialogController((VBox) container.getChildren().get(0), task, btn_taskTitle);
            }
            catch (IOException err) {
                err.printStackTrace();
            }
        });
        VBox bucketControl = ((VBox)(container.getChildren().get(0)));
        bucketControl.getChildren().add(bucketControl.getChildren().size() - 1, btn_taskTitle);
    }

    public HBox getHBox(){
        return this.hbox_bucketContainer;
    }
    public Button getBtnAddBucket(){
        return this.btn_addBucket;
    }
}

