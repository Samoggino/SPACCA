package com.spacca.asset.match;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;
import com.spacca.asset.carte.Carta;
import com.spacca.asset.carte.Mazzo;
import com.spacca.asset.carte.Nome;
import com.spacca.asset.carte.Seme;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.database.FileHandler;

/**
 * La classe <strong>Partita</strong> rappresenta un'istanza di una partita.
 * Contiene informazioni sui giocatori coinvolti, le carte in mano ai giocatori,
 * le carte prese dai giocatori, il mazzo di gioco e il risultato della partita.
 */
public class Partita {

    // lista dei giocatori
    @SerializedName("giocatori")
    private List<AbstractGiocatore> giocatori = new ArrayList<>();

    // carte in mano ai giocatori
    @SerializedName("mano del giocatore")
    private Map<String, Mazzo> manoDellUtente = new HashMap<>();

    // carte prese dai giocatori
    @SerializedName("tutte le prese del giocatore")
    private Map<String, Mazzo> mazzoDellePreseDellUtente = new HashMap<>();

    // mazzo di gioco e piatto
    @SerializedName("mazzo di gioco")
    private Mazzo mazziDiGioco;

    @SerializedName("carte sul tavolo")
    private Mazzo piatto;

    // risultato della partita
    @SerializedName("risultato")
    private String risultato = "risultato default";

    // codice della partita
    @SerializedName("codice")
    private String codice = "codice default";

    private FileHandler fileHandler = new FileHandler();

    public Partita(String codice, List<AbstractGiocatore> giocatori) {

        this.codice = codice;
        this.giocatori = giocatori;
        this.risultato = "Ancora da giocare";
        this.mazziDiGioco = new Mazzo();

        List<Carta> carteDellUtente1 = new ArrayList<>();
        List<Carta> carteDellUtente2 = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            this.manoDellUtente.put(giocatori.get(i).getNickname(), new Mazzo(carteDellUtente1));
        }

        for (int i = 0; i < 2; i++) {
            carteDellUtente1.add(mazziDiGioco.getMazzo().get(i));
        }
        Mazzo mazzo1 = new Mazzo(carteDellUtente1);
        Mazzo mazzo2 = new Mazzo(carteDellUtente2);

        this.mazzoDellePreseDellUtente
                .put(giocatori.get(0).getNickname(), mazzo1.aggiungiCartaAlMazzo(new Carta(Seme.SPADE, Nome.ASSO)));

        this.mazzoDellePreseDellUtente
                .put(giocatori.get(1).getNickname(), mazzo2.aggiungiCartaAlMazzo(new Carta(Seme.COPPE, Nome.RE)));
    }

    public Partita(String codice, List<AbstractGiocatore> giocatori, String risultato) {
        this.codice = codice;
        this.giocatori = giocatori;
        this.risultato = risultato;
    }

    public Partita(String codice) {
        this.codice = codice;
        caricaPartita(codice);
    }

    public String getCodice() {
        return this.codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public void salvaPartita() {
        fileHandler.salvaPartita(this, this.codice);
    }

    public void caricaPartita(String codicePartita) {
        fileHandler.caricaPartita(codicePartita);
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
        salvaPartita();
    }

    public void setRisultato(String risultato) {
        this.risultato = risultato;
        salvaPartita();
    }
}
