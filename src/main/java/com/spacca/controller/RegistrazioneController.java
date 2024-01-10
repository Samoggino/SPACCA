package com.spacca.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class RegistrazioneController implements Initializable {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confermaPasswordField;

    @FXML
    private Label labelConfermaPassword;

    @FXML
    private Button save;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            // Disabilita il pulsante di login all'inizio
            save.setDisable(false);

        } catch (Exception e) {
            System.err.println("Error initialize: " + e.getMessage() + "initialize");
        }
    }

    @FXML
    private void controllaPassword() {

        String password = passwordField.getText();

        if (password.isEmpty()) {
            System.out.println(password);
        } else {
            System.out.println(confermaPasswordField.getText());
        }

        if (password.equals(confermaPasswordField.getText()))

        {
            labelConfermaPassword.setText("Password confermata! ");
            labelConfermaPassword.setTextFill(Color.BLACK);
        } else {
            labelConfermaPassword.setText("Password errata! ");
            labelConfermaPassword.setTextFill(Color.ORANGE);
        }

    }

}
