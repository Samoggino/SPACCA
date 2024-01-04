package com.spacca.asset;

import com.spacca.asset.carte.Seme;
import com.spacca.asset.carte.Nome;

public class Carta {
    public Seme seme;
    public Nome nome;
    public String numero;
    public int punti;

    public Carta(Seme seme, Nome nome) {
        this.seme = seme;
        this.nome = nome;
        this.punti = setPunti(nome);
    }

    public Seme getSeme() {
        return seme;
    }

    public void setSeme(Seme seme) {
        this.seme = seme;
    }

    public Nome getNome() {
        return nome;
    }

    public void setNome(Nome valore) {
        this.nome = valore;
    }

    public void stampa() {
        System.out.println("Carta: " + nome + " di " + seme + " - " + punti + " punti");
    }

    public int getPunti() {
        return punti;
    }

    public int setPunti(Nome nome) {
        int punteggioCarta = 0;
        if (nome == Nome.ASSO) {
            punteggioCarta = 15;
        } else if (nome == Nome.FANTE || nome == Nome.CAVALLO || nome == Nome.RE) {
            punteggioCarta = 10;
        } else if (nome == Nome.DUE || nome == Nome.TRE || nome == Nome.QUATTRO || nome == Nome.CINQUE
                || nome == Nome.SEI || nome == Nome.SETTE) {
            punteggioCarta = 5;
        } else if (nome == Nome.MATTA) {
            punteggioCarta = 0;
        }
        return punteggioCarta;
    }
}
