package com.tanner.planner.controllers;


import com.tanner.planner.controllers.dialogs.AddBucketDialogController;
import com.tanner.planner.controllers.dialogs.AddTaskDialogController;
import com.tanner.planner.data.BucketDAO;
import com.tanner.planner.data.DBConnection;
import com.tanner.planner.data.TaskDAO;
import com.tanner.planner.models.Bucket;
import com.tanner.planner.models.Panel;
import com.tanner.planner.models.Task;
import com.tanner.planner.utils.Inflatable;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PanelController implements Initializable {

    private BorderPane root;

    private BucketDAO bucketDAO;

    private TaskDAO taskDAO;

    ObservableList<Task> taskList;
    @FXML
    private BorderPane borderPane;

    @FXML
    private Button btnHamburgerMenu;


    @FXML
    private Label txtLogo;

    @FXML
    private Button btnHelp;

    @FXML
    private Button btnNotification;

    @FXML
    private Button btnAnalysis;

    @FXML
    private Label txtPanel;

    @FXML
    private Button btnFavourite;

    @FXML
    private Button btnSettings;

    @FXML
    private VBox vboxBucket;

    @FXML
    private Button btnTask;

    @FXML
    private Button btnAddTask;

    @FXML
    private Button btnAddBucket;
    @FXML
    private ScrollPane scroll_Pane;


    @FXML
    private HBox hBox;

    private Panel panel;
    private HomeController homeController;
    private Stage stage;
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
        this.stage.setOnCloseRequest(e -> {
            homeController.toggleStage(true);
        });
        //Setting icons in the button
        Image image1 = new Image("/images/menu.png", btnHamburgerMenu.getWidth(), btnHamburgerMenu.getHeight(), false, true, true);
        BackgroundImage bImage1 = new BackgroundImage(image1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(btnHamburgerMenu.getWidth(), btnHamburgerMenu.getHeight(), true, true, true, false));
        Background backGround1 = new Background(bImage1);
        btnHamburgerMenu.setBackground(backGround1);

        Image image2 = new Image("/images/help_white.png", btnHelp.getWidth(), btnHelp.getHeight(), false, true, true);
        BackgroundImage bImage2 = new BackgroundImage(image2, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(btnHelp.getWidth(), btnHelp.getHeight(), true, true, true, false));
        Background backGround2 = new Background(bImage2);
        btnHelp.setBackground(backGround2);

        Image image3 = new Image("/images/notification_white.png", btnNotification.getWidth(), btnNotification.getHeight(), false, true, true);
        BackgroundImage bImage3 = new BackgroundImage(image3, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(btnNotification.getWidth(), btnNotification.getHeight(), true, true, true, false));
        Background backGround3 = new Background(bImage3);
        btnNotification.setBackground(backGround3);

        Image image4 = new Image("/images/analysis.png", btnAnalysis.getWidth(), btnAnalysis.getHeight(), false, true, true);
        BackgroundImage bImage4 = new BackgroundImage(image4, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(btnAnalysis.getWidth(), btnAnalysis.getHeight(), true, true, true, false));
        Background backGround4 = new Background(bImage4);
        btnAnalysis.setBackground(backGround4);

        Image image5 = new Image("/images/favourite.png", btnFavourite.getWidth(), btnFavourite.getHeight(), false, true, true);
        BackgroundImage bImage5 = new BackgroundImage(image5, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(btnFavourite.getWidth(), btnFavourite.getHeight(), true, true, true, false));
        Background backGround5 = new Background(bImage5);
        btnFavourite.setBackground(backGround5);

        Image image6 = new Image("/images/settings.png", btnSettings.getWidth(), btnSettings.getHeight(), false, true, true);
        BackgroundImage bImage6 = new BackgroundImage(image6, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(btnSettings.getWidth(), btnSettings.getHeight(), true, true, true, false));
        Background backGround6 = new Background(bImage6);
        btnSettings.setBackground(backGround6);
        this.stage.show();
        borderPane.setStyle("-fx-background-color:  " +  panel.getColorConfig());
        scroll_Pane.setStyle("-fx-background: " + this.panel.getColorConfig());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try {
            //DB-s Bucketuudiig listed avna, panel-idtai ni hargalzuulj
            listBucket = bucketDAO.getBuckets(panel);
            //bucket tus bolgond
            for(Bucket bucket : listBucket){
                //shine vbox uusgeed, title-iig ugnu
                VBox vBox = newBucket(bucket.getTitle());
                //taskuuda vbox ruu avna, DB-s taskuuda avna
                vBox = getTasks(vBox, bucket);
//                vBox.getStylesheets().addAll("/css/bucket.css", "/css/global.css");
//                vBox.getStyleClass().add("bucket");
                //add task button nemne
                Button addTask = new Button("Add Task");
                //darah ued form neegdene
                addTask.setOnAction(e -> {
                    try {
                        PanelController panelController;
                        panelController = this;
                        new AddTaskDialogController(panelController, bucket);
                    }
                    catch (IOException e2) {
                        e2.printStackTrace();
                    }
                });
                addTask.getStyleClass().add("addTask");
                ((VBox)(vBox.getChildren().get(0))).getChildren().add(addTask);
                hBox.getChildren().add(vBox);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public void handleBackToHomeClick(MouseEvent e) {
        this.stage.close();
        this.homeController.toggleStage(true);
    }
//addbucket ued dialog neegdene
    @FXML
    void clickedAddBucket(ActionEvent event) {
        try {
            PanelController panelController;
            panelController = this;
            new AddBucketDialogController(panelController, panel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //bucket-d zoriulj vbox uusgene
    public VBox newBucket(String bucketTitle){
        VBox container = new VBox();
        VBox vBox = new VBox();
        Label title = new Label();
        title.setText(bucketTitle);
        title.getStyleClass().add("title");
        vBox.getChildren().add(title);
        vBox.getStylesheets().addAll("/css/bucket.css", "/css/global.css");
        vBox.getStyleClass().add("bucket");
        container.getChildren().add(vBox);
        return container;
    }
    //bucket dotorhi taskuudiig avna
    public VBox getTasks(VBox vBox, Bucket bucket) throws SQLException{

        taskList = taskDAO.getTasks(bucket);
        for(Task task: taskList){
            Button taskTitle = new Button();
            taskTitle.setText(task.getTitle());
            taskTitle.getStyleClass().add("task");
            ((VBox)(vBox.getChildren().get(0))).getChildren().add(taskTitle);
        }
        return vBox;
    }
    public HBox getHBox(){
        return this.hBox;
    }
    public Button getBtnAddBucket(){
        return this.btnAddBucket;
    }
}
