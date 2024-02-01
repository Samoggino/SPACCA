package com.spacca.controller;

import java.util.ArrayList;
import java.util.List;

import com.spacca.App;
import com.spacca.asset.match.Partita;
import com.spacca.asset.utente.Amministratore;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.database.PartitaHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PartitaController {

    @FXML
    private TextField codeTextField;

    @FXML
    private VBox partitaControllerVBox;

    Partita partita;
    Amministratore amministratore = new Amministratore();
    List<AbstractGiocatore> giocatoriDellaPartita;
    AbstractGiocatore giocatoreCorrente;

    public PartitaController() {
    }

    public void initController(AbstractGiocatore giocatoreCorrente, List<AbstractGiocatore> giocatoriDellaPartita) {
        this.giocatoreCorrente = giocatoreCorrente;
        this.giocatoriDellaPartita = giocatoriDellaPartita;
    }

    public void setGiocatoreCorrente(AbstractGiocatore giocatoreCorrente) {
        this.giocatoreCorrente = giocatoreCorrente;
    }

    @FXML
    void nuovaPartita() {
        Amministratore amministratore = new Amministratore();

        List<String> usernameLsit = new ArrayList<>();
        for (AbstractGiocatore utente : giocatoriDellaPartita) {
            usernameLsit.add(utente.getUsername());
        }

        partita = amministratore.creaPartita(usernameLsit); // crea e salva la partita con un codice casuale
        partita.setGiocatoreCorrente(giocatoreCorrente.getUsername());
        partita.nuovoTurno();
        System.out.println(partita);
        changeScene();
    }

    @FXML
    void inviaCodice() {
        String codice = codeTextField.getText();
        caricaPartita(codice);
    }

    @FXML
    void risultatoPartita() {
        System.out.println(partita.getCarteSulTavolo());
        System.out.println(partita.getClassifica());
    }

    @FXML
    void rubaUnMazzo() {
        // partita.rubaUnMazzo(this.giocatoreCorrente, YOSHI);
    }

    @FXML
    void rubaMezzoMazzo() {
        // partita.rubaMezzoMazzo(this.giocatoreCorrente, YOSHI);
    }

    @FXML
    void prendiUnaCartaDalTavoloYoshi() {
        // try {
        // partita.prendiCartaDaTavolo(this.giocatoreCorrente.getUsername());
        // } catch (Exception e) {
        // System.err.println("ERRORE (prendiUnaCartaDalTavoloYoshi):\t\t " +
        // e.getMessage());
        // }
    }

    @FXML
    void prendiUnaCartaDalTavoloMario() {
        // try {
        // partita.prendiCartaDaTavolo(this.giocatoreCorrente.getUsername());
        // } catch (Exception e) {
        // System.err.println("ERRORE (prendiUnaCartaDalTavoloMario):\t\t " +
        // e.getMessage());
        // }
    }

    private void caricaPartita(String codicePartita) {
        PartitaHandler partitaHandler = new PartitaHandler();
        System.out.println("SIAMO DENTRO CARICA PARTITA IN PARTICA CONTROOLER" + codicePartita);

        this.partita = partitaHandler.carica(codicePartita);
        this.partita.setGiocatoreCorrente(giocatoreCorrente.getUsername());
        System.out.println(partita);
        changeScene();
    }

    private void changeScene() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/spacca/pages/tavolo.fxml"));
            Parent root = loader.load();
            // Logica per inizializzare il controller se necessario
            TavoloController tavolo = loader.getController();
            loader.setController(tavolo);

            tavolo.initController(this.partita);

            Stage currentStage = (Stage) partitaControllerVBox.getScene().getWindow();

            currentStage.setScene(new Scene(root));
            currentStage.show();

        } catch (Exception e) {
            System.err.println("ERRORE (lanciaPartita):\t\t " + e.getMessage());
            e.printStackTrace();
        }
    }

}