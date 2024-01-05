package com.spacca.asset.match;

import java.util.List;

public class Torneo {

    String codice;
    List<Partita> partite;

    public Torneo(String codice) {
        this.codice = codice;
    }

    public List<Partita> getPartite() {
        return partite;
    }

    // risultato partite
    public String getLeaderboard() {

        /**
         * //FIXME: probabilmente la leaderboard non è una stringa, ma un oggetto
         * che contiene i risultati delle partite e i giocatori che hanno partecipato
         */
        String leaderboard = "";
        for (Partita partita : partite) {
            leaderboard = partita.getRisultato() + "\n";
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
}
