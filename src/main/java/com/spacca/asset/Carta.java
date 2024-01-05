package com.spacca.asset;

import com.spacca.asset.carte.Nome;
import com.spacca.asset.carte.Seme;

public class Carta {

    /**
     * Punteggi delle carte che volendo potrebbero essere modificati
     * dall'amministratore, ma per ora sono fissi
     */
    final static int PUNTI_ASSO = 15;
    final static int PUNTI_FIGURA = 10;
    final static int PUNTI_CARTA = 5;
    final static int PUNTI_MATTA = PUNTI_CARTA;

    public Seme seme;
    public Nome nome;
    public String numero;
    public int punti;
    public ImmagineCarta immagine;

    public Carta(Seme seme, Nome nome) {
        this.seme = seme;
        this.nome = nome;
        this.punti = setPunti(nome);
    }

    public Carta(String seme, String nome) {
        this.seme = Seme.valueOf(seme);
        this.nome = Nome.valueOf(nome);
        this.punti = setPunti(this.nome);
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

    public String stampa() {
        return "Carta: " + this.nome + " di " + this.seme + " - " + this.punti + " punti" + " - " + this.immagine;
    }

    public int getPunti() {
        return punti;
    }

    public int setPunti(Nome nome) {
        int punteggioCarta = 0;

        switch (nome) {
            case ASSO:
                punteggioCarta = PUNTI_ASSO;
                break;
            case FANTE:
            case CAVALLO:
            case RE:
                punteggioCarta = PUNTI_FIGURA;
                break;
            case DUE:
            case TRE:
            case QUATTRO:
            case CINQUE:
            case SEI:
                punteggioCarta = PUNTI_CARTA;
                break;
            case SETTE:
                punteggioCarta = PUNTI_MATTA;
                break;
            default:
                System.err.println("ERRORE: Nessun caso corrispondente");
                break;
        }
        return punteggioCarta;
    }

    @Override
    public String toString() {
        return stampa();
    }

}
