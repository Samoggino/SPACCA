package com.spacca.controller;

import java.util.ArrayList;
import java.util.List;

import com.spacca.asset.match.Partita;
import com.spacca.asset.utente.Amministratore;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.asset.utente.giocatore.Giocatore;
import com.spacca.database.FileHandler;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PartitaController {
    FileHandler fileHandler = new FileHandler();
    Amministratore amministratore = new Amministratore();
    Partita partita;
    AbstractGiocatore giocatore1 = new Giocatore("Yoshi", "");
    AbstractGiocatore giocatore2 = new Giocatore("Mario", "");
    List<AbstractGiocatore> giocatoriDellaPartita = new ArrayList<>(); // Inizializzazione della lista

    @FXML
    private TextField codeTextField;

    @FXML
    void nuovaPartita() {

        giocatoriDellaPartita.add(giocatore1);
        giocatoriDellaPartita.add(giocatore2);

        partita = amministratore.creaPartita(giocatoriDellaPartita);

        // System.out.println("Mazzo di gioco prima di giocare" +
        // partita.getMazzoDiGioco());

        partita.nuovoTurno(1);

        // System.out.println(partita.getMazzoDiGioco());
        // System.out.println(partita.stampaManoDeiGiocatori());
        System.out.println("Codice:\t" + partita.getCodice());

    }

    @FXML
    private void inviaCodice() {
        String codice = codeTextField.getText();
        // Esegui la tua funzione passando il codice come parametro
        caricaPartita(codice);
    }

    @FXML
    void risultatoPartita() {
        // partita.gioca(giocatore1, 2);
        System.out.println("Carte di " + giocatore1.getUsername() + "\n" + partita.getManoDellUtente(giocatore1.getUsername()));
        System.out.println("Carte di " + giocatore2.getUsername() + "\n" + partita.getManoDellUtente(giocatore2.getUsername()));
        System.out.println(partita.getCarteSulTavolo());
        System.out.println(partita.getRisultato());
    }

    @FXML
    void rubaUnMazzo() {
        partita.rubaUnMazzo(giocatore1, giocatore2);
    }

    @FXML
    void prendiUnaCartaDalTavolo() {
        partita.prendiCartaDaTavolo(giocatore1);
    }

    private void caricaPartita(String codicePartita) {
        partita = amministratore.caricaPartita(codicePartita);
    }
}
