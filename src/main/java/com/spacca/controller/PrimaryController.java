package com.spacca.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.spacca.asset.carte.Carta;
import com.spacca.asset.carte.Mazzo;

import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        // App.setRoot("secondary");
    }

    @FXML
    void provaConCarte() {
        try {
            // leggo il file JSON
            Reader fileReader = new FileReader("src/main/java/com/spacca/database/carte.json");
            Gson gson = new Gson();
            // gson prende il fileReader e lo converte in un array di carte
            Carta[] carte = gson.fromJson(fileReader, Carta[].class);

            // Converti l'array di carte in un ArrayList di carte
            List<Carta> listaCarte = new ArrayList<>(Arrays.asList(carte));

            // Ora puoi utilizzare l'ArrayList di Carta come desideri
            Mazzo mazzo = new Mazzo(listaCarte);

            System.out.println(mazzo);

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

    }
}
