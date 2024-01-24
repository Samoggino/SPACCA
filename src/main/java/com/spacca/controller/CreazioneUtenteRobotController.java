package com.spacca.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.spacca.App;
import com.spacca.asset.utente.giocatore.Giocatore;
import com.spacca.asset.utente.giocatore.SmartCPU;
import com.spacca.asset.utente.giocatore.StupidCPU;
import com.spacca.database.GiocatoreHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CreazioneUtenteRobotController implements Initializable {

    private GiocatoreHandler giocatoreHandler = new GiocatoreHandler();

    @FXML
    private Button creaButton;

    @FXML
    private Button indietroButton;

    @FXML
    private RadioButton sceltaRobotIntelligente;

    @FXML
    private RadioButton sceltaRobotBasico;

    @FXML
    private TextField usernameField;

    @FXML
    private Label usernameLabel;

    @FXML
    private void handleIndietro() {
        try {
            changeSceneAdmin();
        } catch (Exception e) {
            System.err.println("Cambio scena in benvenuto admin errato");
        }
    }

    @FXML
    private void handleCrea() {
        try {
            usernameLabel.setText("");
            usernameLabel.setTextFill(Color.BLACK);
            usernameField.setStyle("-fx-border-color: lightgrey;");

            String username = usernameField.getText();

            if (sceltaRobotIntelligente.isSelected()) {
                System.out.println("crea robot intelligente selezionato ");
                controllaInserimentoUsername(username);
                username = "RI-" + username;
                SmartCPU utenteSmartCPU = new SmartCPU(username);
                giocatoreHandler.salva(utenteSmartCPU, username);
                showAlert("Utente Robot Intelligente" + username + "\n salvato con successo!", "",
                        AlertType.INFORMATION);
                changeSceneAdmin();

            } else if (sceltaRobotBasico.isSelected()) {

                System.out.println("crea robot stupido selezionato ");
                controllaInserimentoUsername(username);
                StupidCPU utenteStupidCPU = new StupidCPU(username);
                System.out.println("utenteSmartCPU " + utenteStupidCPU);
                username = "RS-" + username;
                giocatoreHandler.salva(utenteStupidCPU, username);
                showAlert("Utente Robot Basico" + username + "\n salvato con successo!", "", AlertType.INFORMATION);
                changeSceneAdmin();
            } else {

                showAlert(
                        "Non puoi creare alcun Utente Robot \n se non selezioni il tipo!",
                        "Mancata selezione del tipo di utente robot ", AlertType.ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Salvataggio errato");
        }
    }

    public boolean controllaInserimentoUsername(String username) {
        Boolean controllo = true;
        try {
            usernameLabel.setText("");
            usernameLabel.setTextFill(Color.DARKORANGE);
            usernameField.setStyle("-fx-border-color: lightgrey;");

            if (username.trim().isEmpty()) {
                usernameLabel.setText("Non ha inserito lo username!");
                usernameField.setStyle("-fx-border-color:darkorange");
                controllo = false;
            } else {

                String path = "src/main/resources/com/spacca/database/giocatori/user-" + username + ".json";

                File userFile = new File(path);

                // Verifica se il file esiste
                if (userFile.exists() && userFile.isFile()) {
                    GiocatoreHandler file = new GiocatoreHandler();
                    Giocatore utente = file.carica(path);

                    if (utente.getUsername().equals(username)) {
                        usernameLabel.setText("Username già in utilizzo! ");
                        usernameField.setStyle("-fx-border-color:darkorange");
                        controllo = false;
                    }
                } else {
                    usernameLabel.setText("Username corretto! ");
                    usernameLabel.setTextFill(Color.BLACK);
                    controllo = true;
                }

            }

        } catch (Exception e) {
            System.err.println("ERRORE: Errore generico in\n" + e.getMessage());
        }
        return controllo;
    }

    private void changeSceneAdmin() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/spacca/pages/benvenutoAdmin.fxml"));
            Parent root = loader.load();

            Scene currentScene = indietroButton.getScene();

            BenvenutoAdminController benvenutoAdminController = loader.getController();
            loader.setController(benvenutoAdminController);

            // Ottieni lo Stage dalla scena corrente
            Stage currentStage = (Stage) currentScene.getWindow();
            currentStage.setTitle("Benvenuto Admin !");
            currentStage.setScene(new Scene(root));
            currentStage.show();
        } catch (IOException e) {
            System.err.println("File benvenuto admin non torvato");
        }
    }

    @FXML
    private void handleRadioButtonAction(MouseEvent event) {
        if (event.getSource() instanceof RadioButton) {
            RadioButton clickedCheckBox = (RadioButton) event.getSource();
            if (clickedCheckBox.isSelected()) {
                if (clickedCheckBox == sceltaRobotIntelligente) {
                    System.out.println("robot intelligente selezionato ");
                    sceltaRobotBasico.setSelected(false);
                } else if (clickedCheckBox == sceltaRobotBasico) {
                    System.out.println("robot stupido selezionato ");
                    sceltaRobotIntelligente.setSelected(false);
                }
            }
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        usernameLabel.setText("");
        usernameLabel.setTextFill(Color.BLACK);
        usernameField.setStyle("-fx-border-color: lightgrey;");
    }

    private void showAlert(String content, String titolo, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(tipo.toString());
        alert.setHeaderText(titolo);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
