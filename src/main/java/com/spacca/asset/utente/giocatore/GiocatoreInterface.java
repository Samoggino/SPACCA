package com.spacca.asset.utente.giocatore;

public interface GiocatoreInterface {

    void gioca();

    void pesca();

    void scarta();

    void prendi();

    void prendiTutto();

    void mostraLeaderboard();

    void setNickname(String string);

    String getNickname();
}
