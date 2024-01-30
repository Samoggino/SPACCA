package com.spacca.controller;

import java.io.File;
import java.io.IOException;

import com.spacca.App;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.asset.utente.giocatore.Giocatore;
import com.spacca.database.GiocatoreHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label statusLabel;

    public LoginController() {
    }

    @FXML
    private void handleLogin() {
        try {
            statusLabel.setTextFill(Color.BLACK);
            usernameField.setStyle("-fx-border-color: whitegrey");
            passwordField.setStyle("-fx-border-color: whitegrey");
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
                Giocatore utente = (Giocatore) file.carica(username);

                System.out.println("\n SONO IN HANDLE LOGIN \n" + utente);

                if (utente.getUsername().equals(username) && utente.getPassword().equals(password)) {

                    if (username.equals("admin")) {
                        System.out.println("SEI UN AMMINISTRATORE");
                        changeSceneAdmin("/com/spacca/pages/benvenutoAdmin.fxml", utente);
                    } else {
                        System.out.println("SEI UN UTENTE");
                        changeSceneUtente("/com/spacca/pages/benvenutoUtente.fxml", utente);
                    }

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

    public void changeSceneUtente(String fxmlPath, Object controllerData) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Parent root = loader.load();
            // Logica per inizializzare il controller se necessario
            BenvenutoUtenteController prePartita = loader.getController();
            loader.setController(prePartita);
            prePartita.initController((Giocatore) controllerData);

            Scene currentScene = loginButton.getScene();

            // Ottieni lo Stage dalla scena corrente
            Stage currentStage = (Stage) currentScene.getWindow();

            currentStage.setTitle("Benvenuto " + ((AbstractGiocatore) controllerData).getUsername() + "!");

            currentStage.setScene(new Scene(root));
            currentStage.show();
        } catch (NullPointerException e) {
            System.out.println("Login avvenuto con successo!");
        } catch (IOException e) {
            System.err.println("Errore (changeScene login): \n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore (changeScene login): \n" + e.getMessage());
        }
    }

    public void changeSceneAdmin(String fxmlPath, Object controllerData) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Parent root = loader.load();
            // Logica per inizializzare il controller se necessario
            BenvenutoAdminController prePartita = loader.getController();
            loader.setController(prePartita);

            Scene currentScene = loginButton.getScene();

            // Ottieni lo Stage dalla scena corrente
            Stage currentStage = (Stage) currentScene.getWindow();

            currentStage.setTitle("Benvenuto " + ((AbstractGiocatore) controllerData).getUsername() + "!");
            currentStage.setScene(new Scene(root));
            currentStage.show();
        } catch (NullPointerException e) {
            System.out.println("Login avvenuto con successo!");
        } catch (IOException e) {
            System.err.println("Errore (changeScene login): \n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore (changeScene login): \n" + e.getMessage());
        }

    }

}