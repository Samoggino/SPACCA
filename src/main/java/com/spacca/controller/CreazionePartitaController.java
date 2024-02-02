package com.spacca.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.spacca.asset.match.Partita;
import com.spacca.asset.utente.Amministratore;
import com.spacca.database.GiocatoreHandler;
import com.spacca.database.PartitaHandler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class CreazionePartitaController implements Initializable {
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
    @FXML
    private RadioButton generaCodiceRadioButton;

    private String codicePartita;

    transient Amministratore admin = new Amministratore();

    transient GiocatoreHandler handler = new GiocatoreHandler();

    transient PartitaHandler partitafile = new PartitaHandler();

    private List<ComboBox<String>> comboBoxes = new ArrayList<>();

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
        codiceField.setTextFormatter(textFormatter); // Assicurati di usare lo stesso identificatori
        creaButton.setDisable(true); // Disabilita il bottone
    }

    @FXML
    public void handleGenera() {
        if (generaCodiceRadioButton.isSelected()) {
            codiceField.setText("");
            this.codicePartita = admin.generaNumeroCasualePartita();
            codiceField.setPromptText(codicePartita);
            labelCodice.setText("");
            codiceField.setDisable(true);
        } else if (!generaCodiceRadioButton.isSelected()) {
            codiceField.setText("");
            codiceField.setPromptText("");
            codiceField.setDisable(false);
        }
    }

    @FXML
    private void handleMostra() {
        System.out.println("SIAMO IN MOSTRA PARTITA ");

        if (generaCodiceRadioButton.isSelected()) {
            comboBoxes = inizializeComboBoxandLayout();
            popola();
            creaButton.setDisable(false);
        } else if (!generaCodiceRadioButton.isSelected()) {
            if (controllaCodice(codiceField.getText().trim())) {
                // Inizializza le liste delle ComboBox
                this.codicePartita = "P" + codiceField.getText().trim();
                comboBoxes = inizializeComboBoxandLayout();
                popola();
                creaButton.setDisable(false); // Abilita il bottone
            }
        } else {
            showAlert("Per procedere devi compilare correttamente tutti i campi", "Mancato inserimento",
                    AlertType.ERROR);
        }
    }

    private void popola() {
        GiocatoreHandler handler = new GiocatoreHandler();
        List<String> giocatoriSelezionati = new ArrayList<>();

        // Inizializza tutte le ComboBox con l'elenco completo dei giocatori
        for (ComboBox<String> comboBox : comboBoxes) {
            comboBox.getItems().addAll(handler.getAllGiocatori());
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
    }

    private List<ComboBox<String>> inizializeComboBoxandLayout() throws NullPointerException {

        comboBoxes.clear();

        int num = numeroGiocatoriSpinner.getValue();
        System.out.println("NUM = " + num);

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

    public boolean controllaCodice(String codice) {
        Boolean controllo = true;
        System.out.println("\n \n CONTROLLO INSERIMENTO CODICE P + " + codice + "\n \n");

        labelCodice.setText("");
        labelCodice.setTextFill(Color.DARKORANGE);
        labelCodice.setStyle("-fx-border-color: lightgrey");

        if (codice.trim().isEmpty()) {
            labelCodice.setText("Non ha inserito il codice!");
            codiceField.setStyle("-fx-border-color:darkorange");

            controllo = false;
        } else {
            try {

                // codice = "P" + codice;
                // Verifica se il file esiste
                if (partitafile.VerificaEsistenzaFile(codice)) {

                    Partita partita = partitafile.carica(codice);
                    System.out.println("PARTITA" + codice);

                    if (partita.getCodice().equals(codice)) {
                        labelCodice.setText("Codice della partita già in utilizzo! ");
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
            System.out.println("CODICE : " + codicePartita);

            admin.creaPartita(codicePartita, giocatoriScelti);

            showAlert("Creazione della partita andata a buon fine", "Partita " + codicePartita + " creata",
                    AlertType.INFORMATION);

            admin.ritornaBenvenutoAdmin(creaButton.getScene());

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
        admin.ritornaBenvenutoAdmin(procButton.getScene());
    }

}
