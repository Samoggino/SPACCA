package com.spacca.asset.match;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.database.Database;

public class Partita {
    
    @SerializedName("giocatori")
    List<AbstractGiocatore> giocatori;

    @SerializedName("risultato")
    String risultato = "risultato default";

    public void setRisultato(String risultato) {
        this.risultato = risultato;
    }

    @SerializedName("codice")
    String codice = "codice default";

    public Partita(String codice, List<AbstractGiocatore> giocatori) {
        this.codice = codice;
        this.giocatori = giocatori;
        this.risultato = "Ancora da giocare";
    }

    public Partita(String codice, List<AbstractGiocatore> giocatori, String risultato) {
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
        for (AbstractGiocatore giocatore : giocatori) {
            if (i == 1) {
                stampa += "\tvs\t";
            }
            stampa += giocatore.getNickname();
            i++;
        }
        stampa += "\nCodice " + this.codice + "\nRisultato: " + this.risultato + "\n";

        return stampa;
    }

    @Override
    public String toString() {
        return stampa();
    }

    public List<AbstractGiocatore> getGiocatori() {
        return giocatori;
    }

    public void setGiocatori(List<AbstractGiocatore> giocatori) {
        this.giocatori = giocatori;
    }

}
