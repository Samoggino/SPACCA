
package com.spacca.asset.match;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.spacca.asset.utente.Amministratore;
import com.spacca.database.GiocatoreHandler;

public class CreatoreDiTorneo {

    public static Torneo strutturaTorneo(String codiceTorneo, List<String> partecipanti, Torneo torneo,
            Amministratore admin) {
        for (int i = 0; i < partecipanti.size(); i += 2) {
            if (i + 1 < partecipanti.size()) {
                List<String> listaDeiGiocatori = new ArrayList<>();
                listaDeiGiocatori.add(partecipanti.get(i));
                listaDeiGiocatori.add(partecipanti.get(i + 1));
                if (torneo.getCodiciPartite().add(
                        admin.creaPartita("tornei/" + codiceTorneo + "/T" + admin.generaNumeroCasualePartita(),
                                listaDeiGiocatori).getCodice())) {
                }
            }
        }
        for (String giocatore : partecipanti) {
            new GiocatoreHandler().carica(giocatore).addCodiceTorneo(codiceTorneo);
        }

        return torneo;
    }

    /*
     * Controlla che il numero di giocatori passato sia una potenza di 2, se non lo
     * è aggiunge giocatori finti per arrivare alla potenza di 2 successiva, in modo
     * da creare un torneo ad eliminazione in modo corretto
     * 
     * @param listaGiocatori
     * 
     * @return listaGiocatori con eventuali giocatori finti aggiunti
     */
    public static List<String> controllaNumeroGiocatori(List<String> listaGiocatori, int numeroGiocatoriScelto)
            throws IllegalArgumentException {
        Amministratore admin = new Amministratore();

        // if (listaGiocatori.size() < 2) {
        // throw new IllegalArgumentException("Non è possibile creare un torneo con meno
        // di due giocatori");
        // }

        // se listaGiocatori.size() è minore di numeroGiocatori allora vanno aggiunti
        // giocatori fino a numeroGiocatoriScelto

        while (listaGiocatori.size() < numeroGiocatoriScelto) {
            Random random = new Random();
            int numeroCasuale = random.nextInt(1000);
            String nomeCPU = "CPU-" + numeroCasuale;
            // Verifica se il nome è già presente nella lista
            if (!listaGiocatori.contains(nomeCPU)) {
                listaGiocatori.add(
                        admin.creaGiocatore(nomeCPU, numeroCasuale % 2 == 0 ? "SmartCPU" : "StupidCPU").getUsername());
            }
        }

        return listaGiocatori;
    }

}