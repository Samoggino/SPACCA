package com.spacca.database;

import java.io.File;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.spacca.asset.match.Partita;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.asset.match.Partita;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.asset.utente.giocatore.Giocatore;
import com.spacca.asset.utente.giocatore.SmartCPU;
import com.spacca.asset.utente.giocatore.StupidCPU;
import com.spacca.asset.utente.giocatore.StupidCPU;

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
    public AbstractGiocatore carica(String username) {

        AbstractGiocatore giocatoreVero = null;

        String path = "src/main/resources/com/spacca/database/giocatori/user-" + username + ".json";

        try {
            Reader fileReader = new FileReader(path);

            Gson gson = new Gson();

            // Lettura del file JSON
            JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);

            // Ottieni il valore del campo "type"
            String type = jsonObject.get("type").getAsString();

            if (type.equals("Giocatore")) {
                giocatoreVero = gson.fromJson(jsonObject, Giocatore.class);
            } else if (type.equals("SmartCPU")) {
                giocatoreVero = gson.fromJson(jsonObject, SmartCPU.class);
            } else if (type.equals("StupidCPU")) {
                giocatoreVero = gson.fromJson(jsonObject, StupidCPU.class);
            } else {
                System.err.println("ERRORE: Tipo di giocatore non riconosciuto");
            }

            fileReader.close();

        } catch (JsonIOException e) {
            System.err.println("ERRORE: Errore durante la lettura del file JSON" + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("ERRORE: File non trovato" + e.getMessage());
        } catch (IOException e) {
            System.err.println("ERRORE: Errore durante la lettura del file JSON" + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERRORE: Errore generico in " + e.getMessage());
        }

        return giocatoreVero;

    }

    @Override
    public void elimina(String username) {
        String path = "src/main/resources/com/spacca/database/giocatori/user-" + username + ".json";

        File file = new File(path);

        Giocatore giocatoreEliminato = (Giocatore) this.carica(username);

        List<String> listaCodici = giocatoreEliminato.getListaCodiciPartite();

        if (file.exists() && file.isFile()) {
            if (file.delete()) {

                for (String codice : listaCodici) {
                    Handler handlerPartita = new PartitaHandler();
                    // carico la partita con il codice presente nella lista per eliminare il
                    // giocatore
                    Partita partita = (Partita) handlerPartita.carica(codice);
                    // rimuovo il giocatore nella lista dei giocatori della partita
                    int posizioneVecchioUtente = partita.getListaDeiGiocatori().indexOf(username);

                    // creo un nuovo giocatore stupido e lo sostituisco con un giocatore robot
                    // stupido
                    Handler handlerGiocatore = new GiocatoreHandler();
                    String usernameStupid = "RS-" + username;
                    AbstractGiocatore giocatoreSostituto = new StupidCPU(usernameStupid);

                    partita.getListaDeiGiocatori().set(posizioneVecchioUtente, usernameStupid);
                    // salvo su file il nuovo giocatore stupido
                    handlerGiocatore.salva(giocatoreSostituto, usernameStupid);

                    // salvo la partita modificata
                    handlerPartita.salva(partita, codice);

                }

            } else {
                System.err.println("Errore durante l'eliminazione del giocatore  " + username);
            }
        } else {
            System.err.println("Il giocatore con username " + username + " non esiste o non è un file.");
        }
    }

}