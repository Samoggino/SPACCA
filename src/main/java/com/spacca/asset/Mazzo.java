package com.spacca.asset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.spacca.asset.carte.Nome;
import com.spacca.asset.carte.Seme;

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

    // public void aggiungiCarta(Carta carta) {
    // carte.add(carta);
    // }

    public List<Carta> getMazzo() {
        return carte;
    }
}