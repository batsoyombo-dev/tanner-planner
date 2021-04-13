package com.tanner.planner.controllers.partials;

import com.tanner.planner.controllers.dialogs.AddPanelDialogController;
import com.tanner.planner.models.Panel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeHeaderController implements Initializable {

    private HomePanelContainerController panelContainer;

    public HomeHeaderController(HomePanelContainerController panelContainer) {
        this.panelContainer = panelContainer;
    }
    @FXML
    private Button btnHelp;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image1 = new Image("/images/help_black.png", btnHelp.getWidth(), btnHelp.getHeight(), false, true, true);
        BackgroundImage bImage1 = new BackgroundImage(image1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(btnHelp.getWidth(), btnHelp.getHeight(), true, true, true, false));
        Background backGround1 = new Background(bImage1);
        btnHelp.setBackground(backGround1);

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
