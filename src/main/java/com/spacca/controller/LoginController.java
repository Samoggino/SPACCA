package com.spacca.controller;

// import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class LoginController implements Initializable {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label statusLabel;

    @FXML
    private GridPane gridPane;

    @FXML
    private void handleLogin() {
        System.out.println("Controller del login");
        try {

            String username = usernameField.getText();
            String password = passwordField.getText();

            // Esegui la logica di verifica dell'accesso qui...
            // Ad esempio, controlla se le credenziali sono corrette
            if (username.equals("admin") && password.equals("password")) {
                statusLabel.setText("Accesso effettuato con successo!");
                // Aggiungi qui il codice per passare alla schermata successiva
            } else {
                statusLabel.setText("Credenziali non valide!");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage() + "initialize");
        }
    }
    
}
