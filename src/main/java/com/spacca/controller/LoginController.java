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
            // Disabilita il pulsante di login all'inizio
            loginButton.setDisable(true);

            // Aggiungi i listener ai campi username e password
            usernameField.textProperty().addListener((observable, oldValue, newValue) -> updateLoginButton());
            passwordField.textProperty().addListener((observable, oldValue, newValue) -> updateLoginButton());

        } catch (Exception e) {
            System.err.println("Error initialize: " + e.getMessage() + "initialize");
        }
    }

    private void updateLoginButton() {
        // Abilita il pulsante di login solo se sia username che password sono presenti
        boolean isUsernameEmpty = usernameField.getText().trim().isEmpty();
        boolean isPasswordEmpty = passwordField.getText().trim().isEmpty();

        loginButton.setDisable(isUsernameEmpty || isPasswordEmpty);
    }

    @FXML
    private void handleLogin() {
        try {
            statusLabel.setTextFill(Color.BLACK);
            usernameField.setStyle("-fx-border-color:whitegrey");
            passwordField.setStyle("-fx-border-color:whitegrey");

            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.trim().isEmpty()) {
                statusLabel.setText("Inserisci tutt i dati obbligatori, non ha inserito lo username!");
                usernameField.setStyle("-fx-border-color:darkorange");
                statusLabel.setTextFill(Color.DARKORANGE);
                return;
            } else if (password.trim().isEmpty()) {
                statusLabel.setText("Inserisci tutt i dati obbligatori, non ha inserito la password!");
                passwordField.setStyle("-fx-border-color:darkorange");
                statusLabel.setTextFill(Color.DARKORANGE);
                return;
            }

            String fileName = username + ".json";

            String folderPath = "src/main/resources/com/spacca/database/giocatori/";

            File userFile = new File(folderPath + fileName);
            /*
             * if(usernameField.getText().equals("ila") &&
             * passwordField.getText().equals("ila")){
             * App.setRoot("partita");
             * }
             */

            // Verifica se il file esiste
            if (userFile.exists() && userFile.isFile()) {
                FileHandler file = new FileHandler();
                Giocatore utente = (Giocatore) file.leggiUtente(userFile);

                if (utente.getUsername().equals(username) && utente.getPassword().equals(password)) {
                    System.out.println("Utente registrato " + userFile.getAbsolutePath());
                    statusLabel.setText("Accesso effettuato con successo!");
                    App.setRoot("partita");
                } else if (utente.getUsername().equals(username)) {
                    // password Errata
                    statusLabel.setText("Password errato, riprova!");
                } else if (utente.getPassword().equals(password)) {
                    // username errato
                    statusLabel.setText("Username errato, riprova!");
                }

            } else {
                statusLabel.setText("Sei sicuro di essere registrato ? ");
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
