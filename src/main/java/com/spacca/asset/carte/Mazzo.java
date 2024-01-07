package com.spacca.asset.carte;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.spacca.database.FileHandler;

public class Mazzo {

    @SerializedName("carte nel mazzo")
    private List<Carta> carteNelMazzo;

    public Mazzo() {
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
            System.out.println("Errore nel creare il mazzo di partenza\n\n" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Errore generico nel creare il mazzo di partenza\n\n" + e.getMessage());
        }
        return this;
    }

    public List<Carta> getMazzo() {
        return carteNelMazzo;
    }

    public String stampa() {
        String stampa = "";
        if (carteNelMazzo == null) {
            return "Nessuna carta nel mazzo.";
        }

        for (Carta carta : carteNelMazzo) {
            stampa += carta.stampa() + "\n";
        }
        return "Mazzo di carte:\n" + stampa;
    }

    @Override
    public String toString() {
        return stampa();
    }

    public List<Carta> getCarteNelMazzo() {
        return carteNelMazzo;
    }

    public void setCarteNelMazzo(List<Carta> carte) {
        this.carteNelMazzo = carte;
    }

    public Mazzo aggiungiCartaAlMazzo(Carta carta, List<Carta> mazzo) {
        this.carteNelMazzo = mazzo;
        this.carteNelMazzo.add(carta);
        return this;
    }
}