package com.tanner.planner.controllers.partials;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * ColorBtnContainerController class manage
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
public class ColorBtnContainerController {

    private Button currentSelectedColorBtn;

    /**
     * Handles panel color pick button click event
     * @param e Event arguments
     */
    public void handlePanelColorClick(MouseEvent e) {
        Button selected = ((Button)e.getSource());
        selected.getStyleClass().add("color-selected");
        if (this.currentSelectedColorBtn != null) {
            this.currentSelectedColorBtn.getStyleClass().remove("color-selected");
        }
        this.currentSelectedColorBtn = selected;
    }

    /**
     * Gets selected color value
     * @return color value
     */
    public String getSelectedColor() {
        if (this.currentSelectedColorBtn == null)
            return "";
        return this.currentSelectedColorBtn.getId();
    }

}
