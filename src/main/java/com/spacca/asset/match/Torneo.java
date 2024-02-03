package com.spacca.asset.match;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.spacca.asset.utente.Amministratore;
import com.spacca.database.GiocatoreHandler;
import com.spacca.database.PartitaHandler;
import com.spacca.database.TorneoHandler;

public class Torneo extends Object {

    @SerializedName("codiceTorneo")
    String codice;

    @SerializedName("codiciPartite")
    List<String> codiciPartite = new ArrayList<>();

    @SerializedName("partecipanti")
    List<String> partecipanti = new ArrayList<>();

    transient List<Partita> partite = new ArrayList<>();

    public Torneo(String codice, List<String> partecipanti) {
        this.codice = codice;

        // i partecipanti devono essere fare parte della curva esponenziale 2^n
        // quindi 2, 4, 8, 16...        

        this.partecipanti = partecipanti;

        new TorneoHandler().mkdir(codice);
    }

    public List<String> getPartecipanti() {
        return this.partecipanti;
    }

    public void setPartecipanti(List<String> partecipanti) {
        this.partecipanti = partecipanti;
        // salvaToreno();
    }

    public List<Partita> getPartite() {
        if (getCodiciPartite().size() > 0) {
            for (String codicePartita : getCodiciPartite()) {
                this.partite.add(new PartitaHandler().carica(codicePartita));
            }
        }
        return this.partite;
    }

    public List<String> getCodiciPartite() {
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

        // dalle partite esce uno sconfitto, questo sconfitto viene eliminato dalla
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

    // public List<String> addGiocatoreAlTorneo(String username) {
    // if (this.partecipanti == null) {
    // this.partecipanti = new ArrayList<>();
    // }
    // this.partecipanti.add(username);
    // salvaToreno();
    // return this.partecipanti;
    // }

    private Torneo salvaToreno() {
        new TorneoHandler().salva(this, this.codice);
        return this;
    }

    public boolean possoPassareAlTurnoSuccessivo() {
        // TUTTE le partite devono avere un vincitore

        for (String codice : this.codiciPartite) {
            Partita partita = new PartitaHandler().carica(codice);
            if (!partita.hasWinner()) {
                return false;
            }
        }
        return true;
    }

    public void addCodicePartitaAlTorneo(String codicePartita) {
        if (this.codiciPartite == null) {
            this.codiciPartite = new ArrayList<>();
        }
        this.codiciPartite.add(codicePartita);
        this.partite.add(new PartitaHandler().carica(codicePartita));
        salvaToreno();
    }

    public Torneo nuovoTurnoDelTorneo() {
        // un nuovo turno del torneo consiste nel calcolare i vincitori di ogni partita
        // e metterli in una lista,
        // e cancellare i perdenti dalla lista dei partecipanti e le vecchie partite.

        // poi si creano nuove partite con i vincitori e si aggiungono al torneo
        // e si salva il torneo

        // poi si restituisce il torneo

        // se non posso passare al turno successivo, restituisco il torneo stesso
        if (!possoPassareAlTurnoSuccessivo()) {
            return this;
        }
        if (this.partecipanti.size() == 1) {
            System.out.println("Il vincitore del torneo è: " + partecipanti);
            return this;
        }

        // altrimenti calcolo i vincitori e creo nuove partite
        List<String> vincitori = new ArrayList<>();

        for (String codicePartita : this.codiciPartite) {
            Partita partita = new PartitaHandler().carica(codicePartita);
            vincitori.add(partita.getVincitore());
            new PartitaHandler().elimina(partita.getCodice());
        }

        for (String username : this.partecipanti) {
            new GiocatoreHandler().carica(username).removeCodiceTorneo(this.codice);
        }

        this.partecipanti = vincitori;

        this.codiciPartite = new ArrayList<>();

        if (vincitori.size() > 1) {
            System.out.println("Passano al turno successivo: " + vincitori);
            CreatoreDiTorneo.strutturaTorneo(codice, vincitori, this, new Amministratore());
        }

        // creo nuove partite
        return salvaToreno();

    }

    public String stampa() {
        return "Torneo: " + this.codice + " partecipanti: " + this.partecipanti + " partite: " + this.codiciPartite;
    }

    @Override
    public String toString() {
        return stampa();
    }

}