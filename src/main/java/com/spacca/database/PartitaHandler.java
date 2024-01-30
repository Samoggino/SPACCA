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
        Partita partita = null;
        String path = "src/main/resources/com/spacca/database/partite/" + codicePartita + ".json";
        try {
            codicePartita = codicePartita.toUpperCase();

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
        String path = "src/main/resources/com/spacca/database/partite/" + codice + ".json";
        File file = new File(path);
        Partita partita = this.carica(codice);
        List<String> listaDeiGiocatori = partita.getListaDeiGiocatori();

        if (file.exists() && file.isFile()) {
            if (file.delete()) {

                for (String username : listaDeiGiocatori) {
                    Handler handler = new GiocatoreHandler();
                    AbstractGiocatore giocatore = (AbstractGiocatore) handler.carica(username);
                    giocatore.getListaCodiciPartite().remove(codice);
                    handler.salva(giocatore, username);
                }

            } else {
                System.err.println("Errore durante l'eliminazione del giocatore con codice " + codice);
            }
        } else {
            System.err.println("Il giocatore con codice " + codice + " non esiste o non è un file.");
        }
    }

    public Partita creaPartita(String codice, List<AbstractGiocatore> giocatori) {
        GiocatoreHandler giocatoreHandler = new GiocatoreHandler();

        List<String> listaDeiGiocatori = new ArrayList<>();
        // aggiungo a tutti i giocatori il codice della partita
        // e creo la lista con solo gli username per la creazione della partita
        System.out.println("SONO IN CREA PARTITA HANDLER" + giocatori);

        for (AbstractGiocatore abstractGiocatore : giocatori) {
            abstractGiocatore.addCodicePartita(codice);
            listaDeiGiocatori.add(abstractGiocatore.getUsername());
            // giocatoreHandler.modifica(abstractGiocatore.getUsername(), abstractGiocatore);
        }
        System.out.println("LISTA GIOCATORI" + listaDeiGiocatori);

        Partita partita = new Partita(codice, listaDeiGiocatori);

        // salva(partita, codice);

        return partita;
    }

    @Override
    public Boolean VerificaEsistenzaFile(String codice) {
        String path = "src/main/resources/com/spacca/database/partite/" + codice + ".json";

        File userFile = new File(path);

        // Verifica se il file esiste
        if (userFile.exists() && userFile.isFile()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void modifica(String oldPartita, Object partita) {
        // Percorso del file JSON dell'oldGiocatore
        String path = "src/main/resources/com/spacca/database/partite/" + oldPartita + ".json";

        Partita newPartita = (Partita) partita;

        System.out.println("Partita scelto " + oldPartita);
        Partita vecchiaPartita = carica(oldPartita);
        System.out.println("\n Vacchio partita" + vecchiaPartita + " \n ");
        System.out.println("\n Nuovo partita" + newPartita + " \n ");

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
                System.out.println("File non trovato " + e);
            } catch (Exception e) {
                System.out.println("Eccezione nella modifica del giocatore handler " + e);
                e.printStackTrace();

            }
        }
    }
}