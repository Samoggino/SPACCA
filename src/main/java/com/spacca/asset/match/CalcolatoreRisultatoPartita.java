package com.spacca.asset.match;

import java.util.Map;

import com.spacca.asset.carte.Carta;
import com.spacca.asset.carte.Mazzo;

/**
 * Questa classe calcola il risultato di una partita sommando i punti dei mazzi
 * di ciascun giocatore.
 */
public class CalcolatoreRisultatoPartita {

    /**
     * Calcola il risultato di una partita basandosi sulla mappa fornita dei mazzi
     * delle prese dei giocatori.
     *
     * @param preseDeiGiocatori una mappa contenente i mazzi dei giocatori
     * @return una rappresentazione testuale del risultato, mostrando i punti di
     *         ciascun giocatore
     */
    public static String calcolaRisultato(Map<String, Mazzo> preseDeiGiocatori) {
        StringBuilder risultatoBuilder = new StringBuilder();

        preseDeiGiocatori.forEach((username, mazzo) -> {
            int punti = mazzo
                    .getCarteNelMazzo()
                    .stream()
                    .mapToInt(Carta::getPunti)
                    .sum();
            risultatoBuilder.append(String.format("%s: %d\n", username, punti));
        });

        return "\n" + risultatoBuilder.toString();
    }

}