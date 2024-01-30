package com.spacca.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.spacca.asset.utente.Amministratore;
import com.spacca.asset.utente.giocatore.Giocatore;
import com.spacca.database.GiocatoreHandler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;

public class CreazioneUtenteController implements Initializable {

    GiocatoreHandler giocatoreHandler = new GiocatoreHandler();

    @FXML
    private Button registra, indietro;
    @FXML
    private Label labelUsername, labelEmail, labelPassword, labelConfermaPassword;
    @FXML
    private TextField usernameField, emailField;
    @FXML
    private PasswordField confermaPasswordField, passwordField;
    @FXML
    private Giocatore giocatoreCorrente;

    transient private Amministratore admin = new Amministratore();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            usernameField.setStyle("-fx-border-color: white grey;");
            passwordField.setStyle("-fx-border-color: white grey;");
            emailField.setStyle("-fx-border-color: white grey;");
            confermaPasswordField.setStyle("-fx-border-color: white grey;");

            labelConfermaPassword.setText("");
            labelEmail.setText("");
            labelPassword.setText("");
            labelUsername.setText("");

        } catch (Exception e) {
            System.err.println("Error initialize: " + e.getMessage() + "initialize");
        }
    }

    @FXML
    private void handleRegistra() {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();

            Boolean controlloPassword = controllaInserimentoPassword(password);
            Boolean controlloEmail = controllaInserimentoEmail(email);
            Boolean controlloUsername = controllaInserimentoUsername(username);

            System.out.println("controlloPassword " + controlloPassword);
            System.out.println("controlloEmail " + controlloEmail);
            System.out.println("controlloUsername " + controlloUsername);

            // Fai qualcosa se tutte le condizioni sono vere
            if (controlloEmail & controlloPassword & controlloUsername) {

                admin.creaUtenteFisico(username, password, email);

                showAlert("Utente " + username + " salvato correttamente !", AlertType.INFORMATION);
                tornaIndietro();
            }

        } catch (Exception e) {
            System.out.println("Errore: \n" + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean controllaInserimentoUsername(String username) {
        Boolean controllo = true;
        System.out.println("\n \n CONTROLLO INSERIMENTO USERNAME " + username + "\n \n");

        labelUsername.setText("");
        labelUsername.setTextFill(Color.DARKORANGE);
        usernameField.setStyle("-fx-border-color: lightgrey");

        if (username.trim().isEmpty()) {
            labelUsername.setText("Non ha inserito lo username!");
            usernameField.setStyle("-fx-border-color:darkorange");
            controllo = false;
        } else {
            try {
                GiocatoreHandler file = new GiocatoreHandler();
                System.out.println("VERIFICA ESISTENZA FILE " + file.VerificaEsistenzaFile(username));
                // Verifica se il file esiste
                if (file.VerificaEsistenzaFile(username)) {

                    Giocatore utente = file.carica(username);
                    System.out.println("USERNAME: " + username);
                    System.out.println("UTENTE USERNAME: " + utente.getUsername());

                    if (utente.getUsername().equals(username)) {
                        labelUsername.setText("Username già in utilizzo! ");
                        usernameField.setStyle("-fx-border-color:darkorange");
                        controllo = false;
                    }

                } else {
                    labelUsername.setText("Username corretto! ");
                    labelUsername.setTextFill(Color.BLACK);
                    controllo = true;
                }

            } catch (Exception e) {
                System.err.println("ERRORE: Errore generico in\n" + e.getMessage());
            }
        }
        return controllo;
    }

    public boolean controllaInserimentoPassword(String password) {
        try {
            labelConfermaPassword.setTextFill(Color.DARKORANGE);
            labelPassword.setTextFill(Color.DARKORANGE);
            String confermaPassword = confermaPasswordField.getText();

            if (password.trim().isEmpty() && confermaPassword.trim().isEmpty()) {
                labelPassword.setText("Non hai inserito la password!");
                passwordField.setStyle("-fx-border-color:darkorange");
                labelConfermaPassword.setText("Non hai inserito la conferma!");
                confermaPasswordField.setStyle("-fx-border-color:darkorange");
                return false;
            } else if (confermaPassword.trim().isEmpty()) {
                labelPassword.setText("");
                passwordField.setStyle("-fx-border-color:lightgrey");
                labelConfermaPassword.setText("Non hai inserito la conferma!");
                confermaPasswordField.setStyle("-fx-border-color:darkorange");
                return false;
            } else if (password.trim().isEmpty()) {
                labelConfermaPassword.setText("");
                confermaPasswordField.setStyle("-fx-border-color:lightgrey");
                labelPassword.setText("Non hai inserito la password!");
                passwordField.setStyle("-fx-border-color:darkorange");
                return false;
            } else if (password.equals(confermaPassword)) {
                labelConfermaPassword.setText("Password di conferma corretta!");
                confermaPasswordField.setStyle("-fx-border-color:lightgrey");
                labelConfermaPassword.setTextFill(Color.BLACK);
                return true;
            } else {
                labelPassword.setText("");
                labelConfermaPassword.setText("Password errata, reinseriscila!");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Errore controllo password: \n" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean controllaInserimentoEmail(String email) {
        try {
            labelEmail.setText("");
            if (email.trim().isEmpty()) {
                labelEmail.setText("Non ha inserito l'indirizzo mail!");
                emailField.setStyle("-fx-border-color:darkorange");
                labelEmail.setTextFill(Color.DARKORANGE);
                return false;
            } else if (email.contains("@")) {
                labelEmail.setText("");
                emailField.setStyle("-fx-border-color:lightgrey");
                return true;
            } else {
                labelEmail.setText("Email non valida, reinseriscila!");
                emailField.setStyle("-fx-border-color:darkorange");
                labelEmail.setTextFill(Color.DARKORANGE);
                return false;
            }
        } catch (Exception e) {
            System.err.println("Errore controllaInserimentoEmail: \n" + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    public void tornaIndietro() throws IOException {
        admin.ritornaBenvenutoAdmin(indietro.getScene());
    }

    private void showAlert(String content, AlertType tipo) {
        try {
            Alert alert = new Alert(tipo);
            alert.setTitle(content);
            alert.setContentText(content);
            alert.showAndWait();
        } catch (Exception e) {
            System.err.println("Errore (Login controller): \n" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void initController() {
    }

}
