package com.spacca.asset.match;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;
import com.spacca.asset.carte.Carta;
import com.spacca.asset.carte.Mazzo;
import com.spacca.database.PartitaHandler;

/**
 * La classe <strong>Partita</strong> rappresenta un'istanza di una partita.
 * Contiene informazioni sui giocatori coinvolti, le carte in mano ai giocatori,
 * le carte prese dai giocatori, il mazzo di gioco e il risultato della partita.
 */
public class Partita extends Object {

    // // lista dei giocatori
    @SerializedName("lista dei giocatori")
    private List<String> listaDeiGiocatori;

    // carte in mano ai giocatori
    @SerializedName("mano del giocatore")
    private Map<String, Mazzo> manoDeiGiocatori = new HashMap<>();

    // carte prese dai giocatori
    @SerializedName("prese di ogni giocatore")
    private Map<String, Mazzo> preseDeiGiocatori = new HashMap<>();

    // mazzo di gioco e piatto
    @SerializedName("mazzo di gioco")
    private Mazzo mazzoDiGioco;

    @SerializedName("carte sul tavolo")
    private Mazzo carteSulTavolo;

    // risultato della partita
    @SerializedName("risultato")
    private String risultato;

    @SerializedName("è il turno di")
    private String giocatoreCorrente = "nessuno";

    public String getGiocatoreCorrente() {
        return this.giocatoreCorrente;
    }

    public void setGiocatoreCorrente(String giocatoreCorrente) {
        this.giocatoreCorrente = giocatoreCorrente;
        salvaPartita();
    }

    // codice della partita
    @SerializedName("codice")
    private String codice = "codice default";

    private PartitaHandler handlerPartita = new PartitaHandler();

    public Partita(String codice, List<String> giocatori) {

        this.codice = codice;
        this.listaDeiGiocatori = giocatori;
        this.risultato = "Ancora da giocare";

        // Crea il mazzo di gioco
        this.mazzoDiGioco = new Mazzo().creaMazzoDiPartenza();

        // Crea il mazzo di carte sul tavolo
        this.carteSulTavolo = new Mazzo();

        // setup degli utenti
        for (String giocatore : giocatori) {
            getManoDeiGiocatori().put(giocatore, new Mazzo());
            getPreseDeiGiocatori().put(giocatore, new Mazzo());
        }

        setGiocatoreCorrente(giocatori.get(0));

        salvaPartita();

    }

