package com.spacca.asset.utente.giocatore;

public interface GiocatoreInterface {

    void scarta();

    void prendi();

    void prendiTuttoIlTavolo();

    void prendiTuttoIlMazzoDiUnAltroUtente(AbstractGiocatore giocatore);

    void prendiMezzoMazzoDiUnAltroUtente(AbstractGiocatore giocatore);
}
