package com.tanner.planner.controllers.partials;

import com.tanner.planner.controllers.AuthController;
import com.tanner.planner.controllers.HomeController;
import com.tanner.planner.data.UserDAO;
import com.tanner.planner.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * LoginPartialClass manages the authentication process
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
public class LoginPartialController {

    @FXML
    private TextField inp_usernameField;
    @FXML
    private PasswordField inp_passwordField;

    private final AuthController authController;

    /**
     * Constructor method of the LoginPartialController class
     * @param authController a controller object of auth layout
     */
    public LoginPartialController(AuthController authController) {
        this.authController = authController;
    }

    public void handleLoginClick(MouseEvent e) throws IOException {
        String usernameField = this.inp_usernameField.getText(),
                passwordField = this.inp_passwordField.getText();

        if (usernameField.isEmpty() || passwordField.isEmpty())
            return;

        UserDAO userDAO = new UserDAO();
        User user = new User(-1, usernameField, passwordField);
        if (userDAO.authenticate(user)) {
            this.authController.close();
            new HomeController(new Stage(), user);
        }
    }

    /**
     * Handles register button
     * @param e object of MouseEvent
     * @throws IOException error occurs when creating RegisterPartialController
     */
    public void handleNavigationClick(MouseEvent e) throws IOException {
        this.authController.navigateTo(new RegisterPartialController(authController), "register_form_partial.fxml");
    }

}
