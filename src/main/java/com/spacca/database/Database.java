package com.spacca.database;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.spacca.asset.carte.Carta;
import com.spacca.asset.carte.Mazzo;
import com.spacca.asset.match.Partita;
import com.spacca.asset.utente.giocatore.GiocatoreInterface;

public class Database {

    public Partita creaPartita(String codice, List<GiocatoreInterface> giocatori) {
        Partita partita = null;
        try {
            partita = new Partita("1234", giocatori, "2-1");
            salvaPartita(partita, codice);
        } catch (Exception e) {
            System.err.println("ERRORE: Errore generico in" + this.getClass().getName());
            e.printStackTrace();
        }
        return partita;
    }

    public void salvaPartita(Partita partita, String nomeFile) {

        nomeFile = "src/main/java/com/spacca/database/partite/" + nomeFile + ".json";

        Gson gson = new Gson();
        String json = gson.toJson(partita);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeFile))) {
            writer.write(json);
        } catch (JsonIOException e) {
            System.err.println("ERRORE: Errore durante la scrittura del file JSON in\n" + this.getClass().getName());
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.err.println("ERRORE: File non trovato in\n" + this.getClass().getName());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("ERRORE: Errore durante la scrittura del file JSON in\n" + this.getClass().getName());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("ERRORE: Errore generico in\n" + this.getClass().getName());
            e.printStackTrace();
        }
    }

    public void caricaPartita() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'caricaPartita'");
    }

    public void eliminaPartita() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminaPartita'");
    }

    public void creaTorneo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'creaTorneo'");
    }

    public void salvaTorneo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'salvaTorneo'");
    }

    public void caricaTorneo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'caricaTorneo'");
    }

    public void eliminaTorneo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminaTorneo'");
    }

    public void creaProfiloGiocatore() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'creaProfiloGiocatore'");
    }

    public void modificaProfiloGiocatore() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modificaProfiloGiocatore'");
    }

    public void aggiungiGiocatoreAlTorneo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'aggiungiGiocatoreAlTorneo'");
    }

    public void login() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    public void logout() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'logout'");
    }

    public void mostraLeaderboard() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostraLeaderboard'");
    }

    public void stampaCarteFromJson() {
        try {
            // leggo il file JSON
            Reader fileReader = new FileReader("src/main/java/com/spacca/database/carte.json");
            Gson gson = new Gson();
            // gson prende il fileReader e lo converte in un array di carte
            Carta[] carte = gson.fromJson(fileReader, Carta[].class);

            // Converti l'array di carte in un ArrayList di carte
            List<Carta> listaCarte = new ArrayList<>(Arrays.asList(carte));

            // Ora puoi utilizzare l'ArrayList di Carta come desideri
            Mazzo mazzo = new Mazzo(listaCarte);

            System.out.println(mazzo);

            // chiudo il fileReader
            fileReader.close();

        } catch (JsonIOException e) {
            System.err.println("ERRORE: Errore durante la lettura del file JSON");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.err.println("ERRORE: File non trovato");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("ERRORE: Errore durante la lettura del file JSON");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("ERRORE: Errore generico in" + this.getClass().getName());
            e.printStackTrace();
        }

    }

}
