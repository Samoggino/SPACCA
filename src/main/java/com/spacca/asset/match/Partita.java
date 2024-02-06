package com.spacca.asset.match;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gson.annotations.SerializedName;
import com.spacca.asset.carte.Carta;
import com.spacca.asset.carte.Mazzo;
import com.spacca.asset.carte.Nome;
import com.spacca.asset.carte.Seme;
import com.spacca.database.GiocatoreHandler;
import com.spacca.database.Handler;
import com.spacca.database.PartitaHandler;

/**
 * La classe <strong>Partita</strong> rappresenta un'istanza di una partita.
 * Contiene informazioni sui giocatori coinvolti, le carte in mano ai giocatori,
 * le carte prese dai giocatori, il mazzo di gioco e il risultato della partita.
 */
public class Partita extends Object {

    // // lista dei giocatori
    @SerializedName("lista dei giocatori")
    private List<String> listaDeiGiocatori = new ArrayList<>();

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

    @SerializedName("è il turno di")
    private String giocatoreCorrente = "nessuno";

    @SerializedName("vincitore")
    private String vincitore = null;

    @SerializedName("ultimo giocatore che ha preso")
    private String ultimoGiocatoreCheHapreso = "";

    // codice della partita
    @SerializedName("codice")
    private String codice = "codice default";

    @SerializedName("classifica")
    public Map<String, Integer> classifica = new HashMap<>();

    transient private Handler handlerPartita = new PartitaHandler();

    public String getGiocatoreCorrente() {
        return this.giocatoreCorrente;
    }

    public void setGiocatoreCorrente(String giocatoreCorrente) {
        this.giocatoreCorrente = giocatoreCorrente;
        salvaPartita();
    }

