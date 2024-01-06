package com.spacca.controller;

import java.util.ArrayList;
import java.util.List;

import com.spacca.asset.match.Partita;
import com.spacca.asset.utente.Amministratore;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.asset.utente.giocatore.Giocatore;
import com.spacca.database.Database;

import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    void provaConCarte() {
        Database db = new Database();
        db.stampaCarteFromJson();
    }

    @FXML
    void continuaPartita() {
        List<AbstractGiocatore> giocatori = new ArrayList<>(); // Inizializzazione della lista

        giocatori.add(new Giocatore("piero", "1234")); // Primo oggetto Giocatore
        giocatori.add(new Giocatore("mario", "1234")); // Secondo oggetto Giocatore

        Database db = new Database();
        Partita partita2 = db.caricaPartita("P1234");

        System.out.println(partita2);

        // db.salvaPartita(partita2, "P1234");

    }

    @FXML
    void nuovaPartita() {
        Amministratore amministratore = new Amministratore();

        List<AbstractGiocatore> giocatori = new ArrayList<>(); // Inizializzazione della lista

        giocatori.add(new Giocatore("pierpaolo", "cicciogamer89")); // Primo oggetto Giocatore
        giocatori.add(new Giocatore("bernardo", "cicciogamer89")); // Secondo oggetto Giocatore

        Partita partita = amministratore.creaPartita(giocatori);

        System.out.println(partita + " in " + this.getClass().getName());
    }

}
