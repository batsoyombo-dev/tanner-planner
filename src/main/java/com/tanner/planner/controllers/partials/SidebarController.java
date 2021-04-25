package com.tanner.planner.controllers.partials;

import com.tanner.planner.controllers.AuthController;
import com.tanner.planner.controllers.HomeController;
import com.tanner.planner.data.ActivityDAO;
import com.tanner.planner.models.Activity;
import com.tanner.planner.models.Panel;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * SidebarController class manages the panel navigation
 *
 *#########     #      ##     ##  ##     ##  ########  #######
 *   ##        # #     ## #   ##  ## #   ##  ##        ##    ##
 *   ##       #   #    ##  #  ##  ##  #  ##  ########  #######
 *   ##      #######   ##   # ##  ##   # ##  ##        ## ##
 *   ##     #       #  ##     ##  ##     ##  ########  ##   ##
 *
 * @author Tanner Team
 * @version 1.0
 * @since 2021/05/07
 * @link https://github.com/batsoyombo-dev/tanner-planner
 */
public class SidebarController implements Initializable {

    @FXML
    private Button btn_defaultNavBtn;
    @FXML
    private VBox vbox_activityContainer;
    private Button btn_currentChosen;

    private final HomePanelContainerController panelContainer;
    private ActivityDAO activityDAO;

    private String currentCategory = "all";

    /**
     * Constructor function of SideBarController
     * @param panelContainer Container of panels container
     */
    public SidebarController(HomePanelContainerController panelContainer) {
        this.panelContainer = panelContainer;
        this.activityDAO = new ActivityDAO();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.btn_currentChosen = this.btn_defaultNavBtn;
        this.panelContainer.inflatePanelItemContainer(this.currentCategory);
        this.inflateActivityContainer();
    }

    /**
     * Handles navigation button click event
     * @param e Event arguments
     */
    @FXML
    public void handleNavigation(MouseEvent e) {
        if (this.panelContainer.isPanelLoading())
            return;
        Button btn_navBtn = ((Button)(e.getSource()));
        String newCategory = btn_navBtn.getId();
        if(newCategory.equals(this.currentCategory))
            return;
        btn_navBtn.getStyleClass().add("selected");
        this.btn_currentChosen.getStyleClass().remove("selected");
        this.currentCategory = newCategory;
        this.btn_currentChosen = btn_navBtn;
        this.panelContainer.inflatePanelItemContainer(newCategory);
    }

    /**
     * Handles Logout button action
     * @param e object of MouseEvent class
     * @throws IOException error occurs when creating AuthController fails
     */
    @FXML
    public void handleLogoutClick(MouseEvent e) throws IOException {
        ((Stage)(this.vbox_activityContainer.getScene().getWindow())).close();
        new AuthController(new Stage());
    }

    /**
     * Inflates activity container layout
     */
    public void inflateActivityContainer() {
        List<Activity> activities = activityDAO.getActivities();
        for (Activity activity : activities)
            this.addActivityToContainer(activity);
    }

    /**
     * Adds activity object to the container
     * @param activity an activity to be added to the container
     */
    public void addActivityToContainer(Activity activity) {
        ObservableList<Node> nodes = this.vbox_activityContainer.getChildren();
        nodes.removeIf(node -> node.getId().equals(activity.getPanel().getId()));
        Label activityLabel = new Label();
        activityLabel.getStyleClass().add("nav-item");
        Panel activityPanel = activity.getPanel();
        activityLabel.setText(activityPanel.getTitle());
        activityLabel.setId(activity.getPanel().getId());
        this.vbox_activityContainer.getChildren().add(1, activityLabel);
    }

}
