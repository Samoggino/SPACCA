
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

}