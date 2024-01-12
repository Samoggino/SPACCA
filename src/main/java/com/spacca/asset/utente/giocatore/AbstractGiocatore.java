package com.spacca.asset.utente.giocatore;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class AbstractGiocatore extends Object implements GiocatoreInterface {

    @SerializedName("username")
    String username;

    @SerializedName("listaCodiciPartite")
    List<String> listaCodiciPartite = new ArrayList<>();

    public AbstractGiocatore(String username) {
        this.username = username;
    }

    @Override
    public void scarta() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'gioca'");
    }

    @Override
    public void prendi() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'prendi'");
    }

    @Override
    public void prendiTuttoIlTavolo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'prendiTutto'");
    }

    public String getUsername() {
        return username;
    }

    String stampa() {
        return "Giocatore: " + this.username;
    }

    @Override
    public String toString() {
        return stampa();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getListaCodiciPartite() {
        return listaCodiciPartite;
    }

    public void setListaCodiciPartite(List<String> listaCodiciPartite) {
        this.listaCodiciPartite = listaCodiciPartite;
    }

    public void addCodicePartita(String codicePartita) {
        this.listaCodiciPartite.add(codicePartita);
    }

    @Override
    public void prendiTuttoIlMazzoDiUnAltroUtente(AbstractGiocatore giocatore) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'prendiTuttoIlMazzoDiUnAltroUtente'");
    }

    @Override
    public void prendiMezzoMazzoDiUnAltroUtente(AbstractGiocatore giocatore) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'prendiMezzoMazzoDiUnAltroUtente'");
    }

}
