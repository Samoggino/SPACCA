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

    @FXML
    void nuovaPartita() {
        List<AbstractGiocatore> giocatoriDellaPartita = new ArrayList<>(); // Inizializzazione della lista

        AbstractGiocatore giocatore1 = new Giocatore("pierpaolo", "cicciogamer89", "cicciox@"); // Primo oggetto
        // Giocatore
        AbstractGiocatore giocatore2 = new Giocatore("bernardo", "cicciogamer89", "cicciogamer8@s"); // Secondo oggetto
        // Giocatore

        giocatoriDellaPartita.add(giocatore1); // Aggiunta del primo giocatore alla lista
        giocatoriDellaPartita.add(giocatore2); // Aggiunta del secondo giocatore alla lista

        Partita partita = amministratore.creaPartita(giocatoriDellaPartita);

        System.out.println(partita);

        partita.pesca(giocatore1.getUsername());
        partita.pesca(giocatore2.getUsername());

        System.out.println(partita);
    }

}
