package com.spacca.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.spacca.App;
import com.spacca.database.GiocatoreHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CreazionePartitaController implements Initializable {
    @FXML
    private Spinner numeroGiocatori;
    @FXML
    private ChoiceBox<String> sceltaGiocatore1;
    @FXML
    private ChoiceBox<String> sceltaGiocatore2;
    @FXML
    private ChoiceBox<String> sceltaGiocatore3;
    @FXML
    private ChoiceBox<String> sceltaGiocatore4;
    @FXML
    private Button indietroButton;
    @FXML
    private Button creaButton;
    @FXML
    private Button procButton;
    @FXML
    private TextField codicePartita;
    @FXML
    private Pane rootPane;

    private List<String> listaGiocatori = new ArrayList<>();

    GiocatoreHandler handler = new GiocatoreHandler();

    @FXML
    public void initController() {
    }

    private List<String> giocatoriSelezionati = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inizializza le liste delle ChoiceBox
        List<ChoiceBox<String>> choiceBoxes = Arrays.asList(sceltaGiocatore1, sceltaGiocatore2, sceltaGiocatore3,
                sceltaGiocatore4);

        // Inizializza tutte le ChoiceBox con l'elenco completo dei giocatori
        for (ChoiceBox<String> choiceBox : choiceBoxes) {
            choiceBox.getItems().addAll(handler.getAllGiocatoriWithRobot());

            // Aggiungi un listener per la selezione di ogni ChoiceBox
            choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    // Rimuovi l'utente selezionato dalle altre ChoiceBox
                    for (ChoiceBox<String> otherChoiceBox : choiceBoxes) {
                        if (otherChoiceBox != choiceBox) {
                            otherChoiceBox.getItems().remove(newValue);
                        }
                    }
                    // Aggiungi l'utente selezionato alla lista degli utenti selezionati
                    giocatoriSelezionati.add(newValue);
                }
                if (oldValue != null) {
                    // Rimuovi l'utente deselezionato dalla lista degli utenti selezionati
                    giocatoriSelezionati.remove(oldValue);
                    // Aggiungi l'utente deselezionato alle altre ChoiceBox
                    for (ChoiceBox<String> otherChoiceBox : choiceBoxes) {
                        if (otherChoiceBox != choiceBox && !otherChoiceBox.getItems().contains(oldValue)) {
                            otherChoiceBox.getItems().add(oldValue);
                        }
                    }
                }
            });
        }
    }

    @FXML
    public void handleIndietro() {
        changeScene("/com/spacca/pages/benvenutoAdmin.fxml");
    }

    @FXML
    public void handleCrea() {
        System.out.println("GIOCATORI SCELTI  : " + listaGiocatori);
    }

    @FXML
    private void handleMostra() {
        System.out.println("SIAMO IN MOSTRA PARTITA ");
        // salvo il numero inserito e mostro il numero di giocatori
        // System.out.println("L'amministratore ha selezionato " + "giocatori");
        /*
         * Integer num = (Integer) numeroGiocatori.getValue();
         * System.out.println("L'amministratore ha selezionato " + num + "giocatori");
         * sceltaGiocatore1.setVisible(num >= 1);
         * sceltaGiocatore2.setVisible(num >= 2);
         * sceltaGiocatore3.setVisible(num >= 3);
         * sceltaGiocatore4.setVisible(num >= 4);
         */

        // Aggiorna l'interfaccia utente per rendere effettive le modifiche
        rootPane.requestLayout();
    }

    public void changeScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Parent root = loader.load();

            Scene currentScene = procButton.getScene();

            // Ottieni lo Stage dalla scena corrente
            Stage currentStage = (Stage) currentScene.getWindow();

            currentStage.setTitle("Benvenuto Admin ! ");
            currentStage.setScene(new Scene(root));
            currentStage.show();

        } catch (NullPointerException e) {
            System.out.println("Login avvenuto con successo!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IO Errore (Benvenuto Admin controller): \n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore (Benvenuto Admin controller): \n" + e.getMessage());
        }
    }

}
