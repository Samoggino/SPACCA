package com.spacca.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.spacca.App;
import com.spacca.asset.match.Torneo;
import com.spacca.database.GiocatoreHandler;
import com.spacca.database.TorneoHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CreazioneTorneoController implements Initializable {
    @FXML
    private Spinner<Integer> numeroGiocatoriSpinner;
    @FXML
    private ComboBox<String> sceltaGiocatore1, sceltaGiocatore2, sceltaGiocatore3, sceltaGiocatore4;
    @FXML
    private Label label1, label2, label3, label4, labelCodice;
    @FXML
    private Button indietroButton;
    @FXML
    private Button creaButton;
    @FXML
    private Button procButton;
    @FXML
    private TextField codiceField;

    private String codiceTorneo;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        sceltaGiocatore1.setVisible(false);
        sceltaGiocatore2.setVisible(false);
        sceltaGiocatore3.setVisible(false);
        sceltaGiocatore4.setVisible(false);
        label1.setVisible(false);
        label2.setVisible(false);
        label3.setVisible(false);
        label4.setVisible(false);

        // per far inserire solo numeri interi nel codice della partita
        TextFormatter<Integer> textFormatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        });
        codiceField.setTextFormatter(textFormatter); // Assicurati di usare lo stesso identificatore
    }

    @FXML
    private void handleMostra() {
        System.out.println("SIAMO IN MOSTRA TORNEO ");

        GiocatoreHandler handler = new GiocatoreHandler();

        List<String> giocatoriSelezionati = new ArrayList<>();

        Boolean controlloCodice = controllaCodcie(codiceField.getText().trim());

        if (controlloCodice) {
            // Inizializza le liste delle ComboBox
            List<ComboBox<String>> comboBoxes = inizializeComboBoxandLayout();
            // popolo i combo box
            System.out.println("LISTA COMBO BOX " + comboBoxes.size());

            // Inizializza tutte le ComboBox con l'elenco completo dei giocatori
            for (ComboBox<String> comboBox : comboBoxes) {
                comboBox.getItems().addAll(handler.getAllGiocatoriWithRobot());
                comboBox.setVisibleRowCount(3);

                // Aggiungi un listener per la selezione di ogni ComboBox
                comboBox.getSelectionModel().selectedItemProperty().addListener((observable,
                        oldValue, newValue) -> {
                    if (newValue != null) {
                        // Rimuovi l'utente selezionato dalle altre ComboBox
                        for (ComboBox<String> otherComboBox : comboBoxes) {
                            if (otherComboBox != comboBox) {
                                otherComboBox.getItems().remove(newValue);
                            }
                        }
                        // Aggiungi l'utente selezionato alla lista degli utenti selezionati
                        giocatoriSelezionati.add(newValue);
                    }
                    if (oldValue != null) {
                        // Rimuovi l'utente deselezionato dalla lista degli utenti selezionati
                        giocatoriSelezionati.remove(oldValue);
                        // Aggiungi l'utente deselezionato alle altre ComboBox
                        for (ComboBox<String> otherComboBox : comboBoxes) {
                            if (otherComboBox != comboBox &&
                                    !otherComboBox.getItems().contains(oldValue)) {
                                otherComboBox.getItems().add(oldValue);
                            }
                        }
                    }
                });
            }
            this.codiceTorneo = "T" + codiceField.getText().trim();
        }
    }

    private List<ComboBox<String>> inizializeComboBoxandLayout() throws NullPointerException {

        int num = numeroGiocatoriSpinner.getValue();
        System.out.println("NUM = " + num);

        List<ComboBox<String>> comboBoxes = new ArrayList<>();

        // imposto i valori di default ovvero spinner = 2

        label1.setVisible(true);
        label2.setVisible(true);
        label3.setVisible(false);
        label4.setVisible(false);

        sceltaGiocatore1.setVisible(true);
        sceltaGiocatore2.setVisible(true);
        sceltaGiocatore3.setVisible(false);
        sceltaGiocatore4.setVisible(false);

        comboBoxes.add(sceltaGiocatore1);
        comboBoxes.add(sceltaGiocatore2);

        if (num == 3) {
            sceltaGiocatore3.setVisible(true);
            sceltaGiocatore4.setVisible(false);

            label3.setVisible(true);
            label4.setVisible(false);

            comboBoxes.add(sceltaGiocatore3);
        } else if (num == 4) {
            sceltaGiocatore3.setVisible(true);
            sceltaGiocatore4.setVisible(true);
            label3.setVisible(true);
            label4.setVisible(true);

            comboBoxes.add(sceltaGiocatore3);
            comboBoxes.add(sceltaGiocatore4);
        }

        return comboBoxes;
    }

    public boolean controllaCodcie(String codice) {
        Boolean controllo = true;
        System.out.println("\n \n CONTROLLO INSERIMENTO CODICE P + " + codice + "\n \n");

        labelCodice.setText("");
        labelCodice.setTextFill(Color.DARKORANGE);
        labelCodice.setStyle("-fx-border-color: lightgrey");

        if (codice.trim().isEmpty()) {
            labelCodice.setText("Non ha inserito il codice!");
            codiceField.setStyle("-fx-border-color:darkorange");

            showAlert("Per procedere devi compilare correttamente tutti i campi", "Mancato inserimento del codice",
                    AlertType.ERROR);

            controllo = false;
        } else {
            try {

                TorneoHandler torneoFile = new TorneoHandler();
                codice = "T" + codice;
                // Verifica se il file esiste
                if (torneoFile.VerificaEsistenzaFile(codice)) {

                    Torneo torneo = (Torneo) torneoFile.carica(codice);
                    System.out.println("TORNEO" + codice);

                    if (torneo.getCodice().equals(codice)) {
                        labelCodice.setText("Codice del torneo già in utilizzo! ");
                        codiceField.setStyle("-fx-border-color:darkorange");
                        controllo = false;
                    }
                } else {
                    labelCodice.setText("Codice corretto! ");
                    labelCodice.setTextFill(Color.BLACK);
                    controllo = true;
                }

            } catch (Exception e) {
                System.err.println("ERRORE: Errore generico in\n" + e.getMessage());
            }
        }
        return controllo;
    }

    @FXML
    public void handleCrea() {
        System.out.println("GIOCATORI SCELTI  : ");

        List<ComboBox<String>> comboBoxes = inizializeComboBoxandLayout();

        List<String> giocatoriScelti = new ArrayList<>();

        // trovo la lista dei giocatori selezionati
        // Itera tutte le ComboBox per aggiungere i giocatori selezionati alla lista
        for (ComboBox<String> comboBox : comboBoxes) {
            String giocatoreSelezionato = comboBox.getValue();
            if (giocatoreSelezionato != null) {
                giocatoriScelti.add(giocatoreSelezionato);
            }
        }

        if (giocatoriScelti.size() == comboBoxes.size()) {

            System.out.println("GIOCATORI SCELTI : " + giocatoriScelti);
            System.out.println("CODICE : " + codiceTorneo);

            // Torneo torneo = new Torneo();
            // partitaHandler.salva(partita, codicePartita);
            showAlert("Creazione del torneo andato a buon fine", "Torneo " + codiceTorneo + " creato",
                    AlertType.INFORMATION);

            changeScene("/com/spacca/pages/benvenutoAdmin.fxml");

        } else {
            showAlert("Non hai scelto tutti i giocatori", "Mancata scelta di un giocatore", AlertType.ERROR);
        }
    }

    private void showAlert(String content, String titolo, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setContentText(titolo);
        alert.setHeaderText(content);
        alert.showAndWait();
    }

    @FXML
    public void handleIndietro() {
        changeScene("/com/spacca/pages/benvenutoAdmin.fxml");
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