package com.spacca.database;

public interface DatabaseI {
    void creaPartita();

    void salvaPartita();

    void caricaPartita();

    void eliminaPartita();

    void creaTorneo();

    void salvaTorneo();

    void caricaTorneo();

    void eliminaTorneo();

    void creaProfiloGiocatore();

    void modificaProfiloGiocatore();

    void aggiungiGiocatoreAlTorneo();

    void login();

    void logout();

    void mostraLeaderboard();

}
