package com.spacca.asset.utente;

import java.util.List;
import java.util.Random;

import com.spacca.asset.match.Partita;
import com.spacca.asset.match.Torneo;
import com.spacca.asset.utente.giocatore.GiocatoreInterface;
import com.spacca.database.Database;

/**
 * AmministratoreI
 */
public class Amministratore {
    void creaPartita() {
        // TODO
    }

    void creaTorneo() {
        // TODO
    }

    void eliminaPartita() {
        // TODO
    }

    void eliminaTorneo() {
        // TODO
    }

    void login() {
        // TODO
    }

    void logout() {
        // TODO
    }

    void aggiungiGiocatoreAlTorneo() {
        // TODO
    }

    void creaProfiloGiocatore() {
        // TODO
    }

    void modificaProfiloGiocatore() {
        // TODO
    }

    void creaCodicePartita() {

    }

    public Torneo creaNuovoTorneo() {
        return new Torneo("T" + generaNumeroCasuale());
    }

    protected static int generaNumeroCasuale() {
        // FIXME: non deve generare numeri uguali
        Random random = new Random();
        // Genera un numero casuale compreso tra 1000 e 9999
        int numero = random.nextInt(9000) + 1000;
        return numero;
    }

    public Partita creaPartita(List<GiocatoreInterface> giocatori) {
        Database db = new Database();
        return db.creaPartita("P" + generaNumeroCasuale(), giocatori);
    }

}