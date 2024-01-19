package com.spacca.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.spacca.App;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.asset.utente.giocatore.Giocatore;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class BenvenutoAdminController {

    @FXML
    private Button bottoneCreaSingola;
    @FXML
    private Button bottoneCreaTorneo;

    @FXML
    private Button bottoneCreaUtente;

    @FXML
    private Button bottoneEliminaSingola;

    @FXML
    private Button bottoneModificaUtente;

    @FXML
    private Button bottoneEliminaTorneo;

    List<AbstractGiocatore> giocatoriDellaPartita = new ArrayList<>(); // Inizializzazione della lista
    AbstractGiocatore giocatoreCorrente;
    PartitaController partitaController;

    // Lista degli utenti
    private List<String> userList = new ArrayList<>();

    @FXML
    private void initialize() throws IOException {
        // Imposta il titolo della finestra principale
        App.getStage().setTitle("Benvenuto");
    }

    public void initController(AbstractGiocatore giocatoreCorrente) {

        try {
            this.giocatoreCorrente = giocatoreCorrente;

            /*
             * for (AbstractGiocatore giocatore : giocatoriDellaPartita) {
             * userList.add(giocatore.getUsername());
             * }
             */

        } catch (Exception e) {
            System.err.println("ERRORE (pre):\t\t " + e.getMessage());
        }
    }

}
