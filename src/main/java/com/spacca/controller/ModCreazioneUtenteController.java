package com.spacca.controller;

import java.io.IOException;

import com.spacca.App;
import com.spacca.asset.utente.Amministratore;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ModCreazioneUtenteController {

    transient private Amministratore admin = new Amministratore();

    @FXML
    private Button procediButton, indietroButton;

    @FXML
    private CheckBox sceltaRobot, sceltaUtente;

    @FXML
    private void handleIndietro() {
        admin.ritornaBenvenutoAdmin(indietroButton.getScene());
    }

    @FXML
    void handleProcedi() {
        if (sceltaUtente.isSelected()) {
            System.out.println("Scelta utente selezionato");
            changeSceneUtente("/com/spacca/pages/creazioneUtente.fxml", "Creazione Utente fisico");
        } else if (sceltaRobot.isSelected()) {
            System.out.println("Scelta robot selezionato");
            System.out.println("Scelta utente selezionato");
            changeSceneRobot("/com/spacca/pages/creazioneUtenteRobot.fxml", "Creazione Utente Robot");
        } else if (!sceltaUtente.isSelected() && !sceltaRobot.isSelected()) {
            showAlert(
                    "Non puoi creare alcun utente \\n" + //
                            "se non selezioni il tipo !",
                    "Mancata selezione del tipo di utente", AlertType.ERROR);
        }

    }

    public void changeSceneUtente(String fxmlPath, String titlePages) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Parent root = loader.load();

            Scene currentScene = procediButton.getScene();

            CreazioneUtenteController selezionaUenteController = loader.getController();
            loader.setController(selezionaUenteController);

            // Ottieni lo Stage dalla scena corrente
            Stage currentStage = (Stage) currentScene.getWindow();
            currentStage.setTitle(titlePages);
            currentStage.setScene(new Scene(root));
            currentStage.show();

        } catch (NullPointerException e) {
            System.out.println("NullPointerExceptionMod in modCreazioneUtenteController per creazione utente normale!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(
                    "IO Errore (modCreazioneUtenteController per creazione utente normale ): \n" + e.getMessage());
        } catch (Exception e) {
            System.err
                    .println("Errore (modCreazioneUtenteController per creazione utente normale): \n" + e.getMessage());
        }
    }

    public void changeSceneRobot(String fxmlPath, String titlePages) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Parent root = loader.load();

            Scene currentScene = procediButton.getScene();

            CreazioneUtenteRobotController selezionaUenteController = loader.getController();
            loader.setController(selezionaUenteController);

            // Ottieni lo Stage dalla scena corrente
            Stage currentStage = (Stage) currentScene.getWindow();
            currentStage.setTitle(titlePages);
            currentStage.setScene(new Scene(root));
            currentStage.show();

        } catch (NullPointerException e) {
            System.out.println("NullPointerExceptionMod in modCreazioneUtenteController per creazione utente Robot!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(
                    "IO Errore (modCreazioneUtenteController per creazione utente Robot): \n" + e.getMessage());
        } catch (Exception e) {
            System.err
                    .println("Errore (modCreazioneUtenteController per creazione utente Robot ): \n" + e.getMessage());
        }
    }

    private void showAlert(String content, String titolo, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(tipo.toString());
        alert.setHeaderText(titolo);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleCheckBoxAction(MouseEvent event) {
        if (event.getSource() instanceof CheckBox) {
            CheckBox clickedCheckBox = (CheckBox) event.getSource();
            if (clickedCheckBox.isSelected()) {
                if (clickedCheckBox == sceltaRobot) {
                    sceltaUtente.setSelected(false);
                } else if (clickedCheckBox == sceltaUtente) {
                    sceltaRobot.setSelected(false);
                }
            }
        }
    }

}
