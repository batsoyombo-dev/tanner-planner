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

public class SidebarController implements Initializable {

    @FXML
    private Button btn_defaultNavBtn;
    @FXML
    private VBox vbox_activityContainer;
    private Button btn_currentChosen;

    private final HomePanelContainerController panelContainer;
    private String currentCategory = "all";

    private ActivityDAO activityDAO;

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

    @FXML
    public void handleLogoutClick(MouseEvent e) throws IOException {
        ((Stage)(this.vbox_activityContainer.getScene().getWindow())).close();
        new AuthController(new Stage());
    }

    public void inflateActivityContainer() {
        List<Activity> activities = activityDAO.getActivities();
        for (Activity activity : activities)
            this.addActivityToContainer(activity);
    }

    public void addActivityToContainer(Activity activity) {
        ObservableList<Node> nodes = this.vbox_activityContainer.getChildren();
        nodes.removeIf(node -> node.getId().equals(activity.getPanel().getId()));
        Label activityLabel = new Label();
        activityLabel.getStyleClass().add("nav-item");
        Panel activityPanel = activity.getPanel();
        activityLabel.setText(activityPanel.getTitle());
//        switch(activity.getAction()) {
//            case "insert" -> activityLabel.setText("Created: " + activityPanel.getTitle());
//            case "update" -> activityLabel.setText("Deleted: " + activityPanel.getTitle());
//            case "delete" -> activityLabel.setText("Updated: " + activityPanel.getTitle());
//            default -> {
//                return;
//            }
//        }
        activityLabel.setId(activity.getPanel().getId());
        this.vbox_activityContainer.getChildren().add(1, activityLabel);
    }

}
