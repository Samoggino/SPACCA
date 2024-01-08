package com.spacca.controller;

import java.util.ArrayList;
import java.util.List;

import com.spacca.asset.match.Partita;
import com.spacca.asset.utente.Amministratore;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.asset.utente.giocatore.Giocatore;
import com.spacca.database.FileHandler;

import javafx.fxml.FXML;

public class PartitaController {
    FileHandler fileHandler = new FileHandler();
    Amministratore amministratore = new Amministratore();
    Partita partita;
    AbstractGiocatore giocatore1 = new Giocatore("Yoshi", "");
    AbstractGiocatore giocatore2 = new Giocatore("Mario", "");
    AbstractGiocatore giocatore3 = new Giocatore("Peach", "");
    AbstractGiocatore giocatore4 = new Giocatore("Luigi", "");
    AbstractGiocatore giocatore5 = new Giocatore("Toad", "");
    AbstractGiocatore giocatore6 = new Giocatore("Bowser", "");
    List<AbstractGiocatore> giocatoriDellaPartita = new ArrayList<>(); // Inizializzazione della lista

    @FXML
    void nuovaPartita() {

        giocatoriDellaPartita.add(giocatore1);
        giocatoriDellaPartita.add(giocatore2);
        giocatoriDellaPartita.add(giocatore3);
        giocatoriDellaPartita.add(giocatore4);
        giocatoriDellaPartita.add(giocatore5);
        giocatoriDellaPartita.add(giocatore6);

        partita = amministratore.creaPartita(giocatoriDellaPartita);

        System.out.println("Mazzo di gioco prima di giocare" + partita.getMazzoDiGioco());

        partita.nuovoTurno(1);

        System.out.println(partita.getMazzoDiGioco());
        System.out.println(partita.stampaManoDeiGiocatori());
        System.out.println(partita.getCarteSulTavolo());
        System.out.println("Codice:\t" + partita.getCodice());

    }

    @FXML
    void gioca() {
        // partita.gioca(giocatore1, 2);
        System.out.println("Carte di " + giocatore1.getUsername() + "\n" + partita.getMano(giocatore1.getUsername()));
        System.out.println("Carte di " + giocatore2.getUsername() + "\n" + partita.getMano(giocatore2.getUsername()));
        System.out.println("Carte di " + giocatore3.getUsername() + "\n" + partita.getMano(giocatore3.getUsername()));
        System.out.println("Carte di " + giocatore4.getUsername() + "\n" + partita.getMano(giocatore4.getUsername()));
        System.out.println("Carte di " + giocatore5.getUsername() + "\n" + partita.getMano(giocatore5.getUsername()));
        System.out.println("Carte di " + giocatore6.getUsername() + "\n" + partita.getMano(giocatore6.getUsername()));
        System.out.println(partita.getRisultato());
    }

}
