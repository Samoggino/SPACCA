package com.spacca.asset.match;

import java.util.ArrayList;
import java.util.List;
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

    /**
     * Calcola il vincitore della partita basandosi sulla mappa fornita dei mazzi
     * delle prese dei giocatori.
     *
     * @param preseDeiGiocatori una mappa contenente i mazzi dei giocatori
     * @return una stringa rappresentante il vincitore della partita
     */
    public static String calcolaVincitore(Map<String, Mazzo> preseDeiGiocatori) {
        List<String> vincitori = new ArrayList<>();
        int punteggioMassimo = 0;

        for (Map.Entry<String, Mazzo> entry : preseDeiGiocatori.entrySet()) {
            int punti = entry
                    .getValue()
                    .getCarteNelMazzo()
                    .stream()
                    .mapToInt(Carta::getPunti)
                    .sum();

            if (punti > punteggioMassimo) {
                punteggioMassimo = punti;
                vincitori.clear();
                vincitori.add(entry.getKey());
            } else if (punti == punteggioMassimo) {
                vincitori.add(entry.getKey());
            }
        }

        if (vincitori.size() == 1) {
            return "Il vincitore della partita è: " + vincitori.get(0) + " con " + punteggioMassimo + " punti.";
        } else {
            return "La partita è finita in pareggio tra i giocatori: " + String.join(", ", vincitori) + " con "
                    + punteggioMassimo + " punti ciascuno.";
        }
    }

}
