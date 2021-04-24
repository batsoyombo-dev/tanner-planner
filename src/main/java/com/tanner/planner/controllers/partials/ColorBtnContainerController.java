package com.tanner.planner.controllers.partials;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ColorBtnContainerController {

    private Button currentSelectedColorBtn;

    public void handlePanelColorClick(MouseEvent e) {
        Button selected = ((Button)e.getSource());
        selected.getStyleClass().add("color-selected");
        if (this.currentSelectedColorBtn != null) {
            this.currentSelectedColorBtn.getStyleClass().remove("color-selected");
        }
        this.currentSelectedColorBtn = selected;
    }

    public String getSelectedColor() {
        if (this.currentSelectedColorBtn == null)
            return "";
        return this.currentSelectedColorBtn.getId();
    }

}
