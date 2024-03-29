package com.spacca.asset.utente.giocatore;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.spacca.asset.carte.Carta;
import com.spacca.asset.match.Partita;
import com.spacca.database.GiocatoreHandler;

public class AbstractGiocatore extends Object {

    @SerializedName("username")
    String username;

    @SerializedName("listaCodiciPartite")
    List<String> listaCodiciPartite = new ArrayList<>();

    @SerializedName("listaCodiciTornei")
    List<String> listaCodiciTornei = new ArrayList<>();

    @SerializedName("type")
    String type;

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

    public AbstractGiocatore(String username) {
        this.username = username;
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

    public List<String> getListaCodiciTornei() {
        return this.listaCodiciTornei;
    }

    public List<String> getListaCodiciPartite() {
        return this.listaCodiciPartite;
    }

    public void addCodiceTorneo(String codiceTorneo) {
        this.listaCodiciTornei.add(codiceTorneo);
        salvaGiocatore();
    }

    public void removeCodiceTorneo(String codiceTorneo) {
        this.listaCodiciTornei.remove(codiceTorneo);
        salvaGiocatore();
    }

    public void addCodicePartita(String codicePartita) {
        this.listaCodiciPartite.add(codicePartita);
        salvaGiocatore();
    }

    public void removeCodicePartita(String codicePartita) {
        this.listaCodiciPartite.remove(codicePartita);
        salvaGiocatore();
    }

    public void salvaGiocatore() {
        try {
            new GiocatoreHandler().salva(this, username);
        } catch (Exception e) {
            System.err.println("Errore nell'utente " + e.getMessage());
        }
    }

    public boolean prendi(Partita partita, Carta cartaSulTavolo, Carta cartaDellaMano) {

        try {

            // la carta sul tavolo dev'essere sul tavolo
            if (!partita.getCarteSulTavolo().getCarteNelMazzo().contains(cartaSulTavolo)) {
                return false;
            }

            if (cartaSulTavolo.getNome() == cartaDellaMano.getNome()) {
                partita.getManoDellUtente(this.username).rimuoviCartaDalMazzo(cartaDellaMano);
                partita.getCarteSulTavolo().rimuoviCartaDalMazzo(cartaSulTavolo);
                partita.getPreseDellUtente(this.username).aggiungiCarteAlMazzo(cartaDellaMano, cartaSulTavolo);
                partita.setUltimoGiocatoreCheHapreso(this.username);
                return true;
            }

        } catch (IndexOutOfBoundsException e) {
            System.err.println("Sul tavolo non ci sono carte!" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore nella ricerca della carta sul tavolo" + e.getMessage());
        }
        return false;
    }

    public void rubaUnMazzo(Partita partita, String scammato, Carta cartaCheRuba) {

        try {

            if (partita.getPreseDellUtente(scammato).size() > 0) {

                partita.getPreseDellUtente(this.username)
                        .aggiungiListaCarteAdAltroMazzo(
                                partita.getPreseDellUtente(scammato).getCarteNelMazzo());

                partita.getPreseDellUtente(scammato).getCarteNelMazzo().clear();

                partita.getManoDellUtente(this.username).rimuoviCartaDalMazzo(cartaCheRuba);
                partita.getPreseDellUtente(this.username).aggiungiCarteAlMazzo(cartaCheRuba);

                partita.salvaPartita();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rubaMezzoMazzo(Partita partita, String scammato, Carta cartaCheRuba) {

        // Aggiungi il controllo solo se il mazzo dello scammato ha un numero dispari di
        // carte
        boolean arrotondaPerDifetto = partita.getPreseDellUtente(scammato).size() % 2 != 0;

        try {

            if (partita.getPreseDellUtente(scammato).size() > 0) {
                int metaMazzo = partita.getPreseDellUtente(scammato).size() / 2;

                // Aggiungi l'arrotondamento per difetto se necessario
                if (arrotondaPerDifetto) {
                    metaMazzo = (int) Math.floor(metaMazzo);
                }

                partita.getPreseDellUtente(this.username)
                        .aggiungiListaCarteAdAltroMazzo(
                                partita.getPreseDellUtente(scammato)
                                        .getCarteNelMazzo()
                                        .subList(0, metaMazzo));

                partita.getPreseDellUtente(scammato)
                        .getCarteNelMazzo()
                        .subList(0, metaMazzo)
                        .clear();

                partita.getManoDellUtente(this.username).rimuoviCartaDalMazzo(cartaCheRuba);
                partita.getPreseDellUtente(this.username).aggiungiCarteAlMazzo(cartaCheRuba);
                partita.salvaPartita();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void scarta(Partita partita, Carta cartaScartata) {

        partita.getManoDellUtente(this.username).rimuoviCartaDalMazzo(cartaScartata);
        partita.getCarteSulTavolo().aggiungiCarteAlMazzo(cartaScartata);
        partita.salvaPartita();

    }

    public void assoPrendeTutto(Partita partita, Carta carta) {
        // l'asso prende tutto quello che c'è sul tavolo

        partita.getManoDellUtente(this.username).rimuoviCartaDalMazzo(carta);
        partita.getPreseDellUtente(this.username)
                .aggiungiListaCarteAdAltroMazzo(partita.getCarteSulTavolo().getCarteNelMazzo());
        partita.getPreseDellUtente(this.username).aggiungiCarteAlMazzo(carta);
        // sposta tutte le carte del tavolo nelle prese dell'utente

        partita.getCarteSulTavolo().getCarteNelMazzo().clear();
        partita.setUltimoGiocatoreCheHapreso(this.username);
        partita.salvaPartita();
    }

    /**
     * Se il giocatore è una SmartCPU o una StupidCPU ritorna true, altrimenti
     * false.
     * 
     * @return
     */
    public boolean isCPU() {

        if (this.type.equals("SmartCPU") || this.type.equals("StupidCPU")) {
            return true;
        }

        return false;
    }

    public void setListaCodiciPartite(List<String> listaCodici) {
        this.listaCodiciPartite = listaCodici;
    }

    public void setListaCodiciTornei(List<String> listaCodiciTornei) {
        this.listaCodiciTornei = listaCodiciTornei;
    }
}