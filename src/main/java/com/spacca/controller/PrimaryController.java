package com.spacca.controller;

import java.util.ArrayList;
import java.util.List;

import com.spacca.asset.match.Partita;
import com.spacca.asset.utente.Amministratore;
import com.spacca.asset.utente.giocatore.Giocatore;
import com.spacca.asset.utente.giocatore.GiocatoreInterface;
import com.spacca.database.Database;

import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    void provaConCarte() {
        Database db = new Database();
        db.stampaCarteFromJson();
    }

    @FXML
    void provaConPartita() {
        Amministratore amministratore = new Amministratore();

        List<GiocatoreInterface> giocatori = new ArrayList<>(); // Inizializzazione della lista

        giocatori.add(new Giocatore("piero", "1234")); // Primo oggetto Giocatore
        giocatori.add(new Giocatore("mario", "1234")); // Secondo oggetto Giocatore

        Partita partita = amministratore.creaPartita(giocatori);

        System.out.println(partita);

    }

}
