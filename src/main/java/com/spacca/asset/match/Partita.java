package com.spacca.asset.match;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;
import com.spacca.asset.carte.Carta;
import com.spacca.asset.carte.Mazzo;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.database.FileHandler;

/**
 * La classe <strong>Partita</strong> rappresenta un'istanza di una partita.
 * Contiene informazioni sui giocatori coinvolti, le carte in mano ai giocatori,
 * le carte prese dai giocatori, il mazzo di gioco e il risultato della partita.
 */
public class Partita {

    // lista dei giocatori
    @SerializedName("lista dei giocatori")
    private List<String> listaDeiGiocatori;

    // carte in mano ai giocatori
    @SerializedName("mano del giocatore")
    private Map<String, Mazzo> manoDeiGiocatori;

    // carte prese dai giocatori
    @SerializedName("prese di ogni giocatore")
    private Map<String, Mazzo> preseDeiGiocatori;

    // mazzo di gioco e piatto
    @SerializedName("mazzo di gioco")
    private Mazzo mazzoDiGioco;

    @SerializedName("carte sul tavolo")
    private Mazzo carteSulTavolo;

    // risultato della partita
    @SerializedName("risultato")
    private String risultato;

    // codice della partita
    @SerializedName("codice")
    private String codice = "codice default";

    private FileHandler fileHandler = new FileHandler();

    public Partita(String codice, List<String> giocatori) {

        this.codice = codice;
        this.listaDeiGiocatori = giocatori;
        this.risultato = "Ancora da giocare";

        creaPartita(giocatori);
    }

    private void creaPartita(List<String> giocatori) {
        // Crea il mazzo di gioco
        setMazzoDiGioco(new Mazzo().creaMazzoDiPartenza());

        // Crea il mazzo di carte sul tavolo
        setCarteSulTavolo(new Mazzo());

        // Crea la mano dei giocatori
        setManoDeiGiocatori(new HashMap<>());

        // Crea le carte prese di ogni giocatore
        setPreseDeiGiocatori(new HashMap<>());

        // setup degli utenti
        for (String giocatore : giocatori) {
            this.manoDeiGiocatori.put(giocatore, new Mazzo());
            this.preseDeiGiocatori.put(giocatore, new Mazzo());
        }

        this.risultato = getRisultato();
        salvaPartita();
    }

    public Partita(String codice, List<String> giocatori, String risultato) {
        this.codice = codice;
        this.listaDeiGiocatori = giocatori;
        this.risultato = risultato;
    }

    public Partita(String codice) {
        this.codice = codice;
        caricaPartita(codice);
    }

