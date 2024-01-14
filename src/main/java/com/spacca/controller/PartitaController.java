package com.spacca.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.spacca.asset.match.Partita;
import com.spacca.asset.utente.Amministratore;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PartitaController implements Initializable {

    @FXML
    private TextField codeTextField;

    @FXML
    private VBox partitaControllerVBox;

    Partita partita;
    Amministratore amministratore = new Amministratore();
    // AbstractGiocatore YOSHI = new Giocatore("Yoshi", "", "");
    // AbstractGiocatore MARIO = new Giocatore("Mario", "", "");
    List<AbstractGiocatore> giocatoriDellaPartita;
    AbstractGiocatore giocatoreCorrente;

    public PartitaController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void initController(AbstractGiocatore giocatoreCorrente, List<AbstractGiocatore> giocatoriDellaPartita) {
        this.giocatoreCorrente = giocatoreCorrente;
        this.giocatoriDellaPartita = giocatoriDellaPartita;

    }

    public void setVBox(VBox vBox) {
        this.partitaControllerVBox = vBox;
    }

    public void setGiocatoreCorrente(AbstractGiocatore giocatoreCorrente) {
        this.giocatoreCorrente = giocatoreCorrente;
    }

    @FXML
    void nuovaPartita() {
        partita = amministratore.creaPartita(giocatoriDellaPartita); // oppure carica la partita
        partita.setTokenTurno(giocatoreCorrente.getUsername());
        partita.nuovoTurno();
        System.out.println(partita);

    }

    @FXML
    private void inviaCodice() {
        String codice = codeTextField.getText();
        caricaPartita(codice);
    }

    @FXML
    void risultatoPartita() {

        System.out.println(partita.getCarteSulTavolo());
        System.out.println(partita.getRisultato());
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
        try {
            partita.prendiCartaDaTavolo(this.giocatoreCorrente);
        } catch (Exception e) {
            System.err.println("ERRORE (prendiUnaCartaDalTavoloYoshi):\t\t " + e.getMessage());
        }
    }

    @FXML
    void prendiUnaCartaDalTavoloMario() {
        try {
            partita.prendiCartaDaTavolo(this.giocatoreCorrente);
        } catch (Exception e) {
            System.err.println("ERRORE (prendiUnaCartaDalTavoloMario):\t\t " + e.getMessage());
        }
    }

    private void caricaPartita(String codicePartita) {
        partita = amministratore.caricaPartita(codicePartita);
    }

}
