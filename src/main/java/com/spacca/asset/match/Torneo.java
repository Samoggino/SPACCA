package com.spacca.asset.match;

import java.util.ArrayList;
import java.util.List;

import com.spacca.asset.utente.giocatore.AbstractGiocatore;

public class Torneo {

    String codice;
    List<Partita> partite;
    List<AbstractGiocatore> classifica;

    public Torneo(String codice) {
        this.codice = codice;
    }

    public List<Partita> getPartite() {
        return partite;
    }

    // risultato partite
    public List<AbstractGiocatore> getLeaderboard() {

        /**
         * //FIXME: probabilmente la leaderboard non è una stringa, ma un oggetto
         * che contiene i risultati delle partite e i giocatori che hanno partecipato
         */
        List<AbstractGiocatore> leaderboard = new ArrayList<>();
        for (Partita partita : partite) {
            leaderboard.addAll(partita.getListaDeiGiocatori());
        }
        return leaderboard;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    void creaPartita() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'creaPartita'");
    }

    void accediPartita(String codicePartita) {
        if (codicePartita.equals(this.codice)) {
            // TODO: accedi alla partita
        } else {
            // TODO: mostra pagina che dice che il codice è sbagliato
        }
    }

    public void setPartite(List<Partita> partite) {
        this.partite = partite;
    }
}
