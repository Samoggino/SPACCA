package com.spacca.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.spacca.App;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class BenvenutoUtenteController {

    List<AbstractGiocatore> giocatoriDellaPartita = new ArrayList<>(); // Inizializzazione della lista
    AbstractGiocatore giocatoreCorrente;
    TavoloController partitaController;

    @FXML
    private Button gioca;

    @FXML
    private void handleGiocaButton() {
        // Mostra lo stage del popup
        try {
            if (giocatoreCorrente.getListaCodiciPartite().isEmpty()
                    && giocatoreCorrente.getListaCodiciTornei().isEmpty()) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText("Al momento non hai alcuna partita da giocare.");
                alert.setContentText(
                        "\n Siamo spiacenti, ma non sei stato inserito in alcuna partita. \n Pertanto, non hai la possibilità di giocare.  \n");
                alert.showAndWait();
            } else {
                changeScene("Modalità partita", "/com/spacca/pages/modpartita.fxml");

            }
        } catch (NullPointerException e) {
            System.err.println("Giocatore caricato null: " + e.getLocalizedMessage() + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Errore durante il caricamento di modpartita.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void changeScene(String title, String path) {
        try {
            System.out.println("Siamo dentro Benvenuto utente controller change scene");
            // Carica il nuovo layout FXML per il PartitaController
            FXMLLoader loader = new FXMLLoader(App.class.getResource(path));
            Parent root = loader.load();

            System.out.println("Benvenuto utente controller change scene giocatore corrente : " + giocatoreCorrente);

            ModPartitaController modController = loader.getController();
            loader.setController(partitaController);

            modController.initController(giocatoreCorrente);
            // Ottieni la scena corrente
            Scene currentScene = gioca.getScene();

            // Ottieni lo Stage dalla scena corrente
            Stage currentStage = (Stage) currentScene.getWindow();
            currentStage.setTitle(title);
            currentStage.setScene(new Scene(root));
            Image icon = new Image(getClass().getResourceAsStream("/com/spacca/images/logo/logo.png"), 100, 100, true,
                    true);
            // Imposta l'icona dell'applicazione
            currentStage.getIcons().add(icon);
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void initController(AbstractGiocatore giocatoreCorrente) {

        try {
            this.giocatoreCorrente = giocatoreCorrente;
            // this.giocatoriDellaPartita.add(this.giocatoreCorrente);
        } catch (Exception e) {
            System.err.println("ERRORE (pre):\t\t " + e.getMessage());
        }
    }

}