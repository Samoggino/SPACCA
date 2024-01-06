package com.spacca.asset.utente.giocatore;

import com.google.gson.annotations.SerializedName;

public class Giocatore extends AbstractGiocatore {

    @SerializedName("password")
    String password;

    public Giocatore(String nickname, String password) {
        super(nickname);
        this.password = password;
    }

    public void mostraLeaderboard() {
        // TODO: mostra la leaderboard della partita
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void mostraPartite() {
        System.out.println("Partite di " + this.getNickname() + ":" + this.listaCodiciPartite);
    }
}
