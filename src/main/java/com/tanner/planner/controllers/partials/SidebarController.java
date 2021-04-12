package com.tanner.planner.controllers.partials;

import com.tanner.planner.controllers.HomeController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class SidebarController implements Initializable {

    @FXML
    private Button btn_defaultNavBtn;

    private HomePanelContainerController panelContainer;
    private String currentCategory = "all";
    private Button btn_currentChosen;

    public SidebarController(HomePanelContainerController panelContainer) {
        this.panelContainer = panelContainer;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.btn_currentChosen = this.btn_defaultNavBtn;
    }

    @FXML
    public void handleNavigation(MouseEvent e) {
        Button btn_navBtn = ((Button)(e.getSource()));
        String newCategory = btn_navBtn.getId();
        if(newCategory.equals(this.currentCategory))
            return;
        btn_navBtn.getStyleClass().add("selected");
        this.btn_currentChosen.getStyleClass().remove("selected");
        this.currentCategory = newCategory;
        this.btn_currentChosen = btn_navBtn;
        this.panelContainer.inflateLayoutWithPanel(newCategory);
    }

}
