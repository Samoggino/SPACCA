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
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonWriter;
import com.spacca.asset.match.Partita;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;

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

        System.out.println("Salvo la partita");
        Partita partita = (Partita) partitaObject;

        String path = "src/main/resources/com/spacca/database/partite/" + codicePartita + ".json";
        System.out.println("path partita: " + path + " prima della modifica in salva partita");

        if (!codicePartita.startsWith("P")) {
            path = "src/main/resources/com/spacca/database/" + codicePartita + ".json";
            // dopo aver sistemato il path, devo sistemare il codicePartita, ovvero
            // dev'essere
            // presa solo la parte che è compresa tra l'ultimo / e il .json
            codicePartita = codicePartita.substring(codicePartita.lastIndexOf("P"));
            System.out.println("codice partita: " + codicePartita + " dopo la modifica in salva partita");
        }
        try (JsonWriter writer = new JsonWriter(new FileWriter(path))) {

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
            System.err.println("ERRORE: Errore generico in salva partita\n" + "\n" + e.getMessage());
        }
    }

    /**
     * apri il file che si chiama codicePartita.json
     * e leggi il contenuto del file e lo metti in una Partita.
     * Se non esiste il file, mostra all'utente la schermata per crearne una nuova.
     */
    @Override
    public Partita carica(String codicePartita) {
        System.out.println("Carico la partita");
        Partita partita = null;
        try {
            String path = "src/main/resources/com/spacca/database/partite/" + codicePartita + ".json";

            if (!codicePartita.startsWith("P")) {
                path = "src/main/resources/com/spacca/database/" + codicePartita + ".json";
                // dopo aver sistemato il path, devo sistemare il codicePartita, ovvero
                // dev'essere
                // presa solo la parte che è compresa tra l'ultimo / e il .json
                codicePartita = codicePartita.substring(codicePartita.lastIndexOf("/"));
            }

            Reader fileReader = new FileReader(path);
            Gson gson = new Gson();

            partita = gson.fromJson(fileReader, Partita.class);

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
        System.out.println("Elimino la partita");

        String path = "src/main/resources/com/spacca/database/partite/" + codice + ".json";

        Partita partita = null;
        partita = this.carica(codice);

        if (!codice.startsWith("P")) {
            path = "src/main/resources/com/spacca/database/" + codice + ".json";
            // dopo aver sistemato il path, devo sistemare il codice, ovvero dev'essere
            // presa solo la parte che è compresa tra l'ultimo / e il .json
            partita = this.carica(codice);
            System.out.println("codice partita: " + partita.getCodice() + " dopo la modifica in elimina");
        }
        System.out.println("path partita: " + path + " prima della modifica in elimina");

        // controllo che il codice cominci per P, altrimenti quello non è un codice
        // partita ma un path di una partita di un torneo

        File file = new File(path);
        System.out.println("Elimino la partita " + codice);
        List<String> listaDeiGiocatori = partita.getListaDeiGiocatori();

        if (VerificaEsistenzaFile(codice)) {
            if (file.delete()) {

                for (String username : listaDeiGiocatori) {
                    GiocatoreHandler handler = new GiocatoreHandler();
                    AbstractGiocatore giocatore = (AbstractGiocatore) handler.carica(username);
                    giocatore.getListaCodiciPartite().remove(codice);
                    System.out.println("Elimino la partita " + codice + " dal giocatore " + giocatore.getUsername());
                    handler.salva(giocatore, username);
                }

            } else {
                System.err.println("Errore durante l'eliminazione del giocatore con codice " + codice);
            }
        } else {
            System.err.println("La partita con codice " + codice + " non esiste o non è un file.");
        }
    }

    public void eliminaPartitaDaTorneo(String codicePartita, String codiceTorneo) {
        String path = "tornei/" + codiceTorneo + "/" + codicePartita + ".json";
        elimina(path);
    }

    public Partita creaPartita(String codice, List<AbstractGiocatore> giocatori) {
        System.out.println("Creo la partita");
        Partita partita = null;

        try {
            // if (!codice.startsWith("P")) {
            // // dopo aver sistemato il path, devo sistemare il codice, ovvero dev'essere
            // // presa solo la parte che è compresa tra l'ultimo / e il .json
            // codice = codice.substring(codice.lastIndexOf("/"));
            // System.out.println("codice partita: " + codice + "dopo la modifica in
            // creaPartita");
            // }

            List<String> listaDeiGiocatori = new ArrayList<>();
            // aggiungo a tutti i giocatori il codice della partita
            // e creo la lista con solo gli username per la creazione della partita

            for (AbstractGiocatore abstractGiocatore : giocatori) {
                abstractGiocatore.addCodicePartita(codice);
                listaDeiGiocatori.add(abstractGiocatore.getUsername());
                new GiocatoreHandler().salva(abstractGiocatore, abstractGiocatore.getUsername());
                // giocatoreHandler.modifica(abstractGiocatore.getUsername(),
                // abstractGiocatore);
            }

            partita = new Partita(codice, listaDeiGiocatori);
            System.out.println("codice partita: " + partita.getCodice() + " dopo la modifica in creaPartita");

            // salva(partita, codice);

        } catch (Exception e) {
            System.err.println(
                    "ERRORE (in PartitaHandler:creaPartita) durante la creazione della partita " + e.getMessage());
            e.printStackTrace();
        }
        return partita;

    }

    @Override
    public Boolean VerificaEsistenzaFile(String codice) {
        System.out.println("Verifico l'esistenza della partita");
        try {
            String path = "src/main/resources/com/spacca/database/partite/" + codice + ".json";
            System.out.println("path partita: " + path + " prima della modifica in creaPartita");

            if (!codice.startsWith("P")) {
                path = "src/main/resources/com/spacca/database/" + codice + ".json";
                // dopo aver sistemato il path, devo sistemare il codice, ovvero dev'essere
                // presa solo la parte che è compresa tra l'ultimo / e il .json
                System.out.println("codice partita: " + codice + " prima della modifica in creaPartita");
                codice = codice.substring(codice.lastIndexOf("P"));
                System.out.println("codice partita: " + codice + "dopo la modifica in creaPartita");
            }

            File userFile = new File(path);

            // Verifica se il file esiste
            if (userFile.exists() && userFile.isFile()) {
                return true;
            }

        } catch (Exception e) {
            System.err.println("ERRORE (VerificaEsistenzaFile): " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void modifica(String oldPartita, Object partita) {
        // Percorso del file JSON dell'oldGiocatore
        String path = "src/main/resources/com/spacca/database/partite/" + oldPartita + ".json";

        Partita newPartita = (Partita) partita;

        // Partita vecchiaPartita = carica(oldPartita);

        // se è stato modificato lo username creo il nuovo file ed elimino il vecchio
        if (!oldPartita.equals(newPartita.getCodice())) {
            salva(newPartita, newPartita.getCodice());
            elimina(oldPartita);
        } else { // se non è stato modificato lo username ricarico il file
            try {
                Path playerFilePath = Paths.get(path);
                // Leggi il contenuto del file JSON e deserializza in un oggetto Giocatore
                Gson gson = new Gson();

                // Sovrascrivi il contenuto del file JSON con il nuovo JSON
                String updatedJsonContent = gson.toJson(newPartita);
                Files.write(playerFilePath, updatedJsonContent.getBytes());

            } catch (IOException e) {
                System.err.println("File non trovato " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Eccezione nella modifica del giocatore handler " + e);
                e.printStackTrace();

            }
        }
    }
}