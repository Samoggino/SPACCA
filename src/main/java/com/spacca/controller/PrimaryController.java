package com.spacca.controller;

import java.util.ArrayList;
import java.util.List;

import com.spacca.asset.match.Partita;
import com.spacca.asset.utente.Amministratore;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.asset.utente.giocatore.Giocatore;
import com.spacca.database.FileHandler;

import javafx.fxml.FXML;

public class PrimaryController {
    FileHandler fileHandler = new FileHandler();
    Amministratore amministratore = new Amministratore();

    @FXML
    void provaConCarte() {
        fileHandler.stampaCarteFromJson();
    }

    @FXML
    void continuaPartita() {

        Partita partita = amministratore.caricaPartita("P3456");
        System.out.println(partita);
    }

    @FXML
    void nuovaPartita() {

        List<AbstractGiocatore> giocatori = new ArrayList<>(); // Inizializzazione della lista

        giocatori.add(new Giocatore("pierpaolo", "cicciogamer89")); // Primo oggetto Giocatore
        giocatori.add(new Giocatore("bernardo", "cicciogamer89")); // Secondo oggetto Giocatore

        Partita partita = amministratore.creaPartita(giocatori);

        System.out.println(partita + " in " + this.getClass().getName());
    }

    /**
     * Ovviamente questo metodo non è da tenere, è solo per testare il funzionamento
     */
    @FXML
    void comboCreaContinuaPartita() {

        List<AbstractGiocatore> giocatori = new ArrayList<>(); // Inizializzazione della lista
        giocatori.add(new Giocatore("Yoshi", "cicciogamer89")); // Primo oggetto Giocatore
        giocatori.add(new Giocatore("Mario", "cicciogamer89")); // Secondo oggetto Giocatore

        for (AbstractGiocatore abstractGiocatore : giocatori) {
            System.out.println(abstractGiocatore);
        }

        Partita partita = amministratore.creaPartita(giocatori);
        System.out.println(partita + "\npartita creata");

    }
}
