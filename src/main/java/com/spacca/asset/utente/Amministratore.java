package com.spacca.asset.utente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.spacca.App;
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

    public void modificaProfiloGiocatore(String oldGiocatore, Object newGiocatore) {
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
            return "T" + generaNumeroCasualeTorneo();
        }

    }

    public Partita creaPartita(String codicePartita, List<String> giocatoriScelti) {

        List<AbstractGiocatore> giocatori = new ArrayList<>();

        for (String username : giocatoriScelti) {
            giocatori.add(giocatoreHandler.carica(username));
        }
        System.out.println("SIAMO DENTRO AMMINISTRATORE CREA PARTITA " + giocatori);
        // la crea e la salva in automatico

        partitaHandler.creaPartita(codicePartita, giocatori);
        return partitaHandler.carica(codicePartita);
    }

    public Partita caricaPartita(List<String> giocatoriDellaPartita) {
        return creaPartita(generaNumeroCasualePartita(), giocatoriDellaPartita);
    }

    public Giocatore creaUtenteFisico(String username, String password, String email) {
        Giocatore giocatore = new Giocatore(username, password, email);
        new GiocatoreHandler().salva(giocatore, username);
        return giocatore;
    }

    public AbstractGiocatore creaCPU(String username, String type) {
        // TODO da mettere il tipo di utente robot
        AbstractGiocatore giocatore = null;

        if (type.equals("SmartCPU")) {
            giocatore = new SmartCPU(username);
            System.out.println("SONO IN CREA UTENTE ROBOT" + giocatore);
        } else if (type.equals("StupidCPU")) {
            giocatore = new StupidCPU(username);
            System.out.println("SONO IN CREA UTENTE ROBOT" + giocatore);
        }

        GiocatoreHandler handler = new GiocatoreHandler();
        handler.salva(giocatore, username);
        return handler.carica(username);
    }

    public AbstractGiocatore creaUtenteRobot(String username) {
        // TODO da mettere il tipo di utente robot
        AbstractGiocatore giocatore = new AbstractGiocatore(username);
        giocatoreHandler.salva(giocatore, username);
        return giocatore;
    }

    public AbstractGiocatore caricaUtente(String username) {
        return giocatoreHandler.carica(username);
    }

    public Torneo creaTorneo(String codiceTorneo, List<String> giocatoriScelti) {
        // TODO
        Torneo torneo = new Torneo(codiceTorneo, giocatoriScelti);
        // TODOO creare anche le partite
        torneoHandler.salva(torneo, codiceTorneo);
        return torneo;
    }

    public Torneo creaTorneo(List<String> giocatoriScelti) {
        // TODO
        Torneo torneo = new Torneo(generaNumeroCasualeTorneo(), giocatoriScelti);
        // TODOO creare anche le partite
        torneoHandler.salva(torneo, torneo.getCodice());
        return torneo;
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
            System.out.println("Login avvenuto con successo!");
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
}