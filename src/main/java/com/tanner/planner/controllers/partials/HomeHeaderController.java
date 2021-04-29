package com.tanner.planner.controllers.partials;

import com.tanner.planner.controllers.dialogs.AddPanelDialogController;
import com.tanner.planner.models.Panel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * HomeHeaderController class manages the search and panel creation
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
public class HomeHeaderController implements Initializable {

    @FXML
    private TextField inp_searchField;
    @FXML
    private HBox hbox_rootContainer;
    @FXML
    private Button btn_search;
    @FXML
    private Button btn_addPanel;
    @FXML
    private Button btn_help;

    private final HomePanelContainerController panelContainer;

    /**
     * Constructor method of the HomeHeaderController class
     * @param panelContainer a controller object of home panel container partial
     */
    public HomeHeaderController(HomePanelContainerController panelContainer) {
        this.panelContainer = panelContainer;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.hbox_rootContainer.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            System.out.println(keyEvent.getCode());
            if (keyEvent.getCode() == KeyCode.ENTER)
                this.btn_search.fire();
        });
    }

    /**
     * Handles Search event
     * @param e event argument
     */
    @FXML
    public void handleSearchBtnClick(ActionEvent e) {
        String search = this.inp_searchField.getText();
        if (search.isEmpty())
            return;
        this.panelContainer.inflatePanelItemContainer(search);
    }

    /**
     * Handles new panel button click event
     * @param e Event arguments
     * @throws IOException If FXML file is not properly loaded
     */
    @FXML
    public void handleNewPanelClick(MouseEvent e) throws IOException {
        AddPanelDialogController dialog = new AddPanelDialogController();
        Panel newPanel = dialog.getCreatedPanel();
        if (newPanel != null) {
            this.panelContainer.addNewPanelItemToTheContainer(newPanel);
        }
    }

    @FXML
    void handleHelpBtnClick(ActionEvent event) throws URISyntaxException, IOException {
        URI uri = new URI("https://github.com/batsoyombo-dev/tanner-planner");
        Desktop.getDesktop().browse(uri);
    }

}
