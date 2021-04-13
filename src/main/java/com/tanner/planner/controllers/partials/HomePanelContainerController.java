package com.tanner.planner.controllers.partials;

import com.tanner.planner.controllers.HomeController;
import com.tanner.planner.controllers.PanelController;
import com.tanner.planner.data.PanelDAO;
import com.tanner.planner.models.Panel;
import com.tanner.planner.utils.Inflatable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomePanelContainerController implements Initializable, Inflatable<Panel> {

    @FXML
    private GridPane gp_panelItemContainer;

    private PanelDAO panelDAO;
    private HomeController homeController;

    private int totalPanelItems = 0;
    private int totalItemsInRow = 5;
    private boolean isLoading = false;

    /**
     * @param homeController Controller for home window
     * */
    public HomePanelContainerController(HomeController homeController) {
        this.panelDAO = new PanelDAO();
        this.homeController = homeController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void inflate(List<Panel> objects) {
        this.isLoading = false;
        if(objects == null || objects.size() == 0) {
            this.clearPanelItemContainer();
        } else
            for(Panel panel : objects) {
                this.gp_panelItemContainer.add(newPanelItemContainer(panel), this.totalPanelItems % this.totalItemsInRow, this.totalPanelItems / this.totalItemsInRow, 1, 1);
                this.totalPanelItems++;
            }
    }

    public void inflatePanelItemContainer(String category) {
        this.isLoading = true;

        this.clearPanelItemContainer();
        this.panelDAO.getPanelsConcurrently(category, this);
    }

    public void clearPanelItemContainer() {
        this.gp_panelItemContainer.getChildren().clear();
        this.totalPanelItems = 0;
    }

    public void addNewPanelItemToTheContainer(Panel panel) {
        this.panelDAO.addPanelConcurrently(panel);
        this.gp_panelItemContainer.add(newPanelItemContainer(panel), this.totalPanelItems % this.totalItemsInRow, this.totalPanelItems / this.totalItemsInRow, 1, 1);
        this.totalPanelItems++;
    }

    public VBox newPanelItemContainer(Panel panel) {
        VBox container = new VBox();
        Label title = new Label(panel.getTitle());
        title.getStyleClass().add("panel-title");
        container.getStyleClass().add("panel-container");
        container.setId(panel.getId() + "");
        container.setStyle("-fx-background-color: " + panel.getColorConfig() + ";");
        container.getChildren().add(title);
        container.setOnMouseClicked(e -> {
            try {
                homeController.toggleStage(false);
                new PanelController(homeController, panel);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        return container;
    }

    public boolean isPanelLoading() {
        return this.isLoading;
    }

}
