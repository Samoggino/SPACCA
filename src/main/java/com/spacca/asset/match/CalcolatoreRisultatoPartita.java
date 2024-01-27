package com.spacca.asset.match;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
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
public class CalcolatoreRisultatoPartita {

    static Map<String, Integer> puntiPerGiocatore = new HashMap<String, Integer>();

    /**
     * Calcola il vincitore della partita basandosi sulla mappa fornita dei mazzi
     * delle prese dei giocatori.
     *
     * @param preseDeiGiocatori una mappa contenente i mazzi dei giocatori
     * @return una stringa rappresentante il vincitore della partita
     */
    public static Map<String, Integer> calcolaVincitore(Map<String, Mazzo> preseDeiGiocatori,
            Map<String, Integer> classifica) {

        // prendi tutti i giocatori dalla mappa delle prese e mettili nella mappa dei
        // punti per inizializzarla

        for (String giocatore : preseDeiGiocatori.keySet()) {
            puntiPerGiocatore.put(giocatore, 0);
        }

        puntiPerGiocatore = classifica;

        // per ogni giocatore nella mappa dei punti, calcola i punti e mettili nella
        // mappa dei punti

        calcolaCarte(preseDeiGiocatori); // ora funziona
        calcolaDueDiBastoni(preseDeiGiocatori); // funziona
        calcolaPunti(preseDeiGiocatori);// funziona

        System.out.println("puntiPerGiocatore" + puntiPerGiocatore);

        return ordinaClassifica();

    }

    /**
     * Deve cercare chi ha preso il due di bastoni e assegnare 1 punto
     */
    private static void calcolaDueDiBastoni(Map<String, Mazzo> preseDeiGiocatori) {

        for (Map.Entry<String, Mazzo> entry : preseDeiGiocatori.entrySet()) {
            for (Carta carta : entry.getValue().getCarteNelMazzo()) {
                if (carta.getValore() == 2 && carta.getSeme().equals(Seme.BASTONI)) {
                    puntiPerGiocatore.put(entry.getKey(), puntiPerGiocatore.get(entry.getKey()) + 1);
                    System.out.println(entry.getKey() + " ha preso il due di bastoni");
                }
            }
        }

    }

    /**
     * Deve cercare chi ha più carte e assegnare 1 punto
     */
    private static void calcolaCarte(Map<String, Mazzo> preseDeiGiocatori) {
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
            System.out.println(entry.getKey() + " ha " + conteggioCarte + " carte");
        }

        // Resetta i punti per tutti i giocatori
        for (String giocatore : puntiPerGiocatore.keySet()) {
            puntiPerGiocatore.put(giocatore, 0);
        }

        // Assegna punti solo ai vincitori
        for (String vincitore : vincitori) {
            puntiPerGiocatore.put(vincitore, puntiPerGiocatore.get(vincitore) + 1);
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
    private static void calcolaPunti(Map<String, Mazzo> preseDeiGiocatori) {
        List<String> vincitori = new ArrayList<>();
        int punteggioMassimo = 1;

        System.out.println("calcolaPunti");
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
            System.out.println(entry.getKey() + " " + punti);
        }

        for (String vincitore : vincitori) {
            puntiPerGiocatore.put(vincitore, puntiPerGiocatore.get(vincitore) + 1);
        }

    }

    static Map<String, Integer> ordinaClassifica() {

        // prima
        System.out.println("prima" + puntiPerGiocatore);

        // ordina la mappa dei punti
        Map<String, Integer> mappaOrdinata = puntiPerGiocatore
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        System.out.println(mappaOrdinata);

        // dopo
        System.out.println("dopo" + mappaOrdinata);

        return mappaOrdinata;
    }

}
