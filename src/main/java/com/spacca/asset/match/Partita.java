package com.spacca.asset.match;

public class Partita {

    String risultato;
    int codice;

    public Partita(int codice) {
        this.codice = codice;
    }

    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public void creaPartita() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'creaPartita'");
    }

    public void accediPartita(int codicePartita) {
        if (codicePartita == this.codice) {
            // TODO: accedi alla partita
        } else {
            // TODO: mostra pagina che dice che il codice è sbagliato
        }
    }

    public void salvaPartita() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'salvaPartita'");
    }

    public void caricaPartita() {
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

}
