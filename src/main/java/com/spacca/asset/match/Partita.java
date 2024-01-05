package com.spacca.asset.match;

import java.util.List;

import com.spacca.asset.utente.giocatore.GiocatoreInterface;
import com.spacca.database.Database;

public class Partita {

    String risultato;
    String codice;
    List<GiocatoreInterface> giocatori;

    public Partita(String codice, List<GiocatoreInterface> giocatori) {
        this.codice = codice;
        this.giocatori = giocatori;
    }

    public Partita(String codice, List<GiocatoreInterface> giocatori, String risultato) {
        this.codice = codice;
        this.giocatori = giocatori;
        this.risultato = risultato;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public void salvaPartita() {
        Database db = new Database();
        db.salvaPartita(this, this.codice);
    }

    public void caricaPartita(String codicePartita) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'caricaPartita'");
    }

    public void eliminaPartita() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminaPartita'");
    }

    public String getRisultato() {
        return risultato;
    }

    public String stampa() {
        String stampa = "\nPartita: \n";

        int i = 0;
        for (GiocatoreInterface giocatore : giocatori) {
            if (i == 1) {
                stampa += "\tvs\t";
            }
            stampa += giocatore.getNickname();
            i++;
        }
        stampa += "\nCodice" + this.codice + "\nRisultato: " + this.risultato + "\n";

        return stampa;
    }

    @Override
    public String toString() {
        return stampa();
    }

}
