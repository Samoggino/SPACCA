package com.spacca.asset.carte;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.spacca.database.FileHandler;

public class Mazzo {

    @SerializedName("carte:")
    private List<Carta> carteNelMazzo;

    public Mazzo() {
        this.carteNelMazzo = new ArrayList<>(); // Initialize the list in the default constructor
    }

    public Mazzo(List<Carta> mazzoUtente) {
        this.carteNelMazzo = mazzoUtente;
    }

    public Mazzo creaMazzoDiPartenza() {
        try {
            FileHandler fileHandler = new FileHandler();
            this.carteNelMazzo = new ArrayList<>(Arrays.asList(fileHandler.prendiLeCarteDalJson()));

            Collections.shuffle(this.carteNelMazzo);
            System.out.println("Mazzo iniziale creato con successo!");
        } catch (NullPointerException e) {
            System.err.println("Errore nel creare il mazzo di partenza\n\n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore generico nel creare il mazzo di partenza\n\n" + e.getMessage());
        }
        return this;
    }

    private String stampa() {
        String stampa = "";
        if (carteNelMazzo == null) {
            return "Nessuna carta nel mazzo.";
        }

        int i = 0; // contatore per vedere se stampa tutte le 40 carte
        for (Carta carta : carteNelMazzo) {
            i++;
            stampa += i + ": " + carta.stampa() + "\n";
        }
        return stampa;
    }

    @Override
    public String toString() {
        return stampa();
    }

    public List<Carta> getCarteNelMazzo() {
        return this.carteNelMazzo;
    }

    public void setCarteNelMazzo(List<Carta> carte) {
        this.carteNelMazzo = carte;
    }

    public void aggiungiCartaAlMazzo(Carta carta) {
        if (this.carteNelMazzo == null) {
            this.carteNelMazzo = new ArrayList<>(); // Initialize the list if null
            this.carteNelMazzo.add(carta);
        }
        this.carteNelMazzo.add(carta);
    }

    public void aggiungiListaCarteAdAltroMazzo(List<Carta> mano) {
        if (this.carteNelMazzo == null) {
            this.carteNelMazzo = new ArrayList<>(); // Initialize the list if null
            this.carteNelMazzo.addAll(mano);
        }
        this.carteNelMazzo.addAll(mano);
    }

    public Carta rimuoviCartaDalMazzo(int posizioneDellaCarta) throws IndexOutOfBoundsException {
        return this.carteNelMazzo.remove(posizioneDellaCarta);
    }

    public int size() {
        return this.carteNelMazzo.size();
    }
}