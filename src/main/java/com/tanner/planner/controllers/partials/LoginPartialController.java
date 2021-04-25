package com.tanner.planner.controllers.partials;

import com.tanner.planner.controllers.AuthController;
import com.tanner.planner.controllers.HomeController;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginPartialController implements Initializable {

    @FXML
    private VBox vbox_rootContainer;
    @FXML
    private TextField inp_usernameField;
    @FXML
    private PasswordField inp_passwordField;
    @FXML
    private Button btn_login;

    private final AuthController authController;

    public LoginPartialController(AuthController authController) {
        this.authController = authController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.vbox_rootContainer.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            System.out.println(keyEvent.getCode());
            if (keyEvent.getCode() == KeyCode.ENTER)
                this.btn_login.fire();
        });
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

    public void handleNavigationClick(MouseEvent e) throws IOException {
        this.authController.navigateTo(new RegisterPartialController(authController), "register_form_partial.fxml");
    }

}
