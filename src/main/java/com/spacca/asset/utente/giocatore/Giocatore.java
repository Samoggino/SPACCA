package com.spacca.asset.utente.giocatore;

import com.google.gson.annotations.SerializedName;

public class Giocatore extends AbstractGiocatore {

    @SerializedName("Password")
    String password;

    @SerializedName("Email")
    String email;

    public Giocatore(String username, String password, String email) {
        super(username);
        this.email = email;
        this.password = password;
    }

    public void mostraLeaderboard() {
        // TODO: mostra la leaderboard della partita
    }

    public void mostraPartite() {
        System.out.println("Partite di " + this.getUsername() + ":" + this.listaCodiciPartite);
    }

    @Override
    String stampa() {
        return super.stampa() + " con password: " + this.password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
