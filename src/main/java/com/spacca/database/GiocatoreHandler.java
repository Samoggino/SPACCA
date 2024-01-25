package com.spacca.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonWriter;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.asset.utente.giocatore.Giocatore;

public class GiocatoreHandler implements Handler {

    @Override
    public void salva(Object utenteObject, String username) {

        String path = "src/main/resources/com/spacca/database/giocatori/user-" + username + ".json";

        try (JsonWriter writer = new JsonWriter(new FileWriter(path))) {

            AbstractGiocatore giocatore;
            Gson gson = new Gson();

            if (utenteObject instanceof Giocatore) {
                giocatore = (Giocatore) utenteObject;
                gson.toJson(giocatore, Giocatore.class, writer);
            } else {
                giocatore = (AbstractGiocatore) utenteObject;
                gson.toJson(giocatore, AbstractGiocatore.class, writer);
            }
            System.out.println("Utente salvato correttamente in formato JSON.");

        } catch (JsonIOException e) {
            System.err.println("ERRORE: Errore durante la scrittura del file JSON in\n" + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("ERRORE: File non trovato in\n" + e.getMessage());
        } catch (IOException e) {
            System.err.println("ERRORE: Errore durante la scrittura del file JSON in\n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERRORE: Errore generico in\n" + e.getMessage());
        }
    }

    @Override
    public Giocatore carica(String path) {

        Giocatore giocatore = null;
        try {

            Reader fileReader = new FileReader(path);
            Gson gson = new Gson();

            giocatore = gson.fromJson(fileReader, Giocatore.class);
            System.out.println("Giocatore:" + giocatore + " caricato correttamente in formato JSON.\n\n\n\n");

            fileReader.close();
        } catch (JsonIOException e) {
            System.err.println("ERRORE: Errore durante la lettura del file JSON" + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("ERRORE: File non trovato" + e.getMessage());
        } catch (IOException e) {
            System.err.println("ERRORE: Errore durante la lettura del file JSON" + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERRORE: Errore generico in" + e.getMessage());
        }

        System.out.println("Giocatore:" + giocatore);
        return giocatore;
    }

    @Override // attenzione passare il codice con il nome del file per intero quindi
              // user-codice.json
    public void elimina(String codice) {
        String path = "src/main/resources/com/spacca/database/giocatori/" + codice;
        File file = new File(path);

        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("Il giocatore con codice " + codice + " è stato eliminato correttamente.");
            } else {
                System.err.println("Errore durante l'eliminazione del giocatore con codice " + codice);
            }
        } else {
            System.err.println("Il giocatore con codice " + codice + " non esiste o non è un file.");
        }
    }

    // attenzione passare l'oldGiocatore con il nome del file per intero quindi
    // user-codice.json
    public void modifica(String oldGiocatore, Giocatore newGiocatore) {
        // Percorso del file JSON dell'oldGiocatore
        String path = "src/main/resources/com/spacca/database/giocatori/" + oldGiocatore;
        Path playerFilePath = Paths.get(path);

        try (Reader fileReader = new FileReader(playerFilePath.toFile())) {
            // Leggi il contenuto del file JSON e deserializza in un oggetto Giocatore
            Gson gson = new Gson();
            Giocatore giocatore = gson.fromJson(fileReader, Giocatore.class);

            // Modifica i dati del giocatore esistente con quelli del nuovo giocatore
            // giocatore.setUsername(newGiocatore.getUsername());
            giocatore.setPassword(newGiocatore.getPassword());
            giocatore.setEmail(newGiocatore.getEmail());

            // Sovrascrivi il contenuto del file JSON con il nuovo JSON
            String updatedJsonContent = gson.toJson(giocatore);
            Files.write(playerFilePath, updatedJsonContent.getBytes());

            System.out.println("Giocatore modificato con successo: " + oldGiocatore);
        } catch (Exception e) {
            System.err.println("Errore durante la modifica del giocatore " + oldGiocatore + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}