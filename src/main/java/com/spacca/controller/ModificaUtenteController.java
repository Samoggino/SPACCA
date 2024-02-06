package com.spacca.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.spacca.App;
import com.spacca.asset.utente.giocatore.Giocatore;
import com.spacca.database.GiocatoreHandler;
import com.spacca.database.Handler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ModificaUtenteController {

    @FXML
    private String usernameScelto; // estensione completa --> user- (username).json

    @FXML
    private Giocatore giocatoreScelto;

    @FXML
    private Button procedi;
    @FXML
    private Button indietro;

    @FXML
    private Label labelUsername;

    @FXML
    private TextField usernameField;

    @FXML
    private Label labelEmail;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label labelPassword;

    @FXML
    private Label title;

    transient private GiocatoreHandler handler = new GiocatoreHandler();

    @FXML
    private void procediModifica() throws FileNotFoundException {

        System.out.println("\n PROCEDI MODIFICA \n ");
        System.out.println("Utente corrente " + giocatoreScelto);
        System.out.println("Lista partite " + giocatoreScelto.getListaCodiciPartite());

        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() && email.isEmpty() && password.isEmpty()) {
            showAlert("Non hai effettutato alcuna modifica! ", AlertType.ERROR, "Non è possibile effettuare modifiche");
        } else {
            if (!email.isEmpty() && controllaInserimentoEmail(email)) {
                giocatoreScelto.setEmail(email);
                System.out.println("Utente corrente modifica mail: " + giocatoreScelto.getEmail());
            }
            if (!password.isEmpty()) {
                labelPassword.setText("Password inserita !");
                giocatoreScelto.setPassword(password);
                System.out.println("Utente corrente modifica password: " + giocatoreScelto.getPassword());
            }
            if (!username.isEmpty() && controllaInserimentoUsername(username)) {
                labelPassword.setText("Usernamente inserito !");
                giocatoreScelto.setUsername(username);
                System.out.println("Utente corrente modifica username: " + giocatoreScelto.getUsername());
            }

            handler.modifica(usernameScelto, giocatoreScelto);

            showAlert("Utente " + giocatoreScelto.getUsername() +
                    "modificato con successo!", AlertType.INFORMATION,
                    "Modifica effettuata correttamente");

            changeScene("/com/spacca/pages/benvenutoAdmin.fxml");
        }
    }

    public boolean controllaInserimentoUsername(String username) {
        try {
            labelUsername.setText("");
            labelUsername.setTextFill(Color.DARKORANGE);
            usernameField.setStyle("-fx-border-color:lightgrey");

            if (username.trim().isEmpty()) {
                labelUsername.setText("Non ha inserito lo username!");
                usernameField.setStyle("-fx-border-color:darkorange");
                return false;
            } else {

                // Verifica se il file esiste
                if (handler.VerificaEsistenzaFile(username)) {
                    System.out.println("il file esiste già");
                    Giocatore utente = (Giocatore) handler.carica(username);

                    System.out.println("utente " + utente);

                    if (utente.getUsername().equals(username)) {
                        labelUsername.setText("Username già in utilizzo! ");
                        usernameField.setStyle("-fx-border-color:darkorange");
                        return false;
                    }
                } else {
                    System.out.println("il file non esiste");
                    labelUsername.setTextFill(Color.BLACK);
                    labelUsername.setText("Username corretto! ");
                    usernameField.setStyle("-fx-border-color:lightgrey");
                    return true;
                }
            }

        } catch (Exception e) {
            System.err.println("ERRORE: Errore generico in\n" + e.getMessage());
        }
        return false;
    }

    public boolean controllaInserimentoEmail(String email) {
        try {
            labelEmail.setText("");
            labelEmail.setTextFill(Color.DARKORANGE);

            if (email.trim().isEmpty()) {
                labelEmail.setText("Non ha inserito l'indirizzo mail!");
                emailField.setStyle("-fx-border-color:darkorange");
                return false;
            } else if (email.contains("@")) {
                labelEmail.setText("Email corretta");
                labelEmail.setTextFill(Color.BLACK);
                emailField.setStyle("-fx-border-color:lightgrey");
                return true;
            } else {
                labelEmail.setText("Email non valida, reinseriscila!");
                emailField.setStyle("-fx-border-color:darkorange");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Errore (Login controller): \n" + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    private void showAlert(String content, AlertType tipo, String titolo) {
        try {
            Alert alert = new Alert(tipo);
            alert.setTitle(tipo.toString());
            alert.setHeaderText(titolo);
            alert.setContentText(content);
            alert.showAndWait();
        } catch (Exception e) {
            System.err.println("Errore (alert Modifica utente controller): \n" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleIndietro() {
        changeScene("/com/spacca/pages/benvenutoAdmin.fxml");
    }

    public void changeScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Parent root = loader.load();

            Scene currentScene = procedi.getScene();

            // Ottieni lo Stage dalla scena corrente
            Stage currentStage = (Stage) currentScene.getWindow();
            currentStage.setTitle("Benvenuto Admin ! ");
            currentStage.setScene(new Scene(root));
            currentStage.show();

        } catch (NullPointerException e) {
            System.out.println("Login avvenuto con successo!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IO Errore (Benvenuto Admin controller): \n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore (Benvenuto Admin controller): \n" + e.getMessage());
        }
    }

    @FXML
    public void initController(String username) {
        try {

            this.usernameScelto = username;

            title.setText("Modifica utente " + username);

            // in base al nome del file carico i dati dell'utente e carico il suo file per
            // la modifica

            Handler giocatoreHandler = new GiocatoreHandler();

            this.giocatoreScelto = (Giocatore) giocatoreHandler.carica(username);

            passwordField.setPromptText(this.giocatoreScelto.getPassword());
            usernameField.setPromptText(this.giocatoreScelto.getUsername());
            emailField.setPromptText(this.giocatoreScelto.getEmail());

        } catch (NullPointerException e) {
            System.out.println("utente scelto null" + e);
        } catch (Exception e) {
            System.out.println("ERRORE" + e);
        }

    }

}
