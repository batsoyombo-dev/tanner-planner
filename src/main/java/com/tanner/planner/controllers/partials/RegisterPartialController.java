package com.tanner.planner.controllers.partials;

import com.tanner.planner.controllers.AuthController;
import com.tanner.planner.data.UserDAO;
import com.tanner.planner.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class RegisterPartialController {

    private AuthController authController;

    @FXML
    private TextField inp_usernameField;
    @FXML
    private PasswordField
            inp_passwordField,
            inp_passwordConfirmField;

    public RegisterPartialController(AuthController authController) throws IOException {
        this.authController = authController;
    }

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

    public void handleNavigationClick(MouseEvent e) throws IOException {
        this.authController.navigateTo(new LoginPartialController(authController), "login_form_partial.fxml");
    }


}