    public Partita(String codice, List<String> giocatori) {
        this.codice = codice;
        this.listaDeiGiocatori = giocatori;

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
            UpdateClassifica.aggiornaClassifica(preseDeiGiocatori, classifica);
            if (this.handlerPartita == null) {
                this.handlerPartita = new PartitaHandler();
            }
            new PartitaHandler().salva(this, this.codice);
        } catch (Exception e) {
            System.err.println("Errore nel salvare la partita" + e.getMessage());
        }
    }

    public void fine() throws FileNotFoundException {
        this.handlerPartita.elimina(this.codice);
    }

    public Map<String, Integer> getClassifica() {
        aggiornaClassifica();
        return this.classifica;
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
        stampa += "\nCodice " + this.codice + "\nRisultato: " + this.classifica + "\n";

        return stampa;
    }

    @Override
    public String toString() {
        return stampa();
    }

    public List<String> getListaDeiGiocatori() {

        return this.listaDeiGiocatori;
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

        boolean enoughMazzo = abbastanzaCarteNelMazzo();
        boolean notEnoughMano = giocatoriNonHannoCarteInMano();

        if (getCarteSulTavolo().size() == 40) {
            this.vincitore = getGiocatoreCorrente();

        } else if (notEnoughMano && enoughMazzo) {
            distribuisciLeCarteAiGiocatori(enoughMazzo);
            mettiCarteSulTavolo();
        } else if (notEnoughMano && !enoughMazzo && lunghezzaMazzoDiGiocoCorrente() > 0) {
            // se non ci sono abbastanza carte nel mazzo di gioco per darle a tutti i
            // giocatori, ma ci sono ancora carte nel mazzo di gioco, distribuisci le carte
            // rimaste nel mazzo di gioco e metti le carte sul tavolo
            distribuisciLeCarteAiGiocatori(enoughMazzo);
        } else {
            // dai le carte rimaste sul tavolo all'ultimo giocatore che ha fatto una presa

            if (this.ultimoGiocatoreCheHapreso.equals("")) {
                this.ultimoGiocatoreCheHapreso = getGiocatoreCorrente();
            }

            getPreseDellUtente(this.ultimoGiocatoreCheHapreso).aggiungiListaCarteAdAltroMazzo(
                    getCarteSulTavolo().getCarteNelMazzo());

            getCarteSulTavolo().getCarteNelMazzo().clear();

        }
        salvaPartita();
    }

    void distribuisciLeCarteAiGiocatoriUnoPerUno() {
        // entro qui dentro quando non ho abbastanza carte nel mazzo di gioco per
        // distribuirne 3 ad ogni giocatore, quindi distribuisco le carte una per uno
        // ai giocatori finchè non finiscono le carte nel mazzo di gioco

        // per farlo devo ciclare la lista dei giocatori e per ogni giocatore gli do una
        // carta

        if (getListaDeiGiocatori().size() == 3 && lunghezzaMazzoDiGiocoCorrente() == 1) {
            // do la carta al giocatore corrente
            getManoDellUtente(getGiocatoreCorrente()).aggiungiCarteAlMazzo(
                    this.mazzoDiGioco.getCarteNelMazzo().remove(mazzoDiGioco.size() - 1));
            return;
        }

        int lunghezzaMazzo = lunghezzaMazzoDiGiocoCorrente();

        try {
            while (lunghezzaMazzo > 0) {
                for (String username : getManoDeiGiocatori().keySet()) {
                    if (lunghezzaMazzo <= 0) {
                        break;
                    }
                    Carta cartaDaDare = this.mazzoDiGioco
                            .getCarteNelMazzo()
                            .remove(lunghezzaMazzoDiGiocoCorrente() - 1);
                    getManoDellUtente(username).aggiungiCarteAlMazzo(cartaDaDare);
                    lunghezzaMazzo--;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.err.println("ERRORE (distribuisciLeCarteAiGiocatoriUnoPerUno):  nella dimensione del mazzo di gioco"
                    + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void distribuisciLeCarteAiGiocatori(boolean abbastanzaCarteNelMazzo) {
        if (abbastanzaCarteNelMazzo) {
            distribuisciLeCarteAiGiocatoriNormalmente();
        } else {
            distribuisciLeCarteAiGiocatoriUnoPerUno();
        }
    }

    void distribuisciLeCarteAiGiocatoriNormalmente() {
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
        int carteRimanenti = lunghezzaMazzoDiGiocoCorrente();

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

    // calcolo quando devo ridistribuire le carte
    public boolean giocatoriNonHannoCarteInMano() {

        // se il numero di carte nelle mani dei giocatori è uguale a zero
        // devi ridistribuire le carte
        int index = 0;
        for (String username : getListaDeiGiocatori()) {
            if (getManoDellUtente(username).size() == 0) {
                index++;
            }
            if (index == getListaDeiGiocatori().size()) {
                return true;
            }
        }

        return false;
    }

    boolean abbastanzaCarteNelMazzo() {

        if (getMazzoDiGioco().size() < 3 * getListaDeiGiocatori().size()) {
            return false;
        }
        return true;
    }

    public void aggiornaClassifica() {

        this.classifica = UpdateClassifica.aggiornaClassifica(getPreseDeiGiocatori(), this.classifica);
        salvaPartita();
    }

    public boolean hasWinner() {
        if (this.vincitore != null) {
        }
        return this.vincitore != null;
    }

    public String getVincitore() {

        boolean tuttiStupidi = true;
        // se tutti i giocatori sono stupidi, allora il vincitore è un giocatore a caso
        for (String giocatore : listaDeiGiocatori) {
            if (!new GiocatoreHandler().carica(giocatore).getType().equals("StupidCPU")) {
                tuttiStupidi = false;
            }
        }

        if (tuttiStupidi) {
            Random random = new Random();
            return listaDeiGiocatori.get(random.nextInt(listaDeiGiocatori.size()));
        }

        aggiornaClassifica();

        // devi prendere il primo elemento della classifica se il successivo ha un
        // punteggio minore, altrimenti devi prendere il secondo elemento della
        // classifica e così via

        int punteggioMassimo = 0;
        int numeroVincitori = 0;

        for (Map.Entry<String, Integer> entry : this.classifica.entrySet()) {
            if (entry.getValue() > punteggioMassimo) {
                punteggioMassimo = entry.getValue();
                this.vincitore = entry.getKey();
                numeroVincitori++;
            } else if (entry.getValue() == punteggioMassimo) {
                this.vincitore += " e " + entry.getKey();
                numeroVincitori++;
            }
        }

        if (getListaDeiGiocatori().size() == 2 && numeroVincitori > 1) {
            for (String giocatore : getListaDeiGiocatori()) {
                if (!giocatore.equals(this.ultimoGiocatoreCheHapreso)) {
                    this.vincitore = giocatore;
                    return this.vincitore;
                }
            }
        }

        return this.vincitore;
    }

    public boolean has2Bastoni(String username) {
        for (Carta carta : getPreseDellUtente(username).getCarteNelMazzo()) {
            if (carta.getNome().equals(Nome.DUE) && carta.getSeme().equals(Seme.BASTONI)) {
                return true;
            }
        }
        return false;
    }

    public String getUltimoGiocatoreCheHapreso() {
        return this.ultimoGiocatoreCheHapreso;
    }

    public void setUltimoGiocatoreCheHapreso(String username) {
        this.ultimoGiocatoreCheHapreso = username;
    }

}