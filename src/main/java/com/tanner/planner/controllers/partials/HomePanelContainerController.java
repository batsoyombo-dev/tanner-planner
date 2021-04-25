package com.tanner.planner.controllers.partials;

import com.tanner.planner.controllers.HomeController;
import com.tanner.planner.controllers.PanelController;
import com.tanner.planner.data.ActivityDAO;
import com.tanner.planner.data.PanelDAO;
import com.tanner.planner.models.Activity;
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

/**
 * HomePanelContainerController manages panels
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
public class HomePanelContainerController implements Inflatable<Panel> {

    @FXML
    private GridPane gp_panelItemContainer;

    private final PanelDAO panelDAO;
    private final ActivityDAO activityDAO;
    private final HomeController homeController;

    private int totalPanelItems = 0;
    private final int totalItemsInRow = 5;
    private boolean isLoading = false;
    private String currentCategory = "none";

    /**
     * Constructor method of the HomePanelContainerController class
     * @param homeController a controller object of home layout
     */
    public HomePanelContainerController(HomeController homeController) {
        this.panelDAO = new PanelDAO();
        this.activityDAO = new ActivityDAO();
        this.homeController = homeController;
    }

    /**
     * Adds Panels to the container
     * @param objects Panel type of object list
     */
    @Override
    public void inflate(List<Panel> objects) {
        this.isLoading = false;
        this.clearPanelItemContainer();
        if(objects != null) {
            for(Panel panel : objects) {
                this.gp_panelItemContainer.add(newPanelItemContainer(panel), this.totalPanelItems % this.totalItemsInRow, this.totalPanelItems / this.totalItemsInRow, 1, 1);
                this.totalPanelItems++;
            }
        }
    }

    /**
     * Fetches data from database and inflates container
     * @param category a category string to be searched from database
     */
    public void inflatePanelItemContainer(String category) {
        this.isLoading = true;
        System.out.println(category);
        this.currentCategory = category;
        this.panelDAO.getPanelsConcurrently(category, this);
    }

    /**
     * Removes all the panels from container
     */
    public void clearPanelItemContainer() {
        this.gp_panelItemContainer.getChildren().clear();
        this.totalPanelItems = 0;
    }

    public void addNewPanelItemToTheContainer(Panel panel) {
        this.panelDAO.addPanelConcurrently(panel);
        if (!this.currentCategory.equals("all") && !this.currentCategory.equals(panel.getCategory()))
            return;
        this.gp_panelItemContainer.add(newPanelItemContainer(panel), this.totalPanelItems % this.totalItemsInRow, this.totalPanelItems / this.totalItemsInRow, 1, 1);
        this.totalPanelItems++;
    }

    /**
     * Creates Vbox for panel
     * @param panel Object of Panel class
     * @return container VBox of panel
     */
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
                Activity activity = new Activity(-1, "insert", panel);
                this.activityDAO.addActivity(activity);
                this.homeController.addActivity(activity);
                new PanelController(homeController, panel);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        return container;
    }

    /**
     * Gets category of a panel
     * @return String value category of a panel
     */
    public String getCurrentCategory() {
        return this.currentCategory;
    }

    /**
     * Returns if the panel is loading
     * @return Boolean value of loading action
     */
    public boolean isPanelLoading() {
        return this.isLoading;
    }

}
