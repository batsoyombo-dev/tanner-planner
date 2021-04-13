package com.tanner.planner.controllers;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class PanelController implements Initializable {

    @FXML
    private Button btnHamburgerMenu;

    @FXML
    private Label txtLogo;

    @FXML
    private Button btnHelp;

    @FXML
    private Button btnNotification;

    @FXML
    private Button btnAnalysis;

    @FXML
    private Label txtPanel;

    @FXML
    private Button btnFavourite;

    @FXML
    private Button btnSettings;

    @FXML
    private VBox vboxBucket;

    @FXML
    private Button btnTask;

    @FXML
    private Button btnAddTask;

    @FXML
    private Button btnAddBucket;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Setting icons in the button
        Image image1 = new Image("/images/menu.png", btnHamburgerMenu.getWidth(), btnHamburgerMenu.getHeight(), false, true, true);
        BackgroundImage bImage1 = new BackgroundImage(image1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(btnHamburgerMenu.getWidth(), btnHamburgerMenu.getHeight(), true, true, true, false));
        Background backGround1 = new Background(bImage1);
        btnHamburgerMenu.setBackground(backGround1);

        Image image2 = new Image("/images/help_white.png", btnHelp.getWidth(), btnHelp.getHeight(), false, true, true);
        BackgroundImage bImage2 = new BackgroundImage(image2, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(btnHelp.getWidth(), btnHelp.getHeight(), true, true, true, false));
        Background backGround2 = new Background(bImage2);
        btnHelp.setBackground(backGround2);

        Image image3 = new Image("/images/notification_white.png", btnNotification.getWidth(), btnNotification.getHeight(), false, true, true);
        BackgroundImage bImage3 = new BackgroundImage(image3, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(btnNotification.getWidth(), btnNotification.getHeight(), true, true, true, false));
        Background backGround3 = new Background(bImage3);
        btnNotification.setBackground(backGround3);

        Image image4 = new Image("/images/analysis.png", btnAnalysis.getWidth(), btnAnalysis.getHeight(), false, true, true);
        BackgroundImage bImage4 = new BackgroundImage(image4, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(btnAnalysis.getWidth(), btnAnalysis.getHeight(), true, true, true, false));
        Background backGround4 = new Background(bImage4);
        btnAnalysis.setBackground(backGround4);

        Image image5 = new Image("/images/favourite.png", btnFavourite.getWidth(), btnFavourite.getHeight(), false, true, true);
        BackgroundImage bImage5 = new BackgroundImage(image5, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(btnFavourite.getWidth(), btnFavourite.getHeight(), true, true, true, false));
        Background backGround5 = new Background(bImage5);
        btnFavourite.setBackground(backGround5);

        Image image6 = new Image("/images/settings.png", btnSettings.getWidth(), btnSettings.getHeight(), false, true, true);
        BackgroundImage bImage6 = new BackgroundImage(image6, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(btnSettings.getWidth(), btnSettings.getHeight(), true, true, true, false));
        Background backGround6 = new Background(bImage6);
        btnSettings.setBackground(backGround6);




    }
}
