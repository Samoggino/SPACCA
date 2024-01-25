package com.spacca.database;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

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
    public void elimina(String codice) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'elimina'");
    }

}