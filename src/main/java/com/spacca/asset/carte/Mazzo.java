package com.spacca.asset.carte;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mazzo {
    private List<Carta> carte;

    public Mazzo() {
        this.carte = creaMazzo();
    }

    public Mazzo(List<Carta> mazzoUtente) {
        this.carte = mazzoUtente;
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
        return carte;
    }

    public String stampa() {
        String stampa = "";
        for (Carta carta : carte) {
            stampa += carta.stampa() + "\n";
        }
        return stampa;
    }

    @Override
    public String toString() {
        return "Mazzo di carte:\n" + stampa() + "";
    }
}