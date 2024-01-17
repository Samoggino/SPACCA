package com.spacca.asset.utente;

import java.util.List;
import java.util.Random;

import com.spacca.asset.match.Partita;
import com.spacca.asset.match.Torneo;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.database.PartitaHandler;

/**
 * AmministratoreI
 */
public class Amministratore {
    transient PartitaHandler partitaHandler = new PartitaHandler();;

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

    public Partita creaPartita(List<AbstractGiocatore> giocatori) {
        String codicePartita = "P" + generaNumeroCasuale();

        for (AbstractGiocatore abstractGiocatore : giocatori) {
            abstractGiocatore.addCodicePartita(codicePartita);
        }

        return partitaHandler.creaPartita(codicePartita, giocatori);
    }

    public Partita caricaPartita(String string) {
        return partitaHandler.carica(string);
    }

}