package com.spacca.asset.match;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;
import com.spacca.asset.carte.Carta;
import com.spacca.asset.carte.Mazzo;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.database.FileHandler;

/**
 * La classe <strong>Partita</strong> rappresenta un'istanza di una partita.
 * Contiene informazioni sui giocatori coinvolti, le carte in mano ai giocatori,
 * le carte prese dai giocatori, il mazzo di gioco e il risultato della partita.
 */
public class Partita {

    // lista dei giocatori
    @SerializedName("lista dei giocatori")
    private List<AbstractGiocatore> listaDeiGiocatori = new ArrayList<>();

    // carte in mano ai giocatori
    @SerializedName("mano del giocatore")
    private Map<String, Mazzo> manoDelGiocatore;

    // carte prese dai giocatori
    @SerializedName("prese di ogni giocatore")
    private Map<String, Mazzo> cartePreseDiOgniGiocatore;

    // mazzo di gioco e piatto
    @SerializedName("mazzo di gioco")
    private Mazzo mazzoDiGioco;

    @SerializedName("carte sul tavolo")
    private Mazzo carteSulTavolo;

    // risultato della partita
    @SerializedName("risultato")
    private String risultato = "risultato default";

    // codice della partita
    @SerializedName("codice")
    private String codice = "codice default";

    private FileHandler fileHandler = new FileHandler();

    public Partita(String codice, List<AbstractGiocatore> giocatori) {

        this.codice = codice;
        this.listaDeiGiocatori = giocatori;
        this.risultato = "Ancora da giocare";

        creaPartita(giocatori);
    }

    private void creaPartita(List<AbstractGiocatore> giocatori) {
        // Crea il mazzo di gioco
        setMazzoDiGioco(new Mazzo().creaMazzoDiPartenza());

        // Crea il mazzo di carte sul tavolo
        setCarteSulTavolo(new Mazzo());

        // Crea la mano dei giocatori
        setManoDelGiocatore(new HashMap<>());

        // Crea le carte prese di ogni giocatore
        setCartePreseDiOgniGiocatore(new HashMap<>());

        // setup degli utenti
        for (AbstractGiocatore giocatore : giocatori) {
            this.manoDelGiocatore.put(giocatore.getUsername(), new Mazzo());
            this.cartePreseDiOgniGiocatore.put(giocatore.getUsername(), new Mazzo());
        }

    }

    public Partita(String codice, List<AbstractGiocatore> giocatori, String risultato) {
        this.codice = codice;
        this.listaDeiGiocatori = giocatori;
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
        fileHandler.eliminaPartita(this.codice);
    }

    public String getRisultato() {
        return risultato;
    }

    public String stampa() {
        String stampa = "\nPartita: \n";

        int i = 0;
        for (AbstractGiocatore giocatore : listaDeiGiocatori) {
            if (i == 1) {
                stampa += "\tvs\t";
            }
            stampa += giocatore.getUsername();
            i++;
        }
        stampa += "\nCodice " + this.codice + "\nRisultato: " + this.risultato + "\n";

        return stampa;
    }

    @Override
    public String toString() {
        return stampa();
    }

    public List<AbstractGiocatore> getListaDeiGiocatori() {
        return listaDeiGiocatori;
    }

    public void setListaDeiGiocatori(List<AbstractGiocatore> giocatori) {
        this.listaDeiGiocatori = giocatori;
        salvaPartita();
    }

    public void setRisultato(String risultato) {
        this.risultato = risultato;
        salvaPartita();
    }

    public Mazzo getMano(String nickname) {
        return this.manoDelGiocatore.get(nickname);
    }

    public void setManoDelGiocatore(Map<String, Mazzo> manoDelGiocatore) {
        this.manoDelGiocatore = manoDelGiocatore;
        salvaPartita();
    }

    public Map<String, Mazzo> getCartePreseDiOgniGiocatore() {
        return cartePreseDiOgniGiocatore;
    }

    public void setCartePreseDiOgniGiocatore(Map<String, Mazzo> cartePreseDiOgniGiocatore) {
        this.cartePreseDiOgniGiocatore = cartePreseDiOgniGiocatore;
        salvaPartita();
    }

    public Mazzo getMazzoDiGioco() {
        return mazzoDiGioco;
    }

    public void setMazzoDiGioco(Mazzo mazzoDiGioco) {
        this.mazzoDiGioco = mazzoDiGioco;
        salvaPartita();
    }

    public Mazzo getCarteSulTavolo() {
        return carteSulTavolo;
    }

    public void setCarteSulTavolo(Mazzo carteSulTavolo) {
        this.carteSulTavolo = carteSulTavolo;
        salvaPartita();
    }

    public void pesca(String nickname) {

        Carta cartaPescata = pesca();
        aggiungiCartaAlMazzoDellUtente(nickname, cartaPescata);
        salvaPartita();
    }

    public Carta pesca() {
        return this.mazzoDiGioco.getMazzo().remove(this.mazzoDiGioco.getMazzo().size() - 1);
    }

    public void aggiungiCartaAlMazzoDellUtente(String nickname, Carta carta) {

        System.out.println("Mano del giocatore: " + getMano(nickname));
        this.manoDelGiocatore.put(nickname, carteSulTavolo);
        salvaPartita();
        try {
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
