package com.spacca.controller;

import java.io.IOException;

import com.spacca.App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
//import javafx.scene.control.ChoiceBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BenvenutoController {
    @FXML
    private CheckBox singolaScelta;

    @FXML
    private CheckBox torneoScelta;

    // @FXML
    // private ChoiceBox listaCodici;

    @FXML
    private Button avvioButton;

    @FXML
    private Button indietro;

    @FXML
    private void initialize() {

    }

    @FXML
    private void handleGiocaButton() {
        // Ottenere il tipo di partita selezionato
        // String tipoPartita = singolaScelta.isSelected() ? "Singola" : "Torneo";

        // Ottenere il codice selezionato
        // String codiceSelezione = (String) listaCodici.getValue();

        // Creare un nuovo Stage per il popup
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Modalità di gioco");

        // Caricare il contenuto del modulo partita nel popup
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("modpartita.fxml"));
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Impostare la scena e mostrare il popup
        Scene scene = new Scene(root, 400, 400);
        Image icon = new Image(getClass().getResourceAsStream("/com/spacca/images/icone/logo.jpg"));
        popupStage.getIcons().add(icon);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    @FXML
    private void handleIndietroButton() {
        try {
            /*
             * Stage stage = (Stage) indietro.getScene().getWindow();
             * stage.close();
             */
            App.setRoot("benvenutoUtente");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void handleAvviaButton() {
        try {
            App.setRoot("login");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
     * @FXML
     * private void handleAvviaButton() {
     * try {
     * App.setRoot("partita");
     * } catch (IOException e) {
     * e.printStackTrace();
     * }
     * }
     */
}