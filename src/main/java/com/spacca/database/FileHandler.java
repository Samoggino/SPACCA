package com.spacca.database;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.spacca.asset.carte.Carta;
import com.spacca.asset.match.Partita;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;

public class FileHandler {

    /**
     * crea una partita con un codice e una lista di giocatori
     * 
     * @param codice    il codice della partita
     * @param giocatori la lista dei giocatori
     * @return l'istanza della partita
     */
    public Partita creaPartita(String codice, List<AbstractGiocatore> giocatori) {
        Partita partita = null;
        try {
            // TODO: controllo per il login
            List<String> listaDeiGiocatori = new ArrayList<>(giocatori.size());
            for (AbstractGiocatore abstractGiocatore : giocatori) {
                listaDeiGiocatori.add(abstractGiocatore.getUsername());
            }
            partita = new Partita(codice, listaDeiGiocatori);
            salvaPartita(partita, codice);
        } catch (Exception e) {
            e.printStackTrace();
            // System.err.println("ERRORE: Errore generico in" + this.getClass().getName());
        }
        return partita;
    }

    /**
     * salva la partita in un file JSON
     * 
     * @param partita       l'istanza della partita da salvare
     * @param codicePartita il codice della partita con cui verrà nominato il
     *                      salvataggio
     */
    public void salvaPartita(Partita partita, String codicePartita) {

        codicePartita = "src/main/resources/com/spacca/database/partite/" + codicePartita + ".json";

        Gson gson = new Gson();
        String json = gson.toJson(partita);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(codicePartita))) {
            writer.write(json);

        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * apri il file che si chiama codicePartita.json
     * e leggi il contenuto del file e lo metti in una Partita.
     * Se non esiste il file, mostra all'utente la schermata per crearne una nuova.
     */
    public Partita caricaPartita(String codicePartita) {
        Partita partita = null;

        try {
            Reader fileReader = new FileReader(
                    "src/main/resources/com/spacca/database/partite/" + codicePartita + ".json");
            Gson gson = new Gson();

            partita = gson.fromJson(fileReader, Partita.class);
            System.out.println(gson.toJson(partita));

            fileReader.close();
        } catch (JsonIOException e) {
            // System.err.println("ERRORE: Errore durante la lettura del file JSON in\n" +
            // this.getClass().getName());
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // System.err.println("ERRORE: File non trovato in\n" +
            // this.getClass().getName());
            e.printStackTrace();
        } catch (IOException e) {
            // System.err.println("ERRORE: Errore durante la lettura del file JSON in\n" +
            // this.getClass().getName());
            e.printStackTrace();
        } catch (Exception e) {
            // System.err.println("ERRORE: Errore generico in\n" +
            // this.getClass().getName());
            e.printStackTrace();
        }

        return partita;
    }

    /*
     * public void eliminaPartita() {
     * // TODO Auto-generated method stub
     * throw new
     * UnsupportedOperationException("Unimplemented method 'eliminaPartita'");
     * }
     * 
     * public void creaTorneo() {
     * // TODO Auto-generated method stub
     * throw new UnsupportedOperationException("Unimplemented method 'creaTorneo'");
     * }
     * 
     * public void salvaTorneo() {
     * // TODO Auto-generated method stub
     * throw new
     * UnsupportedOperationException("Unimplemented method 'salvaTorneo'");
     * }
     * 
     * public void caricaTorneo() {
     * // TODO Auto-generated method stub
     * throw new
     * UnsupportedOperationException("Unimplemented method 'caricaTorneo'");
     * }
     * 
     * public void eliminaTorneo() {
     * // TODO Auto-generated method stub
     * throw new
     * UnsupportedOperationException("Unimplemented method 'eliminaTorneo'");
     * }
     * 
     * public void creaProfiloGiocatore() {
     * // TODO Auto-generated method stub
     * throw new
     * UnsupportedOperationException("Unimplemented method 'creaProfiloGiocatore'");
     * }
     * 
     * public void modificaProfiloGiocatore() {
     * // TODO Auto-generated method stub
     * throw new
     * UnsupportedOperationException("Unimplemented method 'modificaProfiloGiocatore'"
     * );
     * }
     * 
     * public void aggiungiGiocatoreAlTorneo() {
     * // TODO Auto-generated method stub
     * throw new
     * UnsupportedOperationException("Unimplemented method 'aggiungiGiocatoreAlTorneo'"
     * );
     * }
     * 
     * public void login() {
     * // TODO Auto-generated method stub
     * throw new UnsupportedOperationException("Unimplemented method 'login'");
     * }
     * 
     * public void logout() {
     * // TODO Auto-generated method stub
     * throw new UnsupportedOperationException("Unimplemented method 'logout'");
     * }
     * 
     * public void mostraLeaderboard() {
     * // TODO Auto-generated method stub
     * throw new
     * UnsupportedOperationException("Unimplemented method 'mostraLeaderboard'");
     * }
     */

    /**
     * Metodo che stampa le carte dal file JSON
     */
    public Carta[] prendiLeCarteDalJson() {
        Carta[] carte = null;
        try {
            // leggo il file JSON
            Reader fileReader = new FileReader("src/main/resources/com/spacca/database/carte.json");
            Gson gson = new Gson();

            // gson prende il fileReader e lo converte in un array di carte
            carte = gson.fromJson(fileReader, Carta[].class);

            // chiudo il fileReader
            fileReader.close();

        } catch (JsonIOException e) {
            // System.err.println("ERRORE: Errore durante la lettura del file JSON");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // System.err.println("ERRORE: File non trovato");
            e.printStackTrace();
        } catch (IOException e) {
            // System.err.println("ERRORE: Errore durante la lettura del file JSON");
            e.printStackTrace();
        } catch (Exception e) {
            // System.err.println("ERRORE: Errore generico in" + this.getClass().getName());
            e.printStackTrace();
        }

        return carte;

    }

    public void eliminaPartita(String codice) {
        // TODO
    }

}
