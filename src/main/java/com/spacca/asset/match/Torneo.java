package com.spacca.asset.match;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.spacca.App;
import com.spacca.asset.utente.Amministratore;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.controller.TavoloController;
import com.spacca.database.GiocatoreHandler;
import com.spacca.database.PartitaHandler;
import com.spacca.database.TorneoHandler;

import javafx.fxml.FXMLLoader;

public class Torneo extends Object {

    @SerializedName("codiceTorneo")
    String codice;

    @SerializedName("codiciPartite")
    List<String> codiciPartite = new ArrayList<>();

    @SerializedName("giocatori rimasti")
    List<String> giocatoriRimasti = new ArrayList<>();

    @SerializedName("partecipanti")
    List<String> partecipanti = new ArrayList<>();

    transient List<Partita> partite = new ArrayList<>();

    @SerializedName("leaderboard")
    String leaderboard = "";

    @SerializedName("vincitore")
    String vincitore;

    public Torneo(String codice, List<String> partecipanti) {

        this.codice = codice;
        this.giocatoriRimasti = partecipanti;
        this.partecipanti = partecipanti;

        new TorneoHandler().mkdir(codice);
    }

    public List<String> getGiocatoriRimasti() {
        return this.giocatoriRimasti;
    }

    public void setGiocatoriRimasti(List<String> partecipanti) {
        this.giocatoriRimasti = partecipanti;
        // salvaToreno();
    }

    public List<Partita> getPartite() {
        if (this.partite == null) {
            this.partite = new ArrayList<>();
        }
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

    public String aggiornaLeaderboard() {

        String dettaglio = "";
        if (giocatoriRimasti.size() == 1) {
            dettaglio = "\t\t\tIl vincitore";
        } else if (giocatoriRimasti.size() == partecipanti.size()) {
            dettaglio = "\tPartecipanti";
        }

        return this.leaderboard = this.giocatoriRimasti + dettaglio + "\n" + this.leaderboard;
    }

    public String getLeaderboard() {
        return this.leaderboard;
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

        simulaPartiteCPU();

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

    public Partita getPartitaDelGiocatore(String username) {

        for (Partita partita : getPartite()) {
            if (partita.getListaDeiGiocatori().contains(username)) {
                return partita;
            }
        }
        return null;
    }

    public Partita getPartitaDelGiocatore(AbstractGiocatore giocatore) {
        return getPartitaDelGiocatore(giocatore.getUsername());
    }

    public Torneo nuovoTurnoDelTorneo() {
        // un nuovo turno del torneo consiste nel calcolare i vincitori di ogni partita
        // e metterli in una lista,
        // e cancellare i perdenti dalla lista dei partecipanti e le vecchie partite.

        try {

            // poi si creano nuove partite con i vincitori e si aggiungono al torneo
            // e si salva il torneo

            // poi si restituisce il torneo

            // se non posso passare al turno successivo, restituisco il torneo stesso

            aggiornaLeaderboard();

            if (this.vincitore != null) {
                return this;
            }

            if (!possoPassareAlTurnoSuccessivo()) {
                return this;
            }

            // altrimenti calcolo i vincitori e creo nuove partite
            List<String> vincitori = new ArrayList<>();

            for (String codicePartita : this.codiciPartite) {
                Partita partita = new PartitaHandler().carica(codicePartita);
                vincitori.add(partita.getVincitore());
                new PartitaHandler().elimina(partita.getCodice());
            }

            for (String username : this.giocatoriRimasti) {
                new GiocatoreHandler().carica(username).removeCodiceTorneo(this.codice);
            }

            this.giocatoriRimasti = vincitori;

            this.codiciPartite = new ArrayList<>();

            if (vincitori.size() > 1) {
                System.out.println("Passano al turno successivo: " + vincitori);
                CreatoreDiTorneo.strutturaTorneo(codice, vincitori, this, new Amministratore());
            }

            if (this.giocatoriRimasti.size() == 1) {
                System.out.println("Il vincitore del torneo è: " + giocatoriRimasti);
                this.vincitore = giocatoriRimasti.get(0);
            }

        } catch (Exception e) {
            System.err.println("Errore nel nuovo turno del torneo: " + e.getMessage());
            e.printStackTrace();
        }

        // creo nuove partite
        return salvaToreno();

    }

    public void simulaPartiteCPU() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/spacca/pages/tavolo.fxml"));

            loader.load();

            TavoloController tavolo = loader.getController();
            loader.setController(tavolo);

            for (String codicePartita : getCodiciPartite()) {
                Partita partita = new PartitaHandler().carica(codicePartita);
                boolean containsRealPlayer = false;
                for (String username : partita.getListaDeiGiocatori()) {
                    if (!new GiocatoreHandler().carica(username).isCPU()) {
                        containsRealPlayer = true;
                        break;
                    }
                }

                if (!containsRealPlayer) {
                    if (!partita.hasWinner()) {
                        tavolo.initController(partita, true);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Errore nella simulazione delle partite: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String stampa() {
        return "Torneo: " + this.codice + " partecipanti: " + this.giocatoriRimasti + " partite: " + this.codiciPartite;
    }

    @Override
    public String toString() {
        return stampa();
    }

    public List<String> getPartecipanti() {
        return this.partecipanti;
    }

}