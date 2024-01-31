package com.spacca.asset.match;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;
import com.spacca.database.TorneoHandler;

public class Torneo {

    @SerializedName("codiceTorneo")
    String codice;

    transient List<Partita> partite;

    @SerializedName("codiciPartite")
    List<String> codiciPartite;

    @SerializedName("classifica")
    Map<String, Integer> classifica = new HashMap<>();

    @SerializedName("partecipanti")
    List<String> partecipanti;

    public Torneo(String codice, List<String> partecipanti) {
        this.codice = codice;
        this.partecipanti = partecipanti;

        new TorneoHandler().mkdir(codice);
    }

    public Torneo creaTorneo(String codice, List<String> partecipanti) {

        this.codice = codice;
        this.partecipanti = partecipanti;

        new TorneoHandler().mkdir(codice);
        return this;
    }

    public List<String> getPartecipanti() {
        return partecipanti;
    }

    public void setPartecipanti(List<String> partecipanti) {
        this.partecipanti = partecipanti;
    }

    public void addGiocatore(String giocatore) {
        if (partecipanti.size() <= 4) {
            partecipanti.add(giocatore);
        } else {
            System.out.println("Torneo al completo, non è possibile aggiungere giocatori");
        }

    }

    public void deleteGiocatore(String giocatore) {
        if (partecipanti.size() >= 2) {
            partecipanti.remove(giocatore);
        } else {
            System.out.println(
                    "Non è possibile rimuovere il giocatore. \n se elimini il giocatore elimini tutto il torneo ");
        }
    }

    public List<Partita> getPartite() {
        if (this.partite == null) {
            this.partite = new ArrayList<>();
        }
        return this.partite;
    }

    public List<String> getCodiciPartite() {
        if (this.codiciPartite == null) {
            this.codiciPartite = new ArrayList<>();
        }
        return this.codiciPartite;
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
        // voglio creare delle partite di un torneo con due giocatori per ogni partita
        // e poi aggiungere le partite al torneo

        // le dalle partite esce uno sconfitto, questo sconfitto viene eliminato dalla
        // classifica, in modo da avere sempre il record dei giocatori che hanno
        // partecipato al torneo
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

    public Map<String, Integer> getClassifica() {
        return classifica;
    }

    public List<String> addGiocatoreAlTorneo(String username) {
        if (this.partecipanti == null) {
            this.partecipanti = new ArrayList<>();
        }
        this.partecipanti.add(username);
        salvaToreno();
        return this.partecipanti;
    }

    private Torneo salvaToreno() {
        new TorneoHandler().salva(this, this.codice);
        return this;
    }

}