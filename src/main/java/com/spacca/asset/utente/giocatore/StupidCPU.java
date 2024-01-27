package com.spacca.asset.utente.giocatore;

public class StupidCPU extends AbstractGiocatore {

    public StupidCPU(String nickname) {
        super("RS" + nickname);
    }

    @Override
    public void scarta() {
        // TODO: gioca in modo stupido
    }
}
