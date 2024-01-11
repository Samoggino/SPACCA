package com.spacca.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

import com.spacca.App;
import com.spacca.asset.utente.giocatore.Giocatore;
import com.spacca.database.FileHandler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class RegistrazioneController implements Initializable {
    @FXML
    private Button save;
    @FXML
    private Button indietro;
    @FXML
    private Label labelUsername;
    @FXML
    private Label labelEmail;
    @FXML
    private Label labelPassword;
    @FXML
    private Label labelConfermaPassword;
    @FXML
    private Label labelData;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField confermaPasswordField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private DatePicker data;

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
            usernameField.setStyle("-fx-border-color:whitegrey");
            passwordField.setStyle("-fx-border-color:whitegrey");
            emailField.setStyle("-fx-border-color:whitegrey");
            confermaPasswordField.setStyle("-fx-border-color:whitegrey");
            data.setStyle("-fx-border-color:whitegrey");
            labelData.setText("");
            labelConfermaPassword.setText("");
            labelEmail.setText("");
            labelPassword.setText("");
            labelUsername.setText("");

            String username = usernameField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();
            LocalDate annoNascita = data.getValue();

            controllaInserimentoUsername(username);
            controllaInserimentoPassword(password);
            controllaInserimentoEmail(email);
            controllaData(annoNascita);

            Giocatore utente = new Giocatore(username, password);

            FileHandler fileHandler = new FileHandler();
            fileHandler.salvaUtente(utente);

        } catch (Exception e) {
            System.out.println("Errore (Login controller): \n" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void controllaData(LocalDate annoNascita) {
        if (annoNascita == null) {
            data.setStyle("-fx-border-color: darkorange");
            labelData.setText("Data non inserita");
            labelData.setTextFill(Color.DARKORANGE);
            return;
        } else {
            LocalDate now = LocalDate.now();
            int eta = Period.between(annoNascita, now).getYears();

            if (eta >= 18) {
                data.setStyle("-fx-border-color: whitegrey");
                labelData.setText("");
            } else {
                showAlert("Non hai diciotto anni, non puoi registrarti");
                try {
                    App.setRoot("login");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                labelData.setText("");
            }
        }
    }

    private void showAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void controllaInserimentoUsername(String username) {
        if (username.trim().isEmpty()) {
            labelUsername.setText("Non ha inserito lo username!");
            usernameField.setStyle("-fx-border-color:darkorange");
            labelUsername.setTextFill(Color.DARKORANGE);
            return;
        } else {
            try {
                String fileName = username + ".json";

                String folderPath = "src/main/resources/com/spacca/database/giocatori/";

                File userFile = new File(folderPath + fileName);

                // Verifica se il file esiste
                if (userFile.exists() && userFile.isFile()) {
                    FileHandler file = new FileHandler();
                    Giocatore utente = (Giocatore) file.leggiUtente(userFile);

                    System.out.println("Sono dentro al file ovvero file esiste");

                    if (utente.getUsername().equals(username)) {
                        labelUsername.setText("Username già in utilizzo! ");
                        usernameField.setStyle("-fx-border-color:darkorange");
                        labelUsername.setTextFill(Color.DARKORANGE);
                        return;
                    } else {
                        labelUsername.setText("Username corretto! ");
                        labelUsername.setTextFill(Color.BLACK);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void controllaInserimentoPassword(String password) {
        String confermaPassword = confermaPasswordField.getText();
        if (password.trim().isEmpty() && confermaPassword.trim().isEmpty()) {
            labelPassword.setText("Non hai inserito la password!");
            passwordField.setStyle("-fx-border-color:darkorange");
            labelPassword.setTextFill(Color.DARKORANGE);
            labelConfermaPassword.setText("Non hai inserito la conferma!");
            confermaPasswordField.setStyle("-fx-border-color:darkorange");
            labelConfermaPassword.setTextFill(Color.DARKORANGE);
            return;
        } else if (confermaPassword.trim().isEmpty()) {
            labelConfermaPassword.setText("Non hai inserito la conferma!");
            confermaPasswordField.setStyle("-fx-border-color:darkorange");
            labelConfermaPassword.setTextFill(Color.DARKORANGE);
            return;
        } else if (password.trim().isEmpty()) {
            labelPassword.setText("Non hai inserito la password!");
            passwordField.setStyle("-fx-border-color:darkorange");
            labelPassword.setTextFill(Color.DARKORANGE);
            return;
        } else if (password.equals(confermaPassword)) {
            labelConfermaPassword.setText("Password di conferma corretta!");
            confermaPasswordField.setStyle("-fx-border-color:whitegrey");
            labelConfermaPassword.setTextFill(Color.BLACK);
        } else {
            labelPassword.setText("");
            labelConfermaPassword.setText("Password errata, reinseriscila!");
            return;
        }
    }

    public void controllaInserimentoEmail(String email) {
        labelEmail.setText("");
        if (email.trim().isEmpty()) {
            labelEmail.setText("Non ha inserito l'indirizzo mail!");
            emailField.setStyle("-fx-border-color:darkorange");
            labelEmail.setTextFill(Color.DARKORANGE);
            return;
        } else if (email.contains("@")) {
            labelEmail.setText("");
            emailField.setStyle("-fx-border-color:whitegrey");
        } else {
            labelEmail.setText("Email non valida, reinseriscila!");
            emailField.setStyle("-fx-border-color:darkorange");
            labelEmail.setTextFill(Color.DARKORANGE);
        }

    }

    public void tornaIndietro() {
        try {
            App.setRoot("login");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