    public String getCodice() {
        return this.codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public void salvaPartita() {
        this.fileHandler.salvaPartita(this, this.codice);
    }

    public void caricaPartita(String codicePartita) {
        this.fileHandler.caricaPartita(codicePartita);
    }

    public void eliminaPartita() {
        this.fileHandler.eliminaPartita(this.codice);
    }

    public String getRisultato() {
        StringBuilder risultatoBuilder = new StringBuilder();

        this.preseDeiGiocatori.forEach((username, mazzo) -> {
            int punti = mazzo
                    .getCarteNelMazzo()
                    .stream()
                    .mapToInt(Carta::getPunti)
                    .sum();

            risultatoBuilder.append(String.format("%s: %d\n", username, punti));
        });

        salvaPartita();
        return risultatoBuilder.toString();
    }

    public Map<String, Mazzo> getManoDeiGiocatori() {
        return this.manoDeiGiocatori;
    }

    public String stampa() {
        String stampa = "\nPartita: \n";

        int i = 0;
        for (String giocatore : this.listaDeiGiocatori) {
            if (i == 1) {
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

    public void setListaDeiGiocatori(List<String> giocatori) {
        this.listaDeiGiocatori = giocatori;
        salvaPartita();
    }

    public void setRisultato(String risultato) {
        this.risultato = risultato;
        salvaPartita();
    }

    public Mazzo getMano(String username) {
        return this.manoDeiGiocatori.get(username);
    }

    public void setManoDeiGiocatori(Map<String, Mazzo> manoDelGiocatore) {
        this.manoDeiGiocatori = manoDelGiocatore;
        salvaPartita();
    }

    public Map<String, Mazzo> getPreseDeiGiocatori() {
        return this.preseDeiGiocatori;
    }

    public void setPreseDeiGiocatori(Map<String, Mazzo> cartePreseDiOgniGiocatore) {
        this.preseDeiGiocatori = cartePreseDiOgniGiocatore;
        salvaPartita();
    }

    public Mazzo getMazzoDiGioco() {
        return this.mazzoDiGioco;
    }

    public void setMazzoDiGioco(Mazzo mazzoDiGioco) {
        this.mazzoDiGioco = mazzoDiGioco;
        salvaPartita();
    }

    public Mazzo getCarteSulTavolo() {
        return this.carteSulTavolo;
    }

    public void setCarteSulTavolo(Mazzo carteSulTavolo) {
        this.carteSulTavolo = carteSulTavolo;
        salvaPartita();
    }

    /**
     * Il metodo <strong>pesca</strong> permette è l'azione con cui vengono
     * distribuite le 3 carte a tutti i giocatori.
     * 
     * Fisicamente vengono prese 3 carte per ogni giocatore dal mazzo di gioco e
     * vengono messe nella mano del giocatore.
     */
    public void nuovoTurno(int distribuzione) {

        try {
            if (distribuzione == 1) {
                distribuisciLeCarteAiGiocatoriUnoPerUno();
            } else if (distribuzione == 3) {
                distribuisciLeCarteAiGiocatoriDi3In3();
            }
            mettiCarteSulTavolo();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Carte distribuite con successo!");
        salvaPartita();
    }

    private void distribuisciLeCarteAiGiocatoriUnoPerUno() {
        int numeroCartePerGiocatore = 3;
        // distribuisce le carte ai giocatori
        for (int i = 0; i < numeroCartePerGiocatore; i++) {
            for (String username : this.manoDeiGiocatori.keySet()) {
                Mazzo mazzoGiocatore = this.manoDeiGiocatori.get(username);
                if (mazzoGiocatore != null && this.mazzoDiGioco != null
                        && this.mazzoDiGioco.size() > 0) {

                    Carta cartaDaDare = this.mazzoDiGioco
                            .getCarteNelMazzo()
                            .remove(lunghezzaMazzoDiGiocoCorrente() - 1);
                    // meno uno tolto da qua sopra
                    mazzoGiocatore.aggiungiCartaAlMazzo(cartaDaDare);
                    this.manoDeiGiocatori.put(username, mazzoGiocatore);
                } else {
                    System.out.println(
                            "Errore: Il mazzo di gioco è vuoto o non è stato inizializzato correttamente.");
                }
            }
        }
    }

    private int lunghezzaMazzoDiGiocoCorrente() {
        return this.mazzoDiGioco.size();
    }

    private void distribuisciLeCarteAiGiocatoriDi3In3() {
        // distribuisce le carte ai giocatori
        for (String username : this.manoDeiGiocatori.keySet()) {
            Mazzo mazzoGiocatore = this.manoDeiGiocatori.get(username);
            if (mazzoGiocatore != null && this.mazzoDiGioco.size() > 0) {

                // Verifica che ci siano almeno 3 carte nel mazzo di gioco prima di procedere
                if (lunghezzaMazzoDiGiocoCorrente() >= 3) {
                    List<Carta> ultimeTreCarte = this.mazzoDiGioco
                            .getCarteNelMazzo()
                            .subList(
                                    lunghezzaMazzoDiGiocoCorrente() - 3,
                                    lunghezzaMazzoDiGiocoCorrente());

                    mazzoGiocatore.aggiungiListaCarteAdAltroMazzo(ultimeTreCarte);

                    // Rimuovi le ultime tre carte dal mazzo di gioco
                    ultimeTreCarte.clear();
                } else {
                    // Gestisci il caso in cui ci siano meno di 3 carte nel mazzo di gioco
                    System.out.println("Non ci sono abbastanza carte nel mazzo di gioco per distribuire al giocatore.");
                }

            } else {
                System.out.println(
                        "Errore: Il mazzo di gioco è vuoto o non è stato inizializzato correttamente.");
            }
        }
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
            this.carteSulTavolo.aggiungiCartaAlMazzo(cartaDaDare);
        }
    }

    public void aggiungiCartaAlMazzoDellUtente(String username, Carta carta) {

        this.manoDeiGiocatori.put(username, carteSulTavolo);
        salvaPartita();
        try {
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void giocaUnaCarta(AbstractGiocatore giocatore1, int posizioneCartaDaGiocare) {

        String username = giocatore1.getUsername();
        try {
            Carta cartaGiocata;
            cartaGiocata = this.manoDeiGiocatori
                    .get(username)
                    .rimuoviCartaDalMazzo(posizioneCartaDaGiocare);
            this.carteSulTavolo.aggiungiCartaAlMazzo(cartaGiocata);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(
                    "Non ci sono più carte in mano al giocatore oppure il giocatore ha al massimo 3 carte in mano.");
        }
    }

    public void rubaUnMazzo(AbstractGiocatore ladro, AbstractGiocatore scammato) {

        String usernameLadro = ladro.getUsername();
        String usernameScammato = scammato.getUsername();

        if (true) {
            // TODO: controllo se l'utente ha una carta che è lo stesso numero della carta
            // in cima al mazzo dell'altro utente
        }

        try {
            Mazzo mazzoLadro = this.preseDeiGiocatori.get(usernameLadro);
            Mazzo mazzoScammato = this.preseDeiGiocatori.get(usernameScammato);

            if (mazzoScammato.size() > 0) {
                mazzoLadro.aggiungiListaCarteAdAltroMazzo(mazzoScammato.getCarteNelMazzo());
                mazzoScammato.getCarteNelMazzo().clear();
            } else {
                System.out.println(
                        "Errore: Il giocatore non ha carte da rubare o non è stato inizializzato correttamente.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rubaMezzoMazzo(AbstractGiocatore ladro, AbstractGiocatore scammato) {

        String usernameLadro = ladro.getUsername();
        String usernameScammato = scammato.getUsername();

        if (true) {
            // TODO: controllo se l'utente ha una carta che è lo stesso numero della carta
            // in cima al mazzo dell'altro utente
        }

        try {
            Mazzo mazzoLadro = this.preseDeiGiocatori.get(usernameLadro);
            Mazzo mazzoScammato = this.preseDeiGiocatori.get(usernameScammato);

            if (mazzoLadro.size() * mazzoScammato.size() > 0) {
                int metaMazzo = mazzoScammato.size() / 2;
                mazzoLadro.aggiungiListaCarteAdAltroMazzo(
                        mazzoScammato
                                .getCarteNelMazzo()
                                .subList(0, metaMazzo));
                mazzoScammato
                        .getCarteNelMazzo()
                        .subList(0, metaMazzo)
                        .clear();
            } else {
                System.out.println("Errore: Il mazzo del giocatore è vuoto o non è stato inizializzato correttamente.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String stampaManoDeiGiocatori() {
        String stampa = "\n";
        int i = 0;
        for (String username : this.manoDeiGiocatori.keySet()) {
            stampa += "Mano di " + username + ": " + this.manoDeiGiocatori.get(username);
            if (i < getListaDeiGiocatori().size()) {
                // mette a capo tra un giocatore e l'altro
                stampa += "\n";
            }
            i++;
        }
        return stampa;
    }

    public void prendiCartaDaTavolo(AbstractGiocatore giocatore) {
        String username = giocatore.getUsername();

        if (true) {
            // controlla che il giocatore abbia una carta che è lo stesso numero della carta
        }
        try {
            Carta cartaGiocata;
            cartaGiocata = this.carteSulTavolo
                    .rimuoviCartaDalMazzo(this.carteSulTavolo.size() - 1);
            this.preseDeiGiocatori.get(username).aggiungiCartaAlMazzo(cartaGiocata);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(
                    "Non ci sono più carte sul tavolo oppure il giocatore ha al massimo 3 carte in mano.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            salvaPartita();
        }
    }

    /**
     * Questo metodo sarà probabilmente utilizzato solo dagli utenti CPU e andrà
     * modificato.
     * 
     * @param cartaDaCercare
     * @param giocatore
     */
    public void cercaCartaSulTavolo(Carta cartaDaCercare, String giocatore) {

        try {
            for (Carta carta : this.carteSulTavolo.getCarteNelMazzo()) {
                if (carta.getNome() == cartaDaCercare.getNome()) {
                    // prendi la carta dal tavolo e mettila nella mano del giocatore
                    Carta cartaGiocata = this.carteSulTavolo
                            .getCarteNelMazzo()
                            .remove(this.carteSulTavolo
                                    .getCarteNelMazzo()
                                    .indexOf(carta));

                    System.out.println("La carta è stata trovata!");
                    this.manoDeiGiocatori.get(giocatore).aggiungiCartaAlMazzo(cartaGiocata);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Sul tavolo non ci sono carte!" + e.getMessage());
        }

    }
}
