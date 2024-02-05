package com.spacca.asset.utente;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.spacca.App;
import com.spacca.asset.match.CreatoreDiTorneo;
import com.spacca.asset.match.Partita;
import com.spacca.asset.match.Torneo;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.asset.utente.giocatore.Giocatore;
import com.spacca.asset.utente.giocatore.SmartCPU;
import com.spacca.asset.utente.giocatore.StupidCPU;
import com.spacca.database.GiocatoreHandler;
import com.spacca.database.PartitaHandler;
import com.spacca.database.TorneoHandler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * AmministratoreI
 */
public class Amministratore extends AbstractGiocatore {
    transient static PartitaHandler partitaHandler = new PartitaHandler();
    transient TorneoHandler torneoHandler = new TorneoHandler();
    transient GiocatoreHandler giocatoreHandler = new GiocatoreHandler();

    public Amministratore() {
        super("admin", "admin");
    }

    public void eliminaPartita(String codice) {
        partitaHandler.elimina(codice);
    }

    public void eliminaTorneo(String codice) {
        torneoHandler.elimina(codice);
    }

    public void modificaProfiloGiocatore(String oldGiocatore, Object newGiocatore) throws FileNotFoundException {
        giocatoreHandler.modifica(oldGiocatore, newGiocatore);
    }

    public String generaNumeroCasualePartita() {
        Random random = new Random();
        // Genera un numero casuale compreso tra 1000 e 9999
        String numero = "P" + String.valueOf(random.nextInt(9000) + 1000);

        if (!partitaHandler.VerificaEsistenzaFile(numero)) {
            return numero;
        } else {
            return generaNumeroCasualePartita();
        }

    }

    public String generaNumeroCasualeTorneo() {
        Random random = new Random();
        // Genera un numero casuale compreso tra 1000 e 9999
        String numero = "T" + String.valueOf(random.nextInt(9000) + 1000);

        if (!torneoHandler.VerificaEsistenzaFile(numero)) {
            return numero;
        } else {
            return generaNumeroCasualeTorneo();
        }

    }

    public Partita creaPartita(List<String> giocatoriScelti) throws IllegalArgumentException {
        if (giocatoriScelti.size() < 2) {
            throw new IllegalArgumentException("Devi selezionare almeno due giocatori");
        }
        return creaPartita(generaNumeroCasualePartita(), giocatoriScelti);
    }

    public Partita creaPartita(String codicePartita, List<String> giocatoriScelti) {

        List<AbstractGiocatore> giocatori = new ArrayList<>();

        if (giocatoriScelti.size() < 2) {
            throw new IllegalArgumentException("Devi selezionare almeno due giocatori");
        }
        try {
            for (String username : giocatoriScelti) {
                giocatori.add(giocatoreHandler.carica(username));
            }
            // System.out.println("SIAMO DENTRO AMMINISTRATORE CREA PARTITA " + giocatori);
            // la crea e la salva in automatico
            new PartitaHandler().creaPartita(codicePartita, giocatori);
        } catch (Exception e) {
            System.err.println("Errore (Amministratore creaPartita): \n" + e.getMessage());
            e.printStackTrace();
        }
        return new PartitaHandler().carica(codicePartita);
    }

    public Partita caricaPartita(String codicePartita) {
        return partitaHandler.carica(codicePartita);
    }

    public void creaUtenteFisico(String username, String password, String email) {
        Giocatore giocatore = new Giocatore(username, password, email);
        giocatoreHandler.salva(giocatore, username);
    }

    public AbstractGiocatore creaGiocatore(String username, String type) {

        AbstractGiocatore giocatore = null;

        switch (type) {
            case "Giocatore":
                giocatore = new Giocatore(username, "password", "email");
                break;

            case "SmartCPU":
                giocatore = new SmartCPU(username);
                break;

            case "StupidCPU":
                giocatore = new StupidCPU(username);
                break;
            default:
                break;
        }

        // System.out.println("Classe del giocatore: " +
        // giocatore.getClass().getName());
        new GiocatoreHandler().salva(giocatore, username);
        return new GiocatoreHandler().carica(username);
    }

    public void creaUtenteRobot(String username, String type) {
        // TODO da mettere il tipo di utente robot
        AbstractGiocatore giocatore = new AbstractGiocatore(username, type);
        giocatoreHandler.salva(giocatore, username);
    }

    public AbstractGiocatore caricaUtente(String username) {
        return giocatoreHandler.carica(username);
    }

    public Torneo creaTorneo(String codiceTorneo, List<String> partecipanti) {

        Torneo torneo = new Torneo(codiceTorneo, partecipanti);

        for (String username : partecipanti) {
            new GiocatoreHandler().carica(username).addCodiceTorneo(codiceTorneo);
        }

        // crei partecipanti/2 partite e le aggiungi al torneo
        // Creazione delle partite
        System.out.println("codice torneo " + codiceTorneo);

        torneo = CreatoreDiTorneo.strutturaTorneo(codiceTorneo, partecipanti, torneo, this);

        torneoHandler.salva(torneo, torneo.getCodice());

        return torneo;
    }

    public String creaPartitaPerTorneo(String codiceTorneo, List<String> listaDeiGiocatori) {
        return creaPartita("tornei/" + codiceTorneo + "/T" + generaNumeroCasualePartita(), listaDeiGiocatori)
                .getCodice();
    }

    public Torneo creaTorneo(List<String> partecipanti) {
        return creaTorneo(generaNumeroCasualeTorneo(), partecipanti);
    }

    public void ritornaBenvenutoAdmin(Scene currentScene) {
        try {
            String path = "/com/spacca/pages/benvenutoAdmin.fxml";

            FXMLLoader loader = new FXMLLoader(App.class.getResource(path));
            Parent root = loader.load();

            Stage currentStage = (Stage) currentScene.getWindow();

            currentStage.setTitle("Benvenuto Admin ! ");
            currentStage.setScene(new Scene(root));
            currentStage.show();

        } catch (NullPointerException e) {
            System.err.println("Login avvenuto con successo!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IO Errore (Benvenuto Admin controller): \n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore (Benvenuto Admin controller): \n" + e.getMessage());
        }
    }

    void login() {
        // TODO
    }

    void logout() {
        // TODO
    }

    void aggiungiGiocatoreAlTorneo() {
        // TODO
    }

    void creaProfiloGiocatore() {
        // TODO
    }

    public Torneo caricaTorneo(String codiceTorneo) {
        return (Torneo) new TorneoHandler().carica(codiceTorneo);
    }
}