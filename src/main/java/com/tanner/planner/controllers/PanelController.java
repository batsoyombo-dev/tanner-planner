package com.tanner.planner.controllers;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;

import java.net.URL;
import java.util.ResourceBundle;

public class PanelController implements Initializable {

    @FXML
    private Button btnHamburgerMenu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ImageView menu = new ImageView("/images/menu.png");
        menu.setFitHeight(20);
        menu.setFitWidth(20);
        //menu.setPreserveRatio(true);
        //btnHamburgerMenu.setGraphic(menu);

        Image image = new Image("/images/menu.png", btnHamburgerMenu.getWidth(), btnHamburgerMenu.getHeight(), false, true, true);
        BackgroundImage bImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(btnHamburgerMenu.getWidth(), btnHamburgerMenu.getHeight(), true, true, true, false));

        Background backGround = new Background(bImage);
        btnHamburgerMenu.setBackground(backGround);

    }
}
