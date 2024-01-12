package com.spacca.database;

import java.io.BufferedWriter;
import java.io.File;
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
    public void salva(Object object, String path) {
        // TODO Auto-generated method stub
    }

    @Override
    public Object carica(String path) {

        File userFile = new File(path);

        Giocatore utente = null;
        try (JsonWriter writer = new JsonWriter(new FileWriter(path))) {
            // leggo il file JSON
            Reader fileReader = new FileReader(userFile);
            Gson gson = new Gson();

            // gson prende il fileReader e lo converte in abstractgiocatore
            utente = gson.fromJson(fileReader, Giocatore.class);
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

        return utente;
    }

    @Override
    public void elimina(String codice) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'elimina'");
    }

    public Giocatore leggiUtente(File userFile) {
        Giocatore utente = null;
        try {
            // leggo il file JSON
            Reader fileReader = new FileReader(userFile);
            Gson gson = new Gson();

            // gson prende il fileReader e lo converte in abstractgiocatore
            utente = gson.fromJson(fileReader, Giocatore.class);

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

        return utente;

    }

    public void salvaUtente(AbstractGiocatore utente) {

        String CredenzialiUtente = "src/main/resources/com/spacca/database/giocatori/" + utente.getUsername() + ".json";

        Gson gson = new Gson();
        String json = gson.toJson(utente);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CredenzialiUtente))) {
            writer.write(json);

        } catch (JsonIOException e) {
            // System.err.println("ERRORE: Errore durante la scrittura del file JSON in\n" +
            // this.getClass().getName());
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // System.err.println("ERRORE: File non trovato in\n" +
            // this.getClass().getName());
            e.printStackTrace();
        } catch (IOException e) {
            // System.err.println("ERRORE: Errore durante la scrittura del file JSON in\n" +
            // this.getClass().getName());
            e.printStackTrace();
        } catch (Exception e) {
            // System.err.println("ERRORE: Errore generico in\n" +
            // this.getClass().getName());
            e.printStackTrace();
        }
    }
}
