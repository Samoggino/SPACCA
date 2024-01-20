package com.spacca.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.asset.utente.giocatore.Giocatore;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

public class SelezionaUenteController implements Initializable {

    private AbstractGiocatore giocatoreCorrente;

    @FXML
    private ChoiceBox<String> listalistaUtenti;

    public void initController(Giocatore giocatoreCorrente) {
        try {
            this.giocatoreCorrente = giocatoreCorrente;

        } catch (NullPointerException e) {
            System.err.println("ERRORE NullPointerException:\t\t " + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERRORE eccezione:\t\t " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Implementa l'inizializzazione qui, se necessario
    }
}
