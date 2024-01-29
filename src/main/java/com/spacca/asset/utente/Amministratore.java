package com.spacca.asset.utente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.spacca.App;
import com.spacca.asset.match.Partita;
import com.spacca.asset.match.Torneo;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.database.GiocatoreHandler;
import com.spacca.database.PartitaHandler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * AmministratoreI
 */
public class Amministratore {
    transient PartitaHandler partitaHandler = new PartitaHandler();

    GiocatoreHandler giocatoreHandler = new GiocatoreHandler();

    void creaTorneo() {
        // TODO
    }

    void eliminaPartita() {
        // TODO
    }

    void eliminaTorneo() {
        // TODO
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

    void modificaProfiloGiocatore() {
        // TODO
    }

    public Torneo creaNuovoTorneo() {
        return null;
        // return new Torneo("T" + generaNumeroCasuale());
    }

    protected static String generaNumeroCasuale() {
        // FIXME: non deve generare numeri uguali
        Random random = new Random();
        // Genera un numero casuale compreso tra 1000 e 9999
        String numero = String.valueOf(random.nextInt(9000) + 1000);
        return numero;
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

    public void ritornaBenvenutoAdmin() {
        try {
            String path = "/com/spacca/pages/benvenutoAdmin.fxml";

            FXMLLoader loader = new FXMLLoader(App.class.getResource(path));
            Parent root = loader.load();

            Scene currentScene = App.getScene();

            // Ottieni lo Stage dalla scena corrente
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

    public Partita caricaPartita(List<String> giocatoriDellaPartita) {
        return creaPartita(generaNumeroCasuale(), giocatoriDellaPartita);
    }

}