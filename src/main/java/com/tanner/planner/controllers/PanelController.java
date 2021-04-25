package com.tanner.planner.controllers;

import com.tanner.planner.controllers.dialogs.*;
import com.tanner.planner.data.BucketDAO;
import com.tanner.planner.data.PanelDAO;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * PanelController class manages the buckets and tasks layout
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
public class PanelController implements Initializable{

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


    private final BorderPane root;
    private final BucketDAO bucketDAO;
    private final TaskDAO taskDAO;
    private final PanelDAO panelDAO;
    private final Panel panel;
    private final HomeController homeController;
    private final Stage stage;
    private final Hyperlink redirect;

    ObservableList<Bucket> listBucket;
    ObservableList<Task> taskList;

    private boolean favourite;

    /**
     * Constructor method of the PanelController class
     * @param homeController a Controller object of the Home window
     * @param panel a Panel object to be displayed
     * @throws IOException If the FXML file is not properly loaded
     */
    public PanelController(HomeController homeController, Panel panel) throws IOException{
        this.panel = panel;
        this.homeController = homeController;
        this.bucketDAO = new BucketDAO();
        this.taskDAO = new TaskDAO();
        this.panelDAO = new PanelDAO();
        this.redirect = new Hyperlink("https://github.com/batsoyombo-dev/tanner-planner");
        FXMLLoader loader = new FXMLLoader(super.getClass().getResource("/controllers/panel_layout.fxml"));
        loader.setController(this);
        this.root = loader.load();
        Scene scene = new Scene(this.root, 1440, 950);
        this.stage = new Stage();
        this.stage.setScene(scene);
        this.stage.setTitle(panel.getTitle());
        this.stage.setResizable(false);
        this.stage.setOnCloseRequest(e -> homeController.toggleStage(true));
        this.addIconToControl("/images/menu.png", btn_hamburgerMenu);
        this.addIconToControl("/images/help_white.png", btn_redirectGithub);
        this.addIconToControl("/images/notification_white.png", btn_showNotifications);
        this.addIconToControl("/images/analysis.png", btn_showAnalysis);
        if(panel.getCategory().equals("inp")) {
            this.addIconToControl("/images/favourite-filled.png", btn_makeFavourite);
            favourite = true;
        }
        else {
            this.addIconToControl("/images/favourite.png", btn_makeFavourite);
            favourite = false;
        }
        this.addIconToControl("/images/settings.png", btn_showSettings);
        txt_panelTitle.setText(panel.getTitle());

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

    /**
     * Method that adds icon to the buttons
     * @param path the path of the image in resource folder
     * @param control an object of Control class
     */

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

    /**
     * Handles the back button click event
     * @param e an Event argument object
     */
    public void handleBackToHomeClick(MouseEvent e) {
        this.stage.close();
        this.homeController.toggleStage(true);
    }

    /**
     * Handles the add bucket button event
     * @param event an Event argument object
     */
    @FXML
    void handleAddBucketClick(ActionEvent event) {
        try {
            new AddBucketDialogController(this, panel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds new bucket to the panel
     * @param bucket a Bucket object to be added to the panel
     * @throws SQLException If error occurs while executing the sql query
     */
    public void addBucketToPanel(Bucket bucket) throws SQLException {
        VBox bucketControlWrapper = newBucket(bucket);
        getTasks(bucketControlWrapper, bucket);
        hbox_bucketContainer.getChildren().add(bucketControlWrapper);
    }

    /**
     * Creates container VBox for bucket
     * @param bucket a Bucket object to be added to the panel
     * @return container VBox of bucket
     */
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
    /**
     * Creates new container for task
     * @param task a Task to be indlated to the container
     * @param container a container for Task object
     */
    public void newTaskContainer(Task task, VBox container) {
        Button btn_taskTitle = new Button(task.getTitle());
        btn_taskTitle.getStyleClass().add("task");
        btn_taskTitle.setCursor(Cursor.HAND);
        if(!task.getState().equals("cm")) {
            LocalDate dueDate = LocalDate.parse(task.getDate());
            LocalDate datePresent = LocalDate.now();
            if (datePresent.compareTo(dueDate) <= 0) {
                task.setState("nm");
            } else{
                task.setState("pd");
            }
            taskDAO.updateTask(task, task.getTitle(), task.getDescription(), task.getState());
        }
        if(task.getState().equals("cm"))
            btn_taskTitle.setStyle("-fx-border-color: #59ad00");
        if(task.getState().equals("pd"))
            btn_taskTitle.setStyle("-fx-border-color: #be1e2d");
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
    /**
     * Handles button favourite
     * @param event An event argument object
     */

    @FXML
    void handleFavouriteClick(ActionEvent event) {
        if(favourite){
            panelDAO.changeCategory(this.panel, false);
            this.addIconToControl("/images/favourite.png", btn_makeFavourite);
            favourite = false;
        }
        else{
            panelDAO.changeCategory(this.panel, true);
            this.addIconToControl("/images/favourite-filled.png", btn_makeFavourite);
            favourite = true;
        }

    }

    /**
     * Handles redirect button click event
     * @param event Event arguments
     */
    @FXML
    void handleRedirectGithub(ActionEvent event) throws URISyntaxException, IOException {
        URI uri = new URI("https://github.com/batsoyombo-dev/tanner-planner");
        Desktop.getDesktop().browse(uri);
    }

    /**
     * Handles setting button click event
     * @param event Event argument
     * @throws IOException when error occurs with creating BucketModifyColorDialog
     */
    @FXML
    void handleSettingsClick(ActionEvent event) throws IOException {
        try {
            new BucketModifyColorDialogController(panel, bp_panelMainContainer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles notification button click event
     * @param event Event arguments
     * @throws IOException If FXML file is not properly loaded
     */
    @FXML
    void handleNotificationClick(ActionEvent event) throws IOException {
        try{
            new BucketNotificationDialogController(panel);
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Return the root container of the bucket
     * @return The root container
     */
    public HBox getHBox(){
        return this.hbox_bucketContainer;
    }

    /**
     * Gets Add Bucket button
     * @return button (add bucket)
     */
    public Button getBtnAddBucket(){
        return this.btn_addBucket;
    }

    /**
     * Returns the root container of the layout
     * @return The root container control
     */
    public BorderPane getRoot(){return this.root;}
    
}

