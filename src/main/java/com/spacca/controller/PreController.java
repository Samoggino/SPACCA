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

public class PreController implements Initializable {

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

    // Lista degli utenti
    private List<String> userList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.giocatoriDellaPartita.add(YOSHI);
            this.giocatoriDellaPartita.add(MARIO);

            // this.giocatoreCorrente = YOSHI;

            for (AbstractGiocatore giocatore : giocatoriDellaPartita) {
                userList.add(giocatore.getUsername());
            }

            for (String username : userList) {
                Button button = new Button(username);
                button.setOnAction(this::handleButtonAction);
                buttonContainer.getChildren().add(button);
            }
        } catch (Exception e) {
            System.err.println("ERRORE (pre):\t\t " + e.getMessage());
        }
    }

    /**
     * private void handleButtonAction(ActionEvent event) {
     * try {
     * Button sourceButton = (Button) event.getSource();
     * String text = sourceButton.getText();
     * System.out.println("Hai premuto il bottone con l'username: " + text);
     * 
     * for (AbstractGiocatore abstractGiocatore : giocatoriDellaPartita) {
     * if (abstractGiocatore.getUsername().equals(text)) {
     * this.giocatoreCorrente = abstractGiocatore;
     * }
     * }
     * 
     * // Carica il nuovo layout FXML per il PartitaController
     * FXMLLoader loader = new
     * FXMLLoader(App.class.getResource("/com/spacca/pages/partita.fxml"));
     * 
     * // Ottieni il controller dalla nuova finestra di scena
     * partitaController = new PartitaController();
     * loader.setController(partitaController);
     * Parent root = loader.load();
     * 
     * // Crea una nuova finestra di scena
     * Stage stage = new Stage();
     * Scene scene = new Scene(root);
     * 
     * partitaController.initController(this.giocatoreCorrente,
     * this.giocatoriDellaPartita);
     * 
     * // Imposta la nuova scena sulla finestra di scena
     * stage.setScene(scene);
     * stage.show();
     * } catch (IOException e) {
     * System.err.println("ERRORE (partita):\t\t " + e.getMessage());
     * e.printStackTrace();
     * }
     * }
     */
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

            // Carica il nuovo layout FXML per il PartitaController
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/spacca/pages/partita.fxml"));

            // Ottieni il controller dalla nuova finestra di scena
            partitaController = new PartitaController();
            loader.setController(partitaController);
            Parent root = loader.load();

            partitaController.initController(this.giocatoreCorrente, this.giocatoriDellaPartita);

            // Ottieni la scena corrente
            Scene currentScene = sourceButton.getScene();

            // Ottieni lo Stage dalla scena corrente
            Stage currentStage = (Stage) currentScene.getWindow();

            // Imposta la nuova scena sulla finestra di scena corrente
            currentStage.setScene(new Scene(root));
            currentStage.show();
        } catch (IOException e) {
            System.err.println("ERRORE (partita):\t\t " + e.getMessage());
            e.printStackTrace();
        }
    }

}