    public String getCodice() {
        return this.codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public void salvaPartita() {
        try {
            calcolaRisultato();
            this.handlerPartita.salva(this, this.codice);
        } catch (Exception e) {
            System.err.println("Errore nel salvare la partita" + e.getMessage());
        }
    }

    public void eliminaPartita() {
        this.handlerPartita.elimina(this.codice);
    }

    public String getRisultato() {
        return this.risultato;
    }

    public Map<String, Mazzo> getManoDeiGiocatori() {
        return this.manoDeiGiocatori;
    }

    private String stampa() {
        String stampa = "\nPartita: \n";

        int i = 0;
        for (String giocatore : getListaDeiGiocatori()) {
            if (i < getListaDeiGiocatori().size() && i > 0) {
                stampa += "\tvs\t";
            }
            stampa += giocatore;
            i++;
        }
        stampa += "\nCodice " + this.codice + "\nRisultato: " + getRisultato() + "\n";

        return stampa;
    }

    @Override
    public String toString() {
        return stampa();
    }

    public List<String> getListaDeiGiocatori() {

        return this.listaDeiGiocatori;
    }

    public void calcolaRisultato() {
        this.risultato = CalcolatoreRisultatoPartita.calcolaRisultato(getPreseDeiGiocatori());
    }

    public Mazzo getManoDellUtente(String username) {
        return this.manoDeiGiocatori.get(username);
    }

    public Mazzo getPreseDellUtente(String username) {
        return this.preseDeiGiocatori.get(username);
    }

    public Map<String, Mazzo> getPreseDeiGiocatori() {
        return this.preseDeiGiocatori;
    }

    public Mazzo getMazzoDiGioco() {
        return this.mazzoDiGioco;
    }

    public Mazzo getCarteSulTavolo() {
        return this.carteSulTavolo;
    }

    /**
     * questo metodo distribuisce le carte ai giocatori e mette le carte sul tavolo
     */
    public void nuovoTurno() {
        distribuisciLeCarteAiGiocatori();
        mettiCarteSulTavolo();
        System.out.println("Carte distribuite con successo!");
        salvaPartita();
    }

    private void distribuisciLeCarteAiGiocatori() {
        // distribuisce le carte ai giocatori
        for (String username : getManoDeiGiocatori().keySet()) {
            Mazzo mazzoGiocatore = getManoDellUtente(username);
            List<Carta> ultimeTreCarte = this.mazzoDiGioco
                    .getCarteNelMazzo()
                    .subList(
                            lunghezzaMazzoDiGiocoCorrente() - 3,
                            lunghezzaMazzoDiGiocoCorrente());

            mazzoGiocatore.aggiungiListaCarteAdAltroMazzo(ultimeTreCarte);

            // è necessario fare clear() per rimuovere le carte dal mazzo di gioco,
            // altrimenti continuerebbe a dare le stesse carte a tutti gli utenti
            ultimeTreCarte.clear();
        }
    }

    private int lunghezzaMazzoDiGiocoCorrente() {
        return this.mazzoDiGioco.size();
    }

    private void mettiCarteSulTavolo() {
        // Supponiamo che questo sia il numero di carte che desideri distribuire in un
        // turno normale.
        int carteDaDistribuire = 4;

        // Ottieni il numero di carte nel mazzo di gioco
        int carteRimanenti = lunghezzaMazzoDiGiocoCorrente() - 1;

        // Determina il numero di carte da distribuire in questo turno
        int carteDaDistribuireQuestoTurno = Math.min(carteDaDistribuire, carteRimanenti);

        // Distribuisci le carte sul tavolo
        for (int i = 0; i < carteDaDistribuireQuestoTurno; i++) {
            Carta cartaDaDare = this.mazzoDiGioco
                    .getCarteNelMazzo()
                    .remove(lunghezzaMazzoDiGiocoCorrente() - 1);
            getCarteSulTavolo().aggiungiCarteAlMazzo(cartaDaDare);
        }
    }

    public void rubaUnMazzo(String ladro, String scammato) {

        if (true) {
            // TODO: controllo se l'utente ha una carta che è lo stesso numero della carta
            // in cima al mazzo dell'altro utente
        }

        try {
            Mazzo mazzoLadro = getPreseDellUtente(ladro);
            Mazzo mazzoScammato = getPreseDellUtente(scammato);

            if (mazzoScammato.size() > 0) {
                mazzoLadro.aggiungiListaCarteAdAltroMazzo(mazzoScammato.getCarteNelMazzo());
                mazzoScammato.getCarteNelMazzo().clear();
                salvaPartita();
            } else {
                System.out.println("L'utente non ha carte da rubare!");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rubaMezzoMazzo(String ladro, String scammato) {

        if (true) {
            // TODO: controllo se l'utente ha una carta che è lo stesso numero della carta
            // in cima al mazzo dell'altro utente
        }

        // Aggiungi il controllo solo se il mazzo dello scammato ha un numero dispari di
        // carte
        boolean arrotondaPerDifetto = getPreseDellUtente(scammato).size() % 2 != 0;

        try {
            Mazzo mazzoLadro = getPreseDellUtente(ladro);
            Mazzo mazzoScammato = getPreseDellUtente(scammato);

            if (mazzoScammato.size() > 0) {
                int metaMazzo = mazzoScammato.size() / 2;

                // Aggiungi l'arrotondamento per difetto se necessario
                if (arrotondaPerDifetto) {
                    metaMazzo = (int) Math.floor(metaMazzo);
                }

                mazzoLadro.aggiungiListaCarteAdAltroMazzo(
                        mazzoScammato
                                .getCarteNelMazzo()
                                .subList(0, metaMazzo));
                mazzoScammato
                        .getCarteNelMazzo()
                        .subList(0, metaMazzo)
                        .clear();

                salvaPartita();
            } else {
                System.out.println("L'utente non ha carte da rubare!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String stampaManoDeiGiocatori() {
        String stampa = "\n";
        int i = 0;
        for (String username : getManoDeiGiocatori().keySet()) {
            stampa += "Mano di " + username + ": " + getManoDellUtente(username);
            if (i < getListaDeiGiocatori().size()) {
                // mette a capo tra un giocatore e l'altro
                stampa += "\n";
            }
            i++;
        }
        return stampa;
    }

    /**
     * Questo metodo sarà probabilmente utilizzato solo dagli utenti CPU e andrà
     * modificato.
     * 
     * @param cartaSulTavolo
     * @param username
     */
    public void prendiCartaConCartaDellaMano(String username, Carta cartaSulTavolo, Carta cartaDellaMano) {
        System.out.println("Carta selezionata: " + cartaDellaMano.getNome() + " di " + cartaDellaMano.getSeme());
        System.out.println("Carta sul tavolo: " + cartaSulTavolo.getNome() + " di " + cartaSulTavolo.getSeme());
        try {
            Iterator<Carta> iterator = getCarteSulTavolo().getCarteNelMazzo().iterator();

            while (iterator.hasNext()) {
                Carta carta = iterator.next();
                if (carta.getNome().equals(cartaSulTavolo.getNome())) {
                    // Rimuovi la carta dal tavolo e mettila nella mano del giocatore
                    iterator.remove();
                    getPreseDellUtente(username).aggiungiCarteAlMazzo(cartaSulTavolo, cartaDellaMano);
                    // rimuoivi la carta dalla mano del giocatore
                    getManoDellUtente(username).rimuoviCartaDalMazzo(cartaDellaMano);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Sul tavolo non ci sono carte!" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore nella ricerca della carta sul tavolo" + e.getMessage());
        } finally {
            salvaPartita();
        }
    }

    public Carta getCartaInCima(String username) {

        if (getPreseDellUtente(username).size() == 0) {
            return null;
        }
        return getPreseDellUtente(username).getCarteNelMazzo().get(getPreseDellUtente(username).size() - 1);
    }

    // viene riordinata la lista dei giocatori in base al giocatore corrente che
    // viene messo per ultimo
    public void passaTurno() {
        List<String> localListGiocatori = getListaDeiGiocatori();

        // Verifica che ci siano almeno due giocatori
        if (localListGiocatori.size() >= 2) {
            Collections.rotate(localListGiocatori, -1);
            setGiocatoreCorrente(localListGiocatori.get(0));
            setListaDeiGiocatori(localListGiocatori);
            salvaPartita();
        }
    }

    private void setListaDeiGiocatori(List<String> localListGiocatori) {
        this.listaDeiGiocatori = localListGiocatori;
    }

    public void scarta(String username, Carta cartaScartata) {

        getManoDellUtente(username).rimuoviCartaDalMazzo(cartaScartata);
        getCarteSulTavolo().aggiungiCarteAlMazzo(cartaScartata);
        salvaPartita();

    }
}