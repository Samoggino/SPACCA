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
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonWriter;
import com.spacca.asset.match.Partita;
import com.spacca.asset.match.Torneo;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;

public class TorneoHandler implements Handler {

    /**
     * salva la partita in un file JSON
     * 
     * @param torneo       l'istanza del torneo da salvare
     * @param codiceTorneo il codice del torneo con cui verrà nominato il
     *                     salvataggio
     */

    @Override // passare il codice per esteso
    public boolean salva(Object torneoObject, String codiceTorneo) {
        Torneo torneo = (Torneo) torneoObject;
        codiceTorneo = "src/main/resources/com/spacca/database/tornei/" + codiceTorneo + "/" + codiceTorneo + ".json";

        /* Implementare creazione partite anche? */

        try (JsonWriter writer = new JsonWriter(new FileWriter(codiceTorneo))) {
            Gson gson = new Gson();
            gson.toJson(torneo, Torneo.class, writer);
            return true;
        } catch (JsonIOException e) {
            System.err.println("ERRORE: Errore durante la scrittura del file JSON in\n" +
                    this.getClass().getName() + "\n" + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("ERRORE: File non trovato in\n" + "\n" + e.getMessage());
        } catch (IOException e) {
            System.err.println("ERRORE: Errore durante la scrittura del file JSON in\n" +
                    this.getClass().getName() + "\n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERRORE: Errore generico nel salvataggio torneo in\n" + "\n" + e.getMessage());
        }
        return false;
    }

    @Override
    public Object carica(String codice) {
        Torneo torneo = null;
        String path = "src/main/resources/com/spacca/database/tornei/" + codice + "/" + codice + ".json";
        try {

            Reader fileReader = new FileReader(path);
            Gson gson = new Gson();

            torneo = gson.fromJson(fileReader, Torneo.class);

            fileReader.close();
        } catch (JsonIOException e) {
            System.err.println("ERRORE: Errore durante la lettura del file JSON in\n" +
                    this.getClass().getName() + e.getMessage());
            // e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.err.println("ERRORE: Questo codice torneo non è valido." + e.getMessage());
        } catch (IOException e) {
            System.err.println("ERRORE: Errore durante la lettura del file JSON in\n" +
                    this.getClass().getName() + "\n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERRORE: Errore generico in\n" + this.getClass().getName() + "\n" + e.getMessage());
        }

        return torneo;
    }

    @Override
    public boolean elimina(String codice) {

        try {
            String path = "src/main/resources/com/spacca/database/tornei/" + codice;

            if (VerificaEsistenzaFile(codice)) {

                Torneo torneo = (Torneo) carica(codice);
                for (String codicePartita : torneo.getCodiciPartite()) {
                    System.out.println("Codice partita: " + codicePartita);
                    Partita partita = new PartitaHandler().carica(codicePartita);
                    System.out.println("Elimino la partita " + partita.getCodice());
                    // System.out.println("Elimino la partita " + codicePartita);
                    new PartitaHandler().elimina(partita.getCodice());
                }

                // rimuovi il codice del torneo da tutti i partecipanti

                for (String giocatore : torneo.getPartecipanti()) {
                    GiocatoreHandler handler = new GiocatoreHandler();
                    AbstractGiocatore giocatoreObject = handler.carica(giocatore);
                    giocatoreObject.getListaCodiciTornei().remove(torneo.getCodice());

                    handler.salva(giocatoreObject, giocatore);
                }

                rmdir(path);
                return true;
            }
        } catch (Exception e) {
            System.err.println("ERRORE (elimina): " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean elimina(Torneo torneo) {
        return elimina(torneo.getCodice());
    }

    @Override
    public Boolean VerificaEsistenzaFile(String codice) {
        System.out.println("Verifica esistenza file ma in torneo" + codice);
        try {
            String path = "src/main/resources/com/spacca/database/tornei/" + codice + "/" + codice + ".json";

            File userFile = new File(path);

            // Verifica se il file esiste
            if (userFile.exists() && userFile.isFile()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void modifica(String oldFileName, Object newObject) {
        // TODO Auto-generated method stub
    }

    public void mkdir(String codice) {

        File dir = new File("src/main/resources/com/spacca/database/tornei/" + codice);
        dir.mkdir();
    }

    public void rmdir(String directoryPath) {
        Path path = Paths.get(directoryPath);
        try {
            Files.walk(path)
                    .sorted(Collections.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
            System.out.println("Cartella eliminata con successo.");
        } catch (IOException e) {
            System.err.println("Errore durante l'eliminazione della cartella: " + e.getMessage());
        }
    }

    public List<String> mostraTuttiITornei() {
        String path = "src/main/resources/com/spacca/database/tornei/";
        File dir = new File(path);
        String[] files = dir.list();
        List<String> listaTornei = new ArrayList<>();
        for (String file : files) {
            if (file.startsWith("T")) {
                listaTornei.add(file);
            }
        }
        return listaTornei;
    }

}