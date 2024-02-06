
package com.spacca.asset.match;

import java.util.ArrayList;
import java.util.List;

import com.spacca.asset.utente.Amministratore;

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
        return torneo;
    }

    /**
     * Controlla che il numero di giocatori passato sia una potenza di 2, se non lo
     * è aggiunge giocatori finti per arrivare alla potenza di 2 successiva, in modo
     * da creare un torneo ad eliminazione in modo corretto
     * 
     * @param listaGiocatori
     * @return listaGiocatori con eventuali giocatori finti aggiunti
     */
    public static List<String> controllaNumeroGiocatori(List<String> listaGiocatori) {
        Amministratore admin = new Amministratore();
        if (listaGiocatori.size() < 2) {
            throw new IllegalArgumentException("Non è possibile creare un torneo con meno di due giocatori");
        }

        if (((listaGiocatori.size() & (listaGiocatori.size() - 1)) == 0) == false) {
            // se il numero di listaGiocatori non è una potenza di 2 allora non è
            // possibile
            /// creare un torneo (molto carino questo controllo, non lo conoscevo)
            // se non è una potenza di due, aggiungi giocatori finti per arrivare alla
            // potenza di due successiva
            int potenzaDiDue = 1;
            while (potenzaDiDue < listaGiocatori.size()) {
                potenzaDiDue *= 2;
            }
            int giocatoriFinti = potenzaDiDue - listaGiocatori.size();
            for (int i = 0; i < giocatoriFinti; i++) {
                listaGiocatori
                        .add(admin.creaGiocatore("CPU" + i, i % 2 == 0 ? "SmartCPU" : "StupidCPU").getUsername());
            }
        }
        return listaGiocatori;
    }

}