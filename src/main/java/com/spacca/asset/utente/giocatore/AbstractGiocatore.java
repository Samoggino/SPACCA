package com.spacca.asset.utente.giocatore;

import com.google.gson.annotations.SerializedName;

public class AbstractGiocatore implements GiocatoreInterface {

    @SerializedName("nickname")
    String nickname;

    @SerializedName("listaCodiciPartite")
    String[] listaCodiciPartite = new String[0];

    public AbstractGiocatore(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void gioca() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'gioca'");
    }

    @Override
    public void pesca() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pesca'");
    }

    @Override
    public void scarta() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'scarta'");
    }

    @Override
    public void prendi() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'prendi'");
    }

    @Override
    public void prendiTutto() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'prendiTutto'");
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    String stampa() {
        return "Giocatore: " + this.nickname;
    }

    @Override
    public String toString() {
        return stampa();
    }

}
