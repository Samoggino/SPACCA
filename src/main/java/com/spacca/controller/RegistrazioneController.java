package com.spacca.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class RegistrazioneController implements Initializable {

    GiocatoreHandler giocatoreHandler = new GiocatoreHandler();

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
            // usernameField.setStyle("-fx-border-color:whitegrey");
            // passwordField.setStyle("-fx-border-color:whitegrey");
            // emailField.setStyle("-fx-border-color:whitegrey");
            // confermaPasswordField.setStyle("-fx-border-color:whitegrey");
            // data.setStyle("-fx-border-color:whitegrey");

            usernameField.setStyle("-fx-border-color: white grey;");
            passwordField.setStyle("-fx-border-color: white grey;");
            emailField.setStyle("-fx-border-color: white grey;");
            confermaPasswordField.setStyle("-fx-border-color: white grey;");
            data.setStyle("-fx-border-color: white grey;");

            labelData.setText("");
            labelConfermaPassword.setText("");
            labelEmail.setText("");
            labelPassword.setText("");
            labelUsername.setText("");

        } catch (Exception e) {
            System.err.println("Error initialize: " + e.getMessage() + "initialize");
        }
    }

    @FXML
    private void handleLogin() {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();
            LocalDate annoNascita = data.getValue();

            Boolean controlloPassword = controllaInserimentoPassword(password);
            Boolean controlloEmail = controllaInserimentoEmail(email);
            Boolean controlloData = controllaData(annoNascita);
            Boolean controlloUsername = controllaInserimentoUsername(username);

            System.out.println("controlloPassword " + controlloPassword);
            System.out.println("controlloEmail " + controlloEmail);
            System.out.println("controlloData " + controlloData);
            System.out.println("controlloUsername " + controlloUsername);

            Giocatore utente = new Giocatore(username, password, email);

            // Fai qualcosa se tutte le condizioni sono vere
            if (controlloData & controlloEmail & controlloPassword & controlloUsername) {
                giocatoreHandler.salva(utente, utente.getUsername());
                if (username.equals("admin")) {
                    changeSceneAdmin("/com/spacca/pages/benvenutoAdmin.fxml", utente);
                } else {
                    changeSceneUtente("/com/spacca/pages/benvenutoUtente.fxml", utente);
                }
            }

        } catch (Exception e) {
            System.out.println("Errore (Login controller): \n" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void changeSceneUtente(String fxmlPath, Object controllerData) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Parent root = loader.load();
            // Logica per inizializzare il controller se necessario
            BenvenutoUtenteController prePartita = loader.getController();
            loader.setController(prePartita);

            prePartita.initController((Giocatore) controllerData);

            Stage currentStage = (Stage) App.getScene().getWindow();

            currentStage.setScene(new Scene(root));
            currentStage.show();
        } catch (IOException e) {
            System.err.println("Errore (changeScene registrazione controller): \n" + e.getMessage());
        }

    }

    public static void changeSceneAdmin(String fxmlPath, Object controllerData) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Parent root = loader.load();
            // Logica per inizializzare il controller se necessario
            BenvenutoAdminController prePartita = loader.getController();
            loader.setController(prePartita);

            prePartita.initController((Giocatore) controllerData);

            Stage currentStage = (Stage) App.getScene().getWindow();

            currentStage.setScene(new Scene(root));
            currentStage.show();
        } catch (IOException e) {
            System.err.println("Errore (changeScene registrazione controller): \n" + e.getMessage());
        }

    }

    private boolean controllaData(LocalDate annoNascita) {
        try {
            if (annoNascita == null) {
                // Modifica lo stile dell'elemento UI 'data' in caso di data mancante
                // e mostra un messaggio di errore
                data.setStyle("-fx-border-color: darkorange");
                labelData.setText("Data non inserita");
                labelData.setTextFill(Color.DARKORANGE);
                return false;
            } else {
                LocalDate now = LocalDate.now();
                int eta = Period.between(annoNascita, now).getYears();

                if (eta >= 18) {
                    // Modifica lo stile dell'elemento UI 'data' in caso di successo
                    data.setStyle("-fx-border-color: whitegrey");
                    labelData.setText("");
                    return true;
                } else {
                    // Mostra un avviso e passa alla schermata di login in caso di età insufficiente
                    showAlert("Non hai diciotto anni, non puoi registrarti");
                    try {
                        // Utilizzo serRotto perchè non mi servono parametri
                        App.setRoot("login");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    labelData.setText("");
                    return false;
                }
            }
        } catch (Exception e) {
            System.err.println("Errore (Login controller): \n" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean controllaInserimentoUsername(String username) {
        Boolean controllo = true;
        try {
            labelUsername.setText("");
            labelUsername.setTextFill(Color.DARKORANGE);
            usernameField.setStyle("-fx-border-color:whitegrey");
        } catch (Exception e) {
            System.err.println("Errore (Login controller): \n" + e.getMessage());
        }

        if (username.trim().isEmpty()) {
            labelUsername.setText("Non ha inserito lo username!");
            usernameField.setStyle("-fx-border-color:darkorange");
            controllo = false;
        } else {
            try {

                String path = "src/main/resources/com/spacca/database/giocatori/user-" + username + ".json";

                File userFile = new File(path);

                // Verifica se il file esiste
                if (userFile.exists() && userFile.isFile()) {
                    GiocatoreHandler file = new GiocatoreHandler();
                    Giocatore utente = file.carica(path);

                    if (utente.getUsername().equals(username)) {
                        labelUsername.setText("Username già in utilizzo! ");
                        usernameField.setStyle("-fx-border-color:darkorange");
                        controllo = false;
                    } else {
                        labelUsername.setText("Username corretto! ");
                        labelUsername.setTextFill(Color.BLACK);
                        controllo = true;
                    }
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
                passwordField.setStyle("-fx-border-color:whitegrey");
                labelConfermaPassword.setText("Non hai inserito la conferma!");
                confermaPasswordField.setStyle("-fx-border-color:darkorange");
                return false;
            } else if (password.trim().isEmpty()) {
                labelConfermaPassword.setText("");
                confermaPasswordField.setStyle("-fx-border-color:whitegrey");
                labelPassword.setText("Non hai inserito la password!");
                passwordField.setStyle("-fx-border-color:darkorange");
                return false;
            } else if (password.equals(confermaPassword)) {
                labelConfermaPassword.setText("Password di conferma corretta!");
                confermaPasswordField.setStyle("-fx-border-color:whitegrey");
                labelConfermaPassword.setTextFill(Color.BLACK);
                return true;
            } else {
                labelPassword.setText("");
                labelConfermaPassword.setText("Password errata, reinseriscila!");
                return false;
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
            if (email.trim().isEmpty()) {
                labelEmail.setText("Non ha inserito l'indirizzo mail!");
                emailField.setStyle("-fx-border-color:darkorange");
                labelEmail.setTextFill(Color.DARKORANGE);
                return false;
            } else if (email.contains("@")) {
                labelEmail.setText("");
                emailField.setStyle("-fx-border-color:whitegrey");
                return true;
            } else {
                labelEmail.setText("Email non valida, reinseriscila!");
                emailField.setStyle("-fx-border-color:darkorange");
                labelEmail.setTextFill(Color.DARKORANGE);
                return false;
            }
        } catch (Exception e) {
            System.err.println("Errore (Login controller): \n" + e.getMessage());
            e.printStackTrace();
            return false;
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

    private void showAlert(String content) {
        try {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        } catch (Exception e) {
            System.err.println("Errore (Login controller): \n" + e.getMessage());
            e.printStackTrace();
        }
    }
}