package com.spacca.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.spacca.asset.match.Partita;
import com.spacca.asset.match.Torneo;
import com.spacca.asset.utente.Amministratore;
import com.spacca.database.GiocatoreHandler;
import com.spacca.database.TorneoHandler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

public class CreazioneTorneoController implements Initializable {
    @FXML
    private ComboBox<String> sceltaGiocatore1, sceltaGiocatore2, sceltaGiocatore3, sceltaGiocatore4;
    @FXML
    private Label label1, label2, label3, label4, labelCodice;
    @FXML
    private Button indietroButton, creaButton, procButton, simulaButton;
    @FXML
    private TextField codiceField;
    @FXML
    private RadioButton generaCodiceRadioButton;
    @FXML
    private RadioButton radioButton2, radioButton4, radioButton8, radioButton16, radioButton32;

    private String codiceTorneo;
    private boolean simula = false;

    private ToggleGroup toggleGroup = new ToggleGroup();

    private transient Amministratore admin = new Amministratore();
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

        radioButton2.setToggleGroup(toggleGroup);
        radioButton4.setToggleGroup(toggleGroup);
        radioButton16.setToggleGroup(toggleGroup);
        radioButton32.setToggleGroup(toggleGroup);
        radioButton8.setToggleGroup(toggleGroup);

