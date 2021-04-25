package com.tanner.planner.controllers.partials;

import com.tanner.planner.controllers.AuthController;
import com.tanner.planner.data.UserDAO;
import com.tanner.planner.models.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterPartialController implements Initializable {

    @FXML
    private VBox vbox_rootContainer;
    @FXML
    private TextField inp_usernameField;
    @FXML
    private PasswordField
            inp_passwordField,
            inp_passwordConfirmField;
    @FXML
    private Button btn_register;

    private final AuthController authController;

    public RegisterPartialController(AuthController authController) {
        this.authController = authController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.vbox_rootContainer.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            System.out.println(keyEvent.getCode());
            if (keyEvent.getCode() == KeyCode.ENTER)
                this.btn_register.fire();
        });
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
