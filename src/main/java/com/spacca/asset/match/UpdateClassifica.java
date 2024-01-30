package com.spacca.asset.match;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.spacca.asset.carte.Carta;
import com.spacca.asset.carte.Mazzo;
import com.spacca.asset.carte.Seme;

/**
 * CalcolatoreRisultatoPartita è una classe che si occupa di calcolare il
 * risultato di una partita di SPACCA.
 * 
 * Il risultato di una partita è dato da tre fattori:
 * - il numero di carte prese da ciascun giocatore
 * - il due di bastoni
 * - i punti delle carte presenti nei mazzi dei giocatori
 */
public class UpdateClassifica {

    /**
     * Calcola il vincitore della partita basandosi sulla mappa fornita dei mazzi
     * delle prese dei giocatori.
     *
     * @param preseDeiGiocatori una mappa contenente i mazzi dei giocatori
     * @return una stringa rappresentante il vincitore della partita
     */
    public static Map<String, Integer> aggiornaClassifica(Map<String, Mazzo> preseDeiGiocatori,
            Map<String, Integer> classifica) {

        // prendi tutti i giocatori dalla mappa delle prese e mettili nella mappa dei
        // punti per inizializzarla

        for (String giocatore : preseDeiGiocatori.keySet()) {
            classifica.put(giocatore, 0);
        }

        calcolaCarte(preseDeiGiocatori, classifica); // ora funziona
        calcolaDueDiBastoni(preseDeiGiocatori, classifica); // funziona
        calcolaPunti(preseDeiGiocatori, classifica);// funziona

        return ordinaClassifica(classifica);

    }

    /**
     * Deve cercare chi ha preso il due di bastoni e assegnare 1 punto
     */
    private static void calcolaDueDiBastoni(Map<String, Mazzo> preseDeiGiocatori, Map<String, Integer> classifica) {

        for (Map.Entry<String, Mazzo> entry : preseDeiGiocatori.entrySet()) {
            for (Carta carta : entry.getValue().getCarteNelMazzo()) {
                if (carta.getValore() == 2 && carta.getSeme().equals(Seme.BASTONI)) {
                    classifica.put(entry.getKey(), classifica.get(entry.getKey()) + 1);
                }
            }
        }
    }

    /**
     * Deve cercare chi ha più carte e assegnare 1 punto
     */
    private static void calcolaCarte(Map<String, Mazzo> preseDeiGiocatori, Map<String, Integer> classifica) {
        List<String> vincitori = new ArrayList<>();
        int carteMassime = 1;

        for (Map.Entry<String, Mazzo> entry : preseDeiGiocatori.entrySet()) {
            int conteggioCarte = entry.getValue().getCarteNelMazzo().size();

            if (conteggioCarte > carteMassime) {
                carteMassime = conteggioCarte;
                // Rimuovi i precedenti vincitori altrimenti mantengono il loro punto guadagnato
                // prima ma che ora non spetta più a loro
                vincitori.clear();
                vincitori.add(entry.getKey());
            } else if (conteggioCarte == carteMassime) {
                vincitori.add(entry.getKey());
            }
        }

        // Resetta i punti per tutti i giocatori
        for (String giocatore : classifica.keySet()) {
            classifica.put(giocatore, 0);
        }

        // Assegna punti solo ai vincitori
        for (String vincitore : vincitori) {
            classifica.put(vincitore, classifica.get(vincitore) + 1);
        }
    }

    /**
     * Calcola il risultato di una partita basandosi sulla mappa fornita dei mazzi
     * delle prese dei giocatori.
     *
     * @param preseDeiGiocatori una mappa contenente i mazzi dei giocatori
     * @return una rappresentazione testuale del risultato, mostrando i punti di
     *         ciascun giocatore
     */
    private static void calcolaPunti(Map<String, Mazzo> preseDeiGiocatori, Map<String, Integer> classifica) {
        List<String> vincitori = new ArrayList<>();
        int punteggioMassimo = 1;

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

        for (String vincitore : vincitori) {
            classifica.put(vincitore, classifica.get(vincitore) + 1);
        }

    }

    static Map<String, Integer> ordinaClassifica(Map<String, Integer> classifica) {

        // ordina la mappa dei punti
        Map<String, Integer> mappaOrdinata = classifica
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (user, punti) -> user,
                        LinkedHashMap::new));

        return mappaOrdinata;
    }

}
