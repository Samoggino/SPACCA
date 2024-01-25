package com.spacca.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonWriter;
import com.spacca.asset.match.Partita;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.asset.utente.giocatore.Giocatore;

public class PartitaHandler implements Handler {

    /**
     * salva la partita in un file JSON
     * 
     * @param partita       l'istanza della partita da salvare
     * @param codicePartita il codice della partita con cui verrà nominato il
     *                      salvataggio
     */
    @Override
    public void salva(Object partitaObject, String codicePartita) {

        Partita partita = (Partita) partitaObject;
        codicePartita = "src/main/resources/com/spacca/database/partite/" + codicePartita + ".json";

        try (JsonWriter writer = new JsonWriter(new FileWriter(codicePartita))) {
            Gson gson = new Gson();
            gson.toJson(partita, Partita.class, writer);

        } catch (JsonIOException e) {
            System.err.println("ERRORE: Errore durante la scrittura del file JSON in\n" +
                    this.getClass().getName() + "\n" + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("ERRORE: File non trovato in\n" + "\n" + e.getMessage());
        } catch (IOException e) {
            System.err.println("ERRORE: Errore durante la scrittura del file JSON in\n" +
                    this.getClass().getName() + "\n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERRORE: Errore generico in\n" + "\n" + e.getMessage());
        }
    }

    /**
     * apri il file che si chiama codicePartita.json
     * e leggi il contenuto del file e lo metti in una Partita.
     * Se non esiste il file, mostra all'utente la schermata per crearne una nuova.
     */
    @Override
    public Partita carica(String codicePartita) {
        Partita partita = null;
        try {
            codicePartita = codicePartita.toUpperCase();
            Reader fileReader = new FileReader(
                    "src/main/resources/com/spacca/database/partite/" + codicePartita + ".json");
            Gson gson = new Gson();

            partita = gson.fromJson(fileReader, Partita.class);
            System.out.println(partita);

            fileReader.close();
        } catch (JsonIOException e) {
            System.err.println("ERRORE: Errore durante la lettura del file JSON in\n" +
                    this.getClass().getName() + e.getMessage());
            // e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.err.println("ERRORE: Questo codice partita non è valido." + e.getMessage());
        } catch (IOException e) {
            System.err.println("ERRORE: Errore durante la lettura del file JSON in\n" +
                    this.getClass().getName() + "\n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERRORE: Errore generico in\n" + this.getClass().getName() + "\n" + e.getMessage());
        }

        return partita;
    }

    @Override
    public void elimina(String codice) {
        String path = "src/main/resources/com/spacca/database/partite/" + codice + ".json";
        File file = new File(path);
        Partita partita = this.carica(codice);
        List<String> listaDeiGiocatori = partita.getListaDeiGiocatori();

        if (file.exists() && file.isFile()) {
            if (file.delete()) {

                for (String username : listaDeiGiocatori) {
                    GiocatoreHandler handler = new GiocatoreHandler();
                    Giocatore giocatore = handler.carica(username);
                    giocatore.getListaCodiciPartite().remove(codice);
                    handler.salva(giocatore, username);
                    System.out
                            .println("Rimosso il codice " + codice + " dalla lista dei codici partite di " + username);
                    System.out.println("Codici partita di " + username + " " + giocatore.getListaCodiciPartite());
                }

                System.out.println("Il giocatore con codice " + codice + " è stato eliminato correttamente.");
            } else {
                System.err.println("Errore durante l'eliminazione del giocatore con codice " + codice);
            }
        } else {
            System.err.println("Il giocatore con codice " + codice + " non esiste o non è un file.");
        }
    }

    public Partita creaPartita(String codice, List<AbstractGiocatore> giocatori) {
        Partita partita;

        // TODO: controllo per il login
        List<String> listaDeiGiocatori = new ArrayList<>(giocatori.size());
        for (AbstractGiocatore abstractGiocatore : giocatori) {
            listaDeiGiocatori.add(abstractGiocatore.getUsername());
        }
        partita = new Partita(codice, listaDeiGiocatori);
        salva(partita, codice);

        return partita;
    }

}