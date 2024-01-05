package com.spacca.controller;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import com.google.gson.Gson;
import com.spacca.asset.Carta;

import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        // App.setRoot("secondary");
    }

    @FXML
    void provaConCarte() {
        // Esempio di utilizzo
        // Carta carta = new Carta(Seme.BASTONI, Nome.FANTE);
        // carta.stampa();

        // // Serializzazione in JSON usando GSON
        // Gson gson = new Gson();
        // String cartaJson = gson.toJson(carta);
        // System.out.println("Carta serializzata in JSON: " + cartaJson);

        // // Deserializzazione da JSON
        // Carta cartaDeserializzata = gson.fromJson(cartaJson, Carta.class);
        // cartaDeserializzata.stampa();

        // stampa da json di una carta
        // Gson gson = new Gson();
        try {
            Reader fileReader = new FileReader("src/main/java/com/spacca/database/carte.json");
            Gson gson = new Gson();
            Carta[] carte = gson.fromJson(fileReader, Carta[].class);
            for (Carta carta : carte) {
                System.out.println(carta);
            }
        } catch (Exception e) {
            System.err.println("ERRORE: Errore durante la lettura del file JSON");
            e.printStackTrace();
        }

    }
}
