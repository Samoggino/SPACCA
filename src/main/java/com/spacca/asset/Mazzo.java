package com.spacca.asset;

import java.util.*;

import com.spacca.asset.carte.Seme;
import com.spacca.asset.carte.Nome;

public class Mazzo {
    private List<Carta> carte;

    private List<Carta> creaMazzo() {
        return creaMazzo(4);
    }

    public Mazzo(int numeroDiMatte) {
        carte = creaMazzo();
    }

    private List<Carta> creaMazzo(int numeroDiMatte) {
        // Aggiungi carte normali
        List<Carta> mazzo = new ArrayList<>();
        for (Seme seme : Seme.values()) {
            for (Nome nome : Nome.values()) {
                if (nome != Nome.MATTA) {
                    Carta carta = new Carta(seme, nome);
                    mazzo.add(carta);
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            mazzo.add(new Carta(Seme.MATTA, Nome.MATTA));
        }

        Collections.shuffle(mazzo);

        return mazzo;
    }

    public void aggiungiCarta(Carta carta) {
        carte.add(carta);
    }

    public List<Carta> getCarte() {
        return carte;
    }
}