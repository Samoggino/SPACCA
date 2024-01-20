package com.spacca.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.spacca.App;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BenvenutoUtenteController implements Initializable {

    List<AbstractGiocatore> giocatoriDellaPartita = new ArrayList<>(); // Inizializzazione della lista
    AbstractGiocatore giocatoreCorrente;
    PartitaController partitaController;

    @FXML
    private Button gioca;

    @FXML
    private void initialize() throws IOException {
        // Imposta il titolo della finestra principale
        App.getStage().setTitle("Benvenuto");
    }

    @FXML
    private void handleGiocaButton() {
        // Mostra lo stage del popup
        try {
            changeScene("/com/spacca/pages/modpartita.fxml");
        } catch (Exception e) {
            System.err.println("Errore durante il caricamento di modpartita.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void changeScene(String path) {
        try {
            // Carica il nuovo layout FXML per il PartitaController
            FXMLLoader loader = new FXMLLoader(App.class.getResource(path));
            Parent root = loader.load();
            ModPartitaController modController = loader.getController();
            loader.setController(partitaController);
            modController.initController(giocatoreCorrente, giocatoriDellaPartita);
            // Ottieni la scena corrente
            Scene currentScene = gioca.getScene();

            // Ottieni lo Stage dalla scena corrente
            Stage currentStage = (Stage) currentScene.getWindow();

            // Imposta la nuova scena sulla finestra di scena corrente
            currentStage.setScene(new Scene(root));
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void initController(AbstractGiocatore giocatoreCorrente) {

        try {
            this.giocatoriDellaPartita.add(this.giocatoreCorrente);
        } catch (Exception e) {
            System.err.println("ERRORE (pre):\t\t " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
