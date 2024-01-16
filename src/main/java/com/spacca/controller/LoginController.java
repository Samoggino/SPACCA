package com.spacca.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.spacca.App;
import com.spacca.asset.utente.giocatore.Giocatore;
import com.spacca.database.FileHandler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class LoginController implements Initializable {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label statusLabel;

    @FXML
    private GridPane gridPane;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {

        } catch (Exception e) {
            System.err.println("Error initialize: " + e.getMessage() + "initialize");
        }
    }

    @FXML
    private void handleLogin() {
        try {
            statusLabel.setTextFill(Color.BLACK);
            usernameField.setStyle("-fx-border-color:whitegrey");
            passwordField.setStyle("-fx-border-color:whitegrey");

            String username = usernameField.getText();
            String password = passwordField.getText();

            statusLabel.setTextFill(Color.DARKORANGE);

            String fileName = username + ".json";

            String folderPath = "src/main/resources/com/spacca/database/giocatori/";

            File userFile = new File(folderPath + fileName);

            if (password.trim().isEmpty() && username.trim().isEmpty()) {
                statusLabel.setText("Non hai compilato i campi!");
            } else if (password.trim().isEmpty()) {
                statusLabel.setText("Non hai inserito la password!");
                passwordField.setStyle("-fx-border-color:#eba400");
            } else if (username.trim().isEmpty()) {
                statusLabel.setText("Non hai inserito il nome utente!");
                usernameField.setStyle("-fx-border-color:#eba400");
            } else if (userFile.exists() && userFile.isFile()) { // Verifica se il file esiste
                FileHandler file = new FileHandler();
                Giocatore utente = file.leggiUtente(userFile);

                if (utente.getUsername().equals(username) && utente.getPassword().equals(password)) {
                    // System.out.println("Utente registrato " + userFile.getAbsolutePath());
                    if (utente.getUsername().equals("admin")) {
                        App.setRoot("benvenutoAdmin");
                    } else {
                        App.setRoot("benvenutoUtente");
                    }

                } else if (utente.getUsername().equals(username)) {
                    // password Errata
                    statusLabel.setText("Password errato, riprova!");
                    statusLabel.setTextFill(Color.DARKORANGE);
                } else if (utente.getPassword().equals(password)) {
                    // username errato
                    statusLabel.setText("Username errato, riprova!");
                }
            } else {
                statusLabel.setText("Sei sicuro di esser registrato ?");
            }
        } catch (IOException e) {
            System.out.println("Errore IOException: \n" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Errore (Login controller): \n" + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegistrazione() {
        try {
            App.setRoot("registrazioneUtente");
        } catch (IOException e) {
            e.getStackTrace();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

}
