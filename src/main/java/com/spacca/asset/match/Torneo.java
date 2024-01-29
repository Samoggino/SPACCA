package com.spacca.asset.match;

import java.util.ArrayList;
import java.util.List;

import com.spacca.asset.utente.giocatore.AbstractGiocatore;

public class Torneo {

    String codice;
    List<Partita> partite;
    List<AbstractGiocatore> classifica;
    List<String> giocatori;

    public Torneo(String codice, List<String> giocatoriScelti) {
        this.codice = codice;
        this.giocatori = giocatoriScelti;
    }

    public List<String> getGiocatori() {
        return giocatori;
    }

    public void setGiocatori(List<String> giocatori) {
        this.giocatori = giocatori;
    }

    public void addGiocatore(String giocatore) {
        if (giocatori.size() <= 4) {
            giocatori.add(giocatore);
        } else {
            System.out.println("Torneo al completo, non è possibile aggiungere giocatori");
        }

    }

    public void deleteGiocatore(String giocatore) {
        if (giocatori.size() >= 2) {
            giocatori.remove(giocatore);
        } else {
            System.out.println(
                    "Non è possibile rimuovere il giocatore. \n se elimini il giocatore elimini tutto il torneo ");
        }
    }

    public List<Partita> getPartite() {
        return partite;
    }

    // risultato partite
    public List<String> getLeaderboard() {

        /**
         * //FIXME: probabilmente la leaderboard non è una stringa, ma un oggetto
         * che contiene i risultati delle partite e i giocatori che hanno partecipato
         */
        List<String> leaderboard = new ArrayList<>();
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