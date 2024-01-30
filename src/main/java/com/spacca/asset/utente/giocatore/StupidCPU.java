package com.spacca.asset.utente.giocatore;

import com.spacca.asset.match.Partita;

public class StupidCPU extends AbstractGiocatore {

    public StupidCPU(String nickname) {
        super(nickname, "StupidCPU");
    }

    public void gioca(Partita partita) {
        // la CPU stupida scarta sempre la prima carta
        if (partita.getManoDellUtente(this.username).getCarteNelMazzo().size() > 0) {
            scarta(partita, partita.getManoDellUtente(this.username).getCarteNelMazzo().get(0));
        }
    }
}
