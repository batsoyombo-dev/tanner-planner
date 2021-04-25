package com.tanner.planner.controllers.partials;

import com.tanner.planner.controllers.AuthController;
import com.tanner.planner.data.UserDAO;
import com.tanner.planner.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class
/**
 * RegisterPartialController manages registrations process
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
 */RegisterPartialController {


    @FXML
    private TextField inp_usernameField;
    @FXML
    private PasswordField
            inp_passwordField,
            inp_passwordConfirmField;

    private final AuthController authController;

    /**
     * Constructor method of the RegisterPartialController class
     * @param authController a controller object of auth layout
     */
    public RegisterPartialController(AuthController authController) {
        this.authController = authController;
    }

    /**
     * Goes back to Login screen
     * Handles Login button action
     * @param e object of MouseEvent class
     * @throws IOException error occurs when calling handleNavigationClick function fails
     */
    public void handleRegisterClick(MouseEvent e) throws IOException {
        String usernameField = this.inp_usernameField.getText(),
                passwordField = this.inp_passwordField.getText(),
                passwordConfirmField = this.inp_passwordConfirmField.getText();

        if (usernameField.isEmpty()
                || passwordField.isEmpty()
                || passwordConfirmField.isEmpty())
            return;

        if (!passwordField.equals(passwordConfirmField))
            return;

        User user = new User(-1, usernameField, passwordField);
        UserDAO userDAO = new UserDAO();
        if (userDAO.register(user))
            this.handleNavigationClick(e);
    }

    /**
     * Handles navigation button click event
     * @param e Event arguments
     * @throws IOException If FXML is not properly loaded
     */
    public void handleNavigationClick(MouseEvent e) throws IOException {
        this.authController.navigateTo(new LoginPartialController(authController), "login_form_partial.fxml");
    }


}
