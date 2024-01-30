package com.spacca.asset.utente.giocatore;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.spacca.database.GiocatoreHandler;

public class AbstractGiocatore extends Object {

    @SerializedName("username")
    String username;

    @SerializedName("listaCodiciPartite")
    List<String> listaCodiciPartite = new ArrayList<>();

    @SerializedName("type")
    String type;

    transient private GiocatoreHandler handlerGiocatore = new GiocatoreHandler();
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public AbstractGiocatore(String username, String type) {
        this.username = username;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    String stampa() {
        return "Giocatore: " + this.username + "\tType: " + this.type;
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
        salvaGiocatore();
    }

    public void salvaGiocatore() {
        try {
            this.handlerGiocatore.salva(this, username);
        } catch (Exception e) {
            System.err.println("Errore nel salvare la partita" + e.getMessage());
        }
    }
}