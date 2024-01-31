package com.spacca.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonWriter;
import com.spacca.asset.match.Torneo;

public class TorneoHandler implements Handler {

    /**
     * salva la partita in un file JSON
     * 
     * @param torneo       l'istanza del torneo da salvare
     * @param codiceTorneo il codice del torneo con cui verrà nominato il
     *                     salvataggio
     */

    @Override // passare il codice per esteso
    public void salva(Object torneoObject, String codiceTorneo) {
        Torneo torneo = (Torneo) torneoObject;
        codiceTorneo = "src/main/resources/com/spacca/database/tornei/" + codiceTorneo + "/" + codiceTorneo + ".json";

        /* Implementare creazione partite anche? */

        try (JsonWriter writer = new JsonWriter(new FileWriter(codiceTorneo))) {
            Gson gson = new Gson();
            gson.toJson(torneo, Torneo.class, writer);

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
            System.err.println("ERRORE: Questo codice partita non è valido." + e.getMessage());
        } catch (IOException e) {
            System.err.println("ERRORE: Errore durante la lettura del file JSON in\n" +
                    this.getClass().getName() + "\n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERRORE: Errore generico in\n" + this.getClass().getName() + "\n" + e.getMessage());
        }

        return torneo;
    }

    @Override
    public void elimina(String codice) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'elimina'");
    }

    @Override // passare solo il codice per esteso
    public Boolean VerificaEsistenzaFile(String codice) {
        String path = "src/main/resources/com/spacca/database/tornei/" + codice + ".json";

        File userFile = new File(path);

        // Verifica se il file esiste
        if (userFile.exists() && userFile.isFile()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void modifica(String oldFileName, Object newObject) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modifica'");
    }

    public void mkdir(String codice) {

        File dir = new File("src/main/resources/com/spacca/database/tornei/" + codice);
        dir.mkdir();

    }

}