package com.spacca.asset.carte;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.annotations.SerializedName;

public class Mazzo {

    @SerializedName("carte:")
    private List<Carta> carteNelMazzo;

    public Mazzo() {
        this.carteNelMazzo = new ArrayList<>(); // Initialize the list in the default constructor
    }

    public Mazzo(List<Carta> mazzoUtente) {
        this.carteNelMazzo = mazzoUtente;
    }

    public Mazzo creaMazzoDiPartenza() {
        try {
            this.carteNelMazzo = new ArrayList<>(Arrays.asList(prendiLeCarteDalJson()));
            Collections.shuffle(this.carteNelMazzo);
        } catch (NullPointerException e) {
            System.err.println("Errore nel creare il mazzo di partenza\n\n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore generico nel creare il mazzo di partenza\n\n" + e.getMessage());
        }
        return this;
    }

    private String stampa() {
        String stampa = "";
        if (carteNelMazzo == null) {
            return "Nessuna carta nel mazzo.";
        }

        int i = 0; // contatore per vedere se stampa tutte le 40 carte
        for (Carta carta : carteNelMazzo) {
            i++;
            stampa += i + ": " + carta.stampa() + "\n";
        }
        return stampa;
    }

    @Override
    public String toString() {
        return stampa();
    }

    public List<Carta> getCarteNelMazzo() {
        return this.carteNelMazzo;
    }

    public void setCarteNelMazzo(List<Carta> carte) {
        this.carteNelMazzo = carte;
    }

    public void aggiungiCarteAlMazzo(Carta... carta) {
        if (this.carteNelMazzo == null) {

            // Initialize the list if null
            this.carteNelMazzo = new ArrayList<>();
        }
        for (Carta c : carta) {
            this.carteNelMazzo.add(c);
        }
    }

    public void rimuoviCartaDalMazzo(Carta carta) {

        try {
            this.carteNelMazzo.remove(carta);
        } catch (Exception e) {
            System.err.println("ERRORE (rimuoviCartaDalMazzo):\t\t " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void aggiungiListaCarteAdAltroMazzo(List<Carta> mano) {
        if (this.carteNelMazzo == null) {
            this.carteNelMazzo = new ArrayList<>(); // Initialize the list if null
            this.carteNelMazzo.addAll(mano);
        }
        this.carteNelMazzo.addAll(mano);
    }

    public int size() {
        return this.carteNelMazzo.size();
    }

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

}