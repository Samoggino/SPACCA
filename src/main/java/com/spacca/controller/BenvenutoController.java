package com.spacca.controller;

import java.io.IOException;

import com.spacca.App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class BenvenutoController {
    @FXML
    private CheckBox singolaScelta;

    @FXML
    private CheckBox torneoScelta;

    @FXML
    private Button avvioButton;

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
            popupStage.show();
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
