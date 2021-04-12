package com.tanner.planner.controllers.partials;

import com.tanner.planner.controllers.dialogs.AddPanelDialogController;
import com.tanner.planner.models.Panel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeHeaderController implements Initializable {

    private HomePanelContainerController panelContainer;

    public HomeHeaderController(HomePanelContainerController panelContainer) {
        this.panelContainer = panelContainer;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
