package com.spacca.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.spacca.App;
import com.spacca.asset.utente.giocatore.Giocatore;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class BenvenutoController {
    @FXML
    private CheckBox singolaScelta;

    @FXML
    private CheckBox torneoScelta;

    @FXML
    private Button avvioButton;

    @FXML
    private ChoiceBox menuTendina;

    @FXML
    private Button indietro;

    private Stage popupStage; // Riferimento allo Stage del popup

    @FXML
    private void initialize() throws IOException {

        // Imposta il titolo della finestra principale
        App.getStage().setTitle("Benvenuto");

        popupStage = new Stage();
        popupStage.setTitle("Modalità partita");

        Image icon = new Image(getClass().getResourceAsStream("/com/spacca/images/icone/logo.jpg"));
        popupStage.getIcons().add(icon);

        popolaChoiceBoxConUtenti();
    }

    private void popolaChoiceBoxConUtenti() {
        /*
         * // Imposta il percorso della cartella degli utenti
         * String folderPath = "src/main/resources/com/spacca/database/giocatori/";
         * 
         * // Ottieni la lista di file nella cartella
         * File folder = new File(folderPath);
         * File[] files = folder.listFiles();
         * 
         * if (files != null) {
         * List<String> nomiUtenti = new ArrayList<>();
         * 
         * // Filtra e leggi i file JSON
         * for (File file : files) {
         * if (file.isFile() && file.getName().endsWith(".json")) {
         * try {
         * // Leggi il giocatore dal file
         * FileHandler fileHandler = new FileHandler();
         * Giocatore giocatore = fileHandler.leggiUtente(file);
         * 
         * // Aggiungi il nome utente alla lista
         * nomiUtenti.add(giocatore.getUsername());
         * } catch (Exception e) {
         * e.printStackTrace();
         * // Gestisci eventuali errori di lettura
         * }
         * }
         * }
         * 
         * // Popola il ChoiceBox con la lista di nomi utenti
         * listaCodici.getItems().addAll(nomiUtenti);
         * }
         */
    }

    /**
     * 
     */
    @FXML
    private void handleIndietroButton() {
        indietro.getScene().getWindow().getScene().getWindow().hide();
    }

    @FXML
    private void handleAvviaButton() {

        // Chiudi lo stage principale
        indietro.getScene().getWindow().getScene().getWindow().hide();
        try {
            // Imposta il titolo della finestra principale
            App.getStage().setTitle("Partita");

            // Carica la nuova schermata
            App.setRoot("partita");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void handleGiocaButton() {
        // Mostra lo stage del popup
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("modpartita" + ".fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 400);
            popupStage.setScene(scene);
            popupStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCheckBoxAction(MouseEvent event) {
        if (event.getSource() instanceof CheckBox) {
            CheckBox clickedCheckBox = (CheckBox) event.getSource();
            if (clickedCheckBox.isSelected()) {
                if (clickedCheckBox == singolaScelta) {
                    torneoScelta.setSelected(false);
                } else if (clickedCheckBox == torneoScelta) {
                    singolaScelta.setSelected(false);
                }
            }
        }
    }
}
