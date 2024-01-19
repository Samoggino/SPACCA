package com.spacca.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.spacca.App;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.asset.utente.giocatore.Giocatore;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PrePartitaController implements Initializable {

    AbstractGiocatore YOSHI = new Giocatore("Yoshi", "", "");
    AbstractGiocatore MARIO = new Giocatore("Mario", "", "");
    AbstractGiocatore LUIGI = new Giocatore("Luigi", "", "");
    AbstractGiocatore PEACH = new Giocatore("Peach", "", "");
    AbstractGiocatore TOAD = new Giocatore("Toad", "", "");
    AbstractGiocatore BOWSER = new Giocatore("Bowser", "", "");

    List<AbstractGiocatore> giocatoriDellaPartita = new ArrayList<>(); // Inizializzazione della lista
    AbstractGiocatore giocatoreCorrente;
    PartitaController partitaController;

    @FXML
    private VBox buttonContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void handleButtonAction(ActionEvent event) {
        try {
            Button sourceButton = (Button) event.getSource();
            String text = sourceButton.getText();
            System.out.println("Hai premuto il bottone con l'username: " + text);

            for (AbstractGiocatore abstractGiocatore : giocatoriDellaPartita) {
                if (abstractGiocatore.getUsername().equals(text)) {
                    this.giocatoreCorrente = abstractGiocatore;
                }
            }

            changeScene(sourceButton);

        } catch (IOException e) {
            System.err.println("ERRORE (partita):\t\t " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void changeScene(Button sourceButton) throws IOException {
        // Carica il nuovo layout FXML per il PartitaController
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/spacca/pages/partita.fxml"));
        Parent root = loader.load();
        // Logica per inizializzare il controller se necessario
        PartitaController partitaController = loader.getController();
        loader.setController(partitaController);
        partitaController.initController(giocatoreCorrente, giocatoriDellaPartita);

        // Ottieni la scena corrente
        Scene currentScene = sourceButton.getScene();

        // Ottieni lo Stage dalla scena corrente
        Stage currentStage = (Stage) currentScene.getWindow();

        // Imposta la nuova scena sulla finestra di scena corrente
        currentStage.setScene(new Scene(root));
        currentStage.show();
    }

    public void initController(AbstractGiocatore giocatoreCorrente) {
        try {
            this.giocatoreCorrente = giocatoreCorrente;
            this.giocatoriDellaPartita.add(this.giocatoreCorrente);
            this.giocatoriDellaPartita.add(MARIO);
            this.giocatoriDellaPartita.add(LUIGI);
            // this.giocatoriDellaPartita.add(PEACH);

            for (AbstractGiocatore giocatore : giocatoriDellaPartita) {
                Button button = new Button(giocatore.getUsername());
                button.setOnAction(this::handleButtonAction);
                buttonContainer.getChildren().add(button);
            }
        } catch (Exception e) {
            System.err.println("ERRORE (pre):\t\t " + e.getMessage());
        }
    }

}