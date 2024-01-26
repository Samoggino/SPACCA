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
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonWriter;
import com.spacca.asset.match.Partita;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.asset.utente.giocatore.Giocatore;
import com.spacca.asset.utente.giocatore.SmartCPU;

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
    public Giocatore carica(String username) {

        String path = "src/main/resources/com/spacca/database/giocatori/user-" + username + ".json";
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

    @Override
    public void elimina(String username) {
        /*
         * Handler handlerGiocatore = new GiocatoreHandler();
         * System.out.println("SONO IN ELIMINA: " + username);
         * 
         * String path = "src/main/resources/com/spacca/database/giocatori/user-" +
         * username + ".json";
         * 
         * File file = new File(path);
         * Giocatore giocatoreEliminato = (Giocatore) handlerGiocatore.carica(username);
         * 
         * if (VerificaEsistenzaFile(username)) {
         * if (file.delete()) {
         * 
         * System.out.println("\n giocatoreEliminato (elimina): " + giocatoreEliminato +
         * "\n");
         * 
         * List<String> listaCodiciPartita = giocatoreEliminato.getListaCodiciPartite();
         * System.out.println("\n listaCodiciPartita: " + listaCodiciPartita + "\n");
         * 
         * for (String codicePartita : listaCodiciPartita) {
         * Handler handlerPartita = new PartitaHandler();
         * System.out.println("\n Stiamo per caricare: " + codicePartita + "\n");
         * Partita partita = (Partita) handlerPartita.carica("P" + codicePartita);
         * System.out.println("\n PARTITA" + codicePartita + "\n");
         * System.out.println("\n LISTA GIOCATORI" + partita.getListaDeiGiocatori() +
         * "\n");
         * // rimuovo l'utente eliminato dalla lista dei giocatori delle partite
         * partita.getListaDeiGiocatori().remove(username);
         * System.out.println("\n LISTA GIOCATORI POST" + partita.getListaDeiGiocatori()
         * + "\n");
         * // creo nuovo sostituto
         * SmartCPU sostituto = new SmartCPU(username);
         * // dopo aver eliminato l'utente lo sostituisco con il computer, creando il
         * file
         * handlerPartita.salva(sostituto, username);
         * // lo aggiungo alla lista dei giocatori della partita
         * partita.getListaDeiGiocatori().add(sostituto.getUsername());
         * 
         * handlerPartita.modifica(codicePartita, partita);
         * // handler.salva(partita, codicePartita);
         * System.out
         * .println("Rimosso lo username " + username + " dalla lista delle partite  " +
         * partita);
         * System.out.println(
         * "username " + username + " lista codici" + partita.getListaDeiGiocatori());
         * }
         * 
         * System.out.println("Il giocatore con codice " + username +
         * " è stato eliminato correttamente.");
         * } else {
         * System.err.println("Errore durante l'eliminazione del giocatore con codice "
         * + username);
         * }
         * } else {
         * System.err.println("Il giocatore con codice " + username +
         * " non esiste o non è un file.");
         * }
         */
    }

    @Override
    public Boolean VerificaEsistenzaFile(String username) {
        String path = "src/main/resources/com/spacca/database/giocatori/user-" + username + ".json";

        File userFile = new File(path);

        // Verifica se il file esiste
        if (userFile.exists() && userFile.isFile()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void modifica(String oldGiocatore, Object newObject) {
        Giocatore newGiocatore = (Giocatore) newObject;
        // Percorso del file JSON dell'oldGiocatore
        String path = "src/main/resources/com/spacca/database/giocatori/user-" + oldGiocatore + ".json";

        System.out.println("Giocatore scelto " + oldGiocatore);
        Giocatore vecchioGiocatore = carica(oldGiocatore);
        System.out.println("\n Vacchio giocatore " + vecchioGiocatore + " \n ");
        System.out.println("\n Nuovo giocatore " + newGiocatore + " \n ");

        // se è stato modificato lo username creo il nuovo file ed elimino il vecchio
        if (!oldGiocatore.equals(newGiocatore.getUsername())) {
            salva(newGiocatore, newGiocatore.getUsername());
            elimina(oldGiocatore);
        } else { // se non è stato modificato lo username ricarico il file
            try {
                Path playerFilePath = Paths.get(path);
                // Leggi il contenuto del file JSON e deserializza in un oggetto Giocatore
                Gson gson = new Gson();

                // Sovrascrivi il contenuto del file JSON con il nuovo JSON
                String updatedJsonContent = gson.toJson(newGiocatore);
                Files.write(playerFilePath, updatedJsonContent.getBytes());
            } catch (IOException e) {
                System.out.println("File non trovato " + e);
            } catch (Exception e) {
                System.out.println("Eccezione nella modifica del giocatore handler " + e);
                e.printStackTrace();

            }
        }
    }

    public List<String> getAllGiocatori() {
        List<String> modifiedFileNames = null;
        try {
            String folderPath = "/com/spacca/database/giocatori/";

            // Ottieni il percorso completo della cartella delle risorse
            Path resourceFolder = Paths.get(getClass().getResource(folderPath).toURI());

            // Ottieni la lista dei nomi dei file JSON presenti nella cartella
            // escludo gli amministratori e gli utenti robot
            List<String> fileNames = Files.list(resourceFolder)
                    .filter(path -> path.toString().endsWith(".json") && Files.isRegularFile(path))
                    .filter(path -> !path.getFileName().toString().equals("user-admin.json"))
                    .filter(path -> !path.getFileName().toString().startsWith("user-RS-"))
                    .filter(path -> !path.getFileName().toString().startsWith("user-RI-"))
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());

            // Rimuovi "user-" e ".json" dai nomi dei file
            modifiedFileNames = fileNames.stream()
                    .map(fileName -> fileName.replace("user-", "").replace(".json", ""))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modifiedFileNames;
    }

}