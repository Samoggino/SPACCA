package com.spacca.asset.utente;

import java.util.Random;

import com.spacca.asset.match.Partita;
import com.spacca.asset.match.Torneo;

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
        return new Torneo(generaNumeroCasuale());
    }

    protected static int generaNumeroCasuale() {
        Random random = new Random();
        // Genera un numero casuale compreso tra 10000 e 99999
        int numero = random.nextInt(90000) + 10000;
        return numero;
    }

    public Partita creaNuovaPartita() {
        return new Partita(generaNumeroCasuale());
    }

}