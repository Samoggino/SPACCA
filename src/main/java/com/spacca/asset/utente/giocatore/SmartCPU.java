package com.spacca.asset.utente.giocatore;

import com.spacca.asset.carte.Carta;
import com.spacca.asset.carte.Nome;
import com.spacca.asset.match.Partita;

public class SmartCPU extends AbstractGiocatore {

    public SmartCPU(String username) {
        super(username, "SmartCPU");
    }

    public void gioca(Partita partita) {

        System.out.println("SmartCPU: " + this.username);

        boolean cartaPresa = false;
        Carta dellaMano = null;
        Carta delTavolo = null;

        // la CPU intelligente scarta solo se non può prendere
        if (partita.getCarteSulTavolo().size() == 0) {
            cartaPresa = false;
        } else {
            for (Carta cartaSulTavolo : partita.getCarteSulTavolo().getCarteNelMazzo()) {
                for (Carta cartaDellaMano : partita.getManoDellUtente(this.username)
                        .getCarteNelMazzo()) {

                    if (cartaDellaMano.getNome().equals(Nome.ASSO)) {
                        assoPrendeTutto(partita, cartaDellaMano);
                        return;
                    }

                    if (cartaDellaMano.getNome().equals(cartaSulTavolo.getNome())) {
                        cartaPresa = true;
                        dellaMano = cartaDellaMano;
                        delTavolo = cartaSulTavolo;
                    }
                }
            }
        }

        if (cartaPresa) {
            System.out.println(partita.getGiocatoreCorrente() + " prende " + delTavolo + " con " + dellaMano);
            prendi(partita, delTavolo, dellaMano);

        } else {
            if (partita.getManoDellUtente(getUsername()).getCarteNelMazzo().size() > 0) {
                scarta(partita, partita.getManoDellUtente(getUsername()).getCarteNelMazzo().get(0));
            }
        }
    }
}
