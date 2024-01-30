package com.spacca.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.spacca.asset.utente.Amministratore;
import com.spacca.asset.utente.giocatore.Giocatore;
import com.spacca.database.GiocatoreHandler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class CreazioneUtenteRobotController implements Initializable {

    transient private Amministratore admin = new Amministratore();

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
        admin.ritornaBenvenutoAdmin(indietroButton.getScene());
    }

    @FXML
    private void handleCrea() {
        try {
            usernameLabel.setText("");
            usernameLabel.setTextFill(Color.BLACK);
            usernameField.setStyle("-fx-border-color: lightgrey;");

            String username = usernameField.getText();

            if (controllaInserimentoUsername(username)) {
                if (sceltaRobotIntelligente.isSelected()) {
                    System.out.println("crea robot intelligente selezionato ");
                    // SmartCPU utenteSmartCPU = new SmartCPU(username);
                    // Per ora carica e salva un abstract, manca il tipo
                    // TODO
                    admin.creaUtenteRobot(username);

                    showAlert("Utente Robot Intelligente" + username + "\n salvato con successo!", "",
                            AlertType.INFORMATION);
                    admin.ritornaBenvenutoAdmin(creaButton.getScene());

                } else if (sceltaRobotBasico.isSelected()) {

                    System.out.println("crea robot stupido selezionato ");
                    // TODO
                    // StupidCPU utenteStupidCPU = new StupidCPU(username);
                    // System.out.println("utenteSmartCPU " + utenteStupidCPU);

                    admin.creaUtenteRobot(username);

                    showAlert("Utente Robot Basico" + username + "\n salvato con successo!", "", AlertType.INFORMATION);
                    admin.ritornaBenvenutoAdmin(creaButton.getScene());
                } else {

                    showAlert(
                            "Non puoi creare alcun Utente Robot \n se non selezioni il tipo!",
                            "Mancata selezione del tipo di utente robot ", AlertType.ERROR);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Salvataggio errato");
        }
    }

    public boolean controllaInserimentoUsername(String username) {
        Boolean controllo = true;
        System.out.println("\n \n" + //
                " CONTROLLO INRIMENTO USERNAME " + username + "\n \n");

        usernameLabel.setText("");
        usernameLabel.setTextFill(Color.DARKORANGE);
        usernameField.setStyle("-fx-border-color: lightgrey");

        if (username.trim().isEmpty()) {
            usernameLabel.setText("Non ha inserito lo username!");
            usernameField.setStyle("-fx-border-color:darkorange");
            controllo = false;
        } else {
            try {

                GiocatoreHandler file = new GiocatoreHandler();

                // Verifica se il file esiste
                if (file.VerificaEsistenzaFile(username)) {

                    Giocatore utente = (Giocatore) file.carica(username);
                    System.out.println("GIOCATORE" + utente);

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

            } catch (Exception e) {
                System.err.println("ERRORE: Errore generico in\n" + e.getMessage());
            }
        }
        return controllo;
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
