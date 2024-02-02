package com.spacca.asset.utente.giocatore;

import com.google.gson.annotations.SerializedName;

public class Giocatore extends AbstractGiocatore {

    @SerializedName("Password")
    String password;

    @SerializedName("Email")
    String email;

    public Giocatore(String username, String password, String email) {
        super(username, "Giocatore");
        this.email = email;
        this.password = password;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}