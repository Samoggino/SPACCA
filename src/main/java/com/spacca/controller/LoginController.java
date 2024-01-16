package com.spacca.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.spacca.App;
import com.spacca.asset.utente.giocatore.Giocatore;
import com.spacca.database.GiocatoreHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
            statusLabel.setTextFill(Color.DARKORANGE);

            String username = usernameField.getText();
            String password = passwordField.getText();

            String pathString = "src/main/resources/com/spacca/database/giocatori/user-" + username + ".json";
            File userFile = new File(pathString);

            if (password.trim().isEmpty() && username.trim().isEmpty()) {
                statusLabel.setText("Non hai compilato i campi!");
            } else if (password.trim().isEmpty()) {
                statusLabel.setText("Non hai inserito la password!");
                passwordField.setStyle("-fx-border-color:darkorange");
            } else if (username.trim().isEmpty()) {
                statusLabel.setText("Non hai inserito il nome utente!");
                usernameField.setStyle("-fx-border-color:darkorange");
            } else if (userFile.exists() && userFile.isFile()) { // Verifica se il file esiste

                GiocatoreHandler file = new GiocatoreHandler();
                Giocatore utente = (Giocatore) file.carica(pathString);

                if (utente.getUsername().equals(username) && utente.getPassword().equals(password)) {

                    // App.setRoot("benvenuto");
                    changeScene("/com/spacca/pages/prepartita.fxml", utente);

                    System.out.println("Benvenuto " + utente.getUsername());
                    statusLabel.setText("Benvenuto " + username);

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
            // } catch (IOException e) {
            // System.err.println("Errore IOException: \n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore (Login controller): \n" + e.getMessage());
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

    public static void changeScene(String fxmlPath, Object controllerData) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Parent root = loader.load();
            // Logica per inizializzare il controller se necessario
            PrePartitaController prePartita = loader.getController();
            loader.setController(prePartita);

            prePartita.initController((Giocatore) controllerData);

            Stage currentStage = (Stage) App.getScene().getWindow();

            currentStage.setScene(new Scene(root));
            currentStage.show();
        } catch (IOException e) {
            System.err.println("Errore (changeScene): \n" + e.getMessage());
        }

    }

}
