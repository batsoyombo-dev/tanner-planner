package com.tanner.planner.controllers.dialogs;

import com.tanner.planner.data.PanelDAO;
import com.tanner.planner.data.TaskDAO;
import com.tanner.planner.models.Panel;
import com.tanner.planner.models.Task;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * BucketNotificationDialogController manages notifications
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
public class BucketNotificationDialogController implements Initializable {

    @FXML
    private VBox containerVBox;

    private ScrollPane root;
    private Stage stage;

    private Panel panel;
    private TaskDAO taskDAO;
    private ObservableList<Task> pastDueTasks;

    /**
     * Constructor method of the BucketNotificationDialogController class
     * @param panel A main panel object
     * @throws IOException If FXML file is not properly loaded
     */
    public BucketNotificationDialogController(Panel panel) throws IOException{
        this.panel = panel;
        this.taskDAO = new TaskDAO();
        FXMLLoader loader = new FXMLLoader(super.getClass().getResource("/dialogs/bucket_notification_dialog.fxml"));
        loader.setController(this);
        this.root = loader.load();
        Scene scene = new Scene(this.root, 400, 500);
        this.stage = new Stage();
        this.stage.setScene(scene);
        this.stage.setTitle("Notifications");
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pastDueTasks = taskDAO.getNotification(panel);
        for(Task task: pastDueTasks)
            newNotification(task, containerVBox);
    }

    /**
     * Creates notification and adds to the container Vbox
     * @param task object of Task class
     * @param containerVBox object of Vbox class
     */
    public void newNotification(Task task, VBox containerVBox){
        Label labelNotification = new Label();
        labelNotification.getStyleClass().add("dialogLabel");
        LocalDate dueDate = LocalDate.parse(task.getDate());
        LocalDate presentDate = LocalDate.now();
        long daysDue = DAYS.between(dueDate, presentDate);
        labelNotification.setText("Task " + "'" + task.getTitle()+ "'" + " is past due for "+daysDue+" days");
        containerVBox.getChildren().add(labelNotification);
    }
}
