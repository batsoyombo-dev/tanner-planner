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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeHeaderController implements Initializable {

    @FXML
    private TextField inp_searchField;
    @FXML
    private HBox hbox_rootContainer;
    @FXML
    private Button btn_search;

    private final HomePanelContainerController panelContainer;

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

    @FXML
    public void handleSearchBtnClick(ActionEvent e) {
        String search = this.inp_searchField.getText();
        if (search.isEmpty())
            return;
        this.panelContainer.inflatePanelItemContainer(search);
    }

    @FXML
    public void handleNewPanelClick(MouseEvent e) throws IOException {
        AddPanelDialogController dialog = new AddPanelDialogController();
        Panel newPanel = dialog.getCreatedPanel();
        if (newPanel != null) {
            this.panelContainer.addNewPanelItemToTheContainer(newPanel);
        }
    }

}
