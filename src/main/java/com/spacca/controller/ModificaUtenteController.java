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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ModificaUtenteController implements Initializable {

    @FXML
    private String utenteScelto;

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

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }

    @FXML
    private void procediModifica() {

        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        controllaInserimentoPassword(password);
        controllaInserimentoEmail(email);
        controllaInserimentoUsername(username);

        System.out.println("" + controllaInserimentoPassword(password) +
                controllaInserimentoEmail(email) +
                controllaInserimentoUsername(username));

        if (username.isEmpty() && email.isEmpty() && password.isEmpty()) {
            showAlert("Non hai effettutato alcuna modifica! ");
        } else {
            if (controllaInserimentoPassword(password) &&
                    controllaInserimentoEmail(email) &&
                    controllaInserimentoUsername(username)) {

                giocatoreScelto.setEmail(email);
                giocatoreScelto.setPassword(password);
                giocatoreScelto.setUsername(username);

                GiocatoreHandler giocatoreHandler = new GiocatoreHandler();
                giocatoreHandler.elimina(utenteScelto);
                giocatoreHandler.salva(giocatoreScelto, username);

                showAlert("Giocatore Modificato con successo !");
                handleIndietro();
            }
        }
    }

    public boolean controllaInserimentoUsername(String username) {
        try {
            labelUsername.setText("");
            labelUsername.setTextFill(Color.DARKORANGE);
            usernameField.setStyle("-fx-border-color:whitegrey");

            if (username.trim().isEmpty()) {
                labelUsername.setText("Non ha inserito lo username!");
                usernameField.setStyle("-fx-border-color:darkorange");
                return false;
            } else {
                String path = "src/main/resources/com/spacca/database/giocatori/user-" + username + ".json";

                File userFile = new File(path);

                // Verifica se il file esiste
                if (userFile.exists() && userFile.isFile()) {
                    GiocatoreHandler file = new GiocatoreHandler();
                    Giocatore utente = file.carica(path);

                    System.out.println(utente);

                    if (utente.getUsername().equals(username)) {
                        labelUsername.setText("Username già in utilizzo! ");
                        usernameField.setStyle("-fx-border-color:darkorange");
                        return false;
                    }
                } else {
                    labelUsername.setTextFill(Color.BLACK);
                    System.out.println("siamo qui perchè non stampi?");
                    labelUsername.setText("Username corretto! ");
                    usernameField.setStyle("-fx-border-color:whitegrey");
                    return true;
                }
            }

        } catch (Exception e) {
            System.err.println("ERRORE: Errore generico in\n" + e.getMessage());
        }
        return false;
    }

    public boolean controllaInserimentoPassword(String password) {
        try {
            labelPassword.setTextFill(Color.DARKORANGE);
            labelPassword.setText("");

            if (password.trim().isEmpty()) {
                labelPassword.setText("Non hai inserito la password!");
                passwordField.setStyle("-fx-border-color:darkorange");
                return false;
            } else {
                labelPassword.setText("Password inserita correttamente");
                labelPassword.setTextFill(Color.BLACK);
                passwordField.setStyle("-fx-border-color:whitegrey");
                return true;
            }
        } catch (Exception e) {
            System.err.println("Errore (Login controller): \n" + e.getMessage());
            e.printStackTrace();
            return false;
        }
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
                emailField.setStyle("-fx-border-color:whitegrey");
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

    private void showAlert(String content) {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("");
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
    public void initController(String nomeFile) {
        try {

            this.utenteScelto = nomeFile;

            String senzaUser = utenteScelto.replace("user-", "");
            // Rimuovi ".json" dalla fine della stringa
            String senzaJson = senzaUser.replace(".json", "");

            title.setText("Modifica utente " + senzaJson);

            // in base al nome del file carico i dati dell'utente e carico il suo file per
            // la modifica

            String path = "src/main/resources/com/spacca/database/giocatori/" + nomeFile;
            GiocatoreHandler giocatoreHandler = new GiocatoreHandler();

            this.giocatoreScelto = giocatoreHandler.carica(path);

            passwordField.setPromptText(giocatoreScelto.getPassword());
            usernameField.setPromptText(giocatoreScelto.getUsername());
            emailField.setPromptText(giocatoreScelto.getEmail());

        } catch (NullPointerException e) {
            System.out.println("utente scelto null" + e);
        } catch (Exception e) {
            System.out.println("ERRORE" + e);
        }

    }

}
