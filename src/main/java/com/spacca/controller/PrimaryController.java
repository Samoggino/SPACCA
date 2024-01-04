package com.spacca.controller;

import java.io.IOException;

import com.google.gson.Gson;
import com.spacca.asset.Carta;
import com.spacca.asset.carte.Seme;
import com.spacca.asset.carte.Nome;

import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        // App.setRoot("secondary");
    }

    @FXML
    void provaConCarte() {
        // Esempio di utilizzo
        Carta carta = new Carta(Seme.BASTONI, Nome.ASSO);
        carta.stampa();

        // Serializzazione in JSON usando GSON
        Gson gson = new Gson();
        String cartaJson = gson.toJson(carta);
        System.out.println("Carta serializzata in JSON: " + cartaJson);

        // Deserializzazione da JSON
        Carta cartaDeserializzata = gson.fromJson(cartaJson, Carta.class);
        cartaDeserializzata.stampa();
    }
}