        // per far inserire solo numeri interi nel codice della partita
        TextFormatter<Integer> textFormatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        });
        codiceField.setTextFormatter(textFormatter); // Assicurati di usare lo stesso identificatore
        creaButton.setDisable(true); // Disabilita il bottone
        simulaButton.setDisable(true);
    }

    @FXML
    public void handleGenera() {
        if (generaCodiceRadioButton.isSelected()) {
            codiceField.setText("");
            this.codiceTorneo = admin.generaNumeroCasualeTorneo();
            codiceField.setPromptText(codiceTorneo);
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
        System.out.println("SIAMO IN MOSTRA TORNEO ");

        String codice = codiceField.getText().trim();

        // se il codice non è stato inserito correttamente e/o il radio botton
        // selezionato
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();

        if (generaCodiceRadioButton.isSelected() && selectedRadioButton != null) {
            comboBoxes = inizializeComboBoxandLayout();
            popola();
            creaButton.setDisable(false);
            simulaButton.setDisable(false);

        } else if (!generaCodiceRadioButton.isSelected()) {
            if (controllaCodice(codice) && selectedRadioButton != null) {
                this.codiceTorneo = codice;
                comboBoxes = inizializeComboBoxandLayout();
                popola();
                creaButton.setDisable(false); // Abilita il bottone
            } else {
                creaButton.setDisable(true);
            }
        } else {
            showAlert("Per procedere devi compilare correttamente tutti i campi", "Mancato inserimento di un campo",
                    AlertType.ERROR);
        }

    }

    private void popola() {
        GiocatoreHandler handler = new GiocatoreHandler();
        List<String> giocatoriSelezionati = new ArrayList<>();

        // Inizializza tutte le ComboBox con l'elenco completo dei giocatori
        for (ComboBox<String> comboBox : comboBoxes) {
            comboBox.getItems().addAll(handler.mostraTutteGliUtenti());
            comboBox.setVisibleRowCount(5);

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
        // imposto i valori di default ovvero spinner = 2

        if (radioButton2.isSelected()) {
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
        } else if (radioButton4.isSelected() ||
                radioButton8.isSelected() ||
                radioButton16.isSelected() ||
                radioButton32.isSelected()) {

            label1.setVisible(true);
            label2.setVisible(true);
            label3.setVisible(true);
            label4.setVisible(true);

            sceltaGiocatore1.setVisible(true);
            sceltaGiocatore2.setVisible(true);
            sceltaGiocatore3.setVisible(true);
            sceltaGiocatore4.setVisible(true);

            comboBoxes.add(sceltaGiocatore1);
            comboBoxes.add(sceltaGiocatore2);
            comboBoxes.add(sceltaGiocatore3);
            comboBoxes.add(sceltaGiocatore4);
        }

        return comboBoxes;
    }

    public boolean controllaCodice(String codice) {
        Boolean controllo = true;
        System.out.println("\n \n CONTROLLO INSERIMENTO CODICE T + " + codice + "\n \n");

        labelCodice.setText("");
        labelCodice.setTextFill(Color.DARKORANGE);
        labelCodice.setStyle("-fx-border-color: lightgrey");

        if (codice.trim().isEmpty()) {
            labelCodice.setText("Non ha inserito il codice!");
            codiceField.setStyle("-fx-border-color:darkorange");
            controllo = false;
        } else {
            try {

                TorneoHandler torneoFile = new TorneoHandler();
                codice = "T" + codice;
                // Verifica se il file esiste
                if (torneoFile.verificaEsistenzaFile(codice)) {

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
        System.out.println("CREA TORNEO CONTROLLER  : ");

        List<String> giocatoriScelti = new ArrayList<>();

        // trovo la lista dei giocatori selezionati
        // Itera tutte le ComboBox per aggiungere i giocatori selezionati alla lista
        for (ComboBox<String> comboBox : comboBoxes) {
            String giocatoreSelezionato = comboBox.getValue();
            if (giocatoreSelezionato != null) {
                giocatoriScelti.add(giocatoreSelezionato);
            }
        }

        int value = 0;
        if (radioButton2.isSelected()) {
            value = 2;
        } else if (radioButton4.isSelected()) {
            value = 4;
        } else if (radioButton8.isSelected()) {
            value = 8;
        } else if (radioButton16.isSelected()) {
            value = 16;
        } else if (radioButton32.isSelected()) {
            value = 32;
        }
        if (simula) {
            List<String> listaGiocatori = new ArrayList<>();
            Torneo torneoSimulato = admin.creaTorneo(codiceTorneo, listaGiocatori, value);

            showAlert("Simulazione del torneo andata a buon fine", "Torneo " + codiceTorneo + " creato",
                    AlertType.INFORMATION);

            simulaTorneoHandler(admin, torneoSimulato);

        } else {
            if (giocatoriScelti.size() >= 2) {

                admin.creaTorneo(codiceTorneo, giocatoriScelti, value);

                showAlert("Creazione del torneo andato a buon fine", "Torneo " + codiceTorneo + " creato",
                        AlertType.INFORMATION);
                handleIndietro();
            } else {
                showAlert("Mancato scelta di almeno due giocatori", "Per procedere seleziona almeno due giocatori",
                        AlertType.INFORMATION);
            }
        }
    }

    /**
     * itero ricorsivamente per tutti i turni del torneo, fino a quando non ho un
     * vincitore
     */
    private void simulaTorneoHandler(Amministratore amministratore, Torneo torneo) {
        try {
            if (torneo.hasWinner()) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setContentText("Il vincitore del torneo è: " + torneo.getVincitore());
                alert.setHeaderText("Vincitore del torneo");
                alert.showAndWait();
                System.out.println("Il vincitore del torneo è: " + torneo.getVincitore());
                return;
            }
            for (String codicePartita : torneo.getCodiciPartite()) {
                Partita partita = admin.caricaPartita(codicePartita);
                TavoloController tavolo = new TavoloController();
                tavolo.initController(partita, false, amministratore);

            }
            if (torneo.getGiocatoriRimasti().size() > 1) {
                simulaTorneoHandler(admin, admin.caricaTorneo(torneo.getCodice()).nuovoTurnoDelTorneo());
            }
        } catch (Exception e) {
            System.err.println("Errore (simulaTorneoHandler): \n" + e.getMessage());
            e.printStackTrace();
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

    @FXML
    public void simulaTorneo() {
        simula = true;
    }
}