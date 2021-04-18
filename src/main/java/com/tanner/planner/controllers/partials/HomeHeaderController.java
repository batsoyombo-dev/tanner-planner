package com.tanner.planner.controllers.partials;

import com.tanner.planner.controllers.dialogs.AddPanelDialogController;
import com.tanner.planner.models.Panel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import java.io.IOException;

public class HomeHeaderController {

    @FXML
    private TextField inp_searchField;

    private final HomePanelContainerController panelContainer;

    public HomeHeaderController(HomePanelContainerController panelContainer) {
        this.panelContainer = panelContainer;
    }

    @FXML
    public void handleSearchBtnClick(MouseEvent e) {
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
