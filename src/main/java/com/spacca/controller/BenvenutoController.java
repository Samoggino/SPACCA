package com.spacca.controller;

//import java.io.File;
import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
import java.util.ArrayList;
import java.util.List;

import com.spacca.App;
//import com.spacca.asset.utente.giocatore.Giocatore;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
//import com.spacca.asset.utente.giocatore.Giocatore;

//import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
//import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BenvenutoController {

    List<AbstractGiocatore> giocatoriDellaPartita = new ArrayList<>(); // Inizializzazione della lista
    AbstractGiocatore giocatoreCorrente;
    PartitaController partitaController;

    @FXML
    private VBox buttonContainer;

    // Lista degli utenti
    private List<String> userList = new ArrayList<>();

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
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/spacca/pages/modpartita.fxml"));

            // Carica la radice della nuova finestra
            Parent root = loader.load();

            // Crea uno stage per la nuova finestra
            Stage newStage = new Stage();

            // Imposta il titolo e la scena per il nuovo stage
            newStage.setTitle("Mod Partita");
            newStage.setScene(new Scene(root));

            // Ottieni il controller dalla finestra caricata
            ModPartitaController modPartitaController = loader.getController();

            // Inizializza il controller se necessario (puoi creare un metodo initController
            // nel tuo ModPartitaController)
            modPartitaController.initController();

            // Mostra il nuovo stage
            newStage.show();

        } catch (IOException e) {
            System.err.println("Errore durante il caricamento di modpartita.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /*
     * private void changeScene(Button sourceButton) throws IOException {
     * // Carica il nuovo layout FXML per il PartitaController
     * FXMLLoader loader = new
     * FXMLLoader(App.class.getResource("/com/spacca/pages/partita.fxml"));
     * Parent root = loader.load();
     * // Logica per inizializzare il controller se necessario
     * PartitaController partitaController = loader.getController();
     * loader.setController(partitaController);
     * partitaController.initController(giocatoreCorrente, giocatoriDellaPartita);
     * 
     * // Ottieni la scena corrente
     * Scene currentScene = sourceButton.getScene();
     * 
     * // Ottieni lo Stage dalla scena corrente
     * Stage currentStage = (Stage) currentScene.getWindow();
     * 
     * // Imposta la nuova scena sulla finestra di scena corrente
     * currentStage.setScene(new Scene(root));
     * currentStage.show();
     * }
     */

    /*
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
     * changeScene(sourceButton);
     * 
     * } catch (IOException e) {
     * System.err.println("ERRORE (partita):\t\t " + e.getMessage());
     * e.printStackTrace();
     * }
     * }
     */

    public void initController(AbstractGiocatore giocatoreCorrente) {

        try {
            this.giocatoreCorrente = giocatoreCorrente;
            /*
             * this.giocatoriDellaPartita.add(this.giocatoreCorrente);
             * // this.giocatoriDellaPartita.add(MARIO);
             * // this.giocatoriDellaPartita.add(LUIGI);
             * // this.giocatoriDellaPartita.add(PEACH);
             * 
             * // this.giocatoreCorrente = YOSHI;
             */
            for (AbstractGiocatore giocatore : giocatoriDellaPartita) {
                userList.add(giocatore.getUsername());
            }

            /*
             * for (String username : userList) {
             * Button button = new Button(username);
             * button.setOnAction(this::handleButtonAction);
             * buttonContainer.getChildren().add(button);
             * }
             */

        } catch (Exception e) {
            System.err.println("ERRORE (pre):\t\t " + e.getMessage());
        }
    }

    public static void changeScene(String fxmlPath, Object controllerData) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Parent root = loader.load();
            // Logica per inizializzare il controller se necessario
            PartitaController partitaController = loader.getController();
            loader.setController(partitaController);

            Stage currentStage = (Stage) App.getScene().getWindow();

            currentStage.setScene(new Scene(root));
            currentStage.show();
        } catch (IOException e) {
            System.err.println("Errore (changeScene login): \n" + e.getMessage());
        }
    }
}
