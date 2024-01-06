package com.spacca.asset.carte;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Mazzo {

    @SerializedName("carte nel mazzo")
    private List<Carta> carteNelMazzo;

    public Mazzo() {
        this.carteNelMazzo = creaMazzo();
    }

    public Mazzo(List<Carta> mazzoUtente) {
        this.carteNelMazzo = mazzoUtente;
    }

    private List<Carta> creaMazzo() {
        // Aggiungi carte normali
        List<Carta> mazzo = new ArrayList<>();
        for (Seme seme : Seme.values()) {
            for (Nome nome : Nome.values()) {
                Carta carta = new Carta(seme, nome);
                mazzo.add(carta);
            }
        }
        Collections.shuffle(mazzo);
        return mazzo;
    }

    public List<Carta> getMazzo() {
        return carteNelMazzo;
    }

    public String stampa() {
        String stampa = "";
        for (Carta carta : carteNelMazzo) {
            stampa += carta.stampa() + "\n";
        }
        return stampa;
    }

    @Override
    public String toString() {
        return "Mazzo di carte:\n" + stampa() + "";
    }

    public List<Carta> getCarteNelMazzo() {
        return carteNelMazzo;
    }

    public void setCarteNelMazzo(List<Carta> carte) {
        this.carteNelMazzo = carte;
    }

    public Mazzo aggiungiCartaAlMazzo(Carta carta) {
        this.carteNelMazzo.add(carta);
        return this;
    }
}