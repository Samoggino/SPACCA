package com.spacca.controller;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.asset.utente.giocatore.Giocatore;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.nio.file.*;

public class SelezionaUenteController implements Initializable {

    @FXML
    private AbstractGiocatore giocatoreCorrente;
    @FXML
    private ChoiceBox<String> listaUtenti;

    private String nomeFileUtenteScelto;

    @FXML
    public void initController(Giocatore utenteCorrente) {
        try {
            this.giocatoreCorrente = utenteCorrente;
        } catch (NullPointerException e) {
            System.err.println("ERRORE NullPointerException:\t\t " + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERRORE eccezione:\t\t " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            String folderPath = "/com/spacca/database/giocatori";

            // Ottieni il percorso completo della cartella delle risorse
            Path resourceFolder = Paths.get(getClass().getResource(folderPath).toURI());

            // Ottieni la lista dei nomi dei file JSON presenti nella cartella
            List<String> fileNames = Files.walk(resourceFolder, 1)
                    .filter(path -> path.toString().endsWith(".json") && Files.isRegularFile(path))
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());

            // Popola il ChoiceBox con la lista dei nomi dei file
            listaUtenti.getItems().addAll(fileNames);

            listaUtenti.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("Utente selezionato: " + newValue);
                // Salva il valore selezionato nella variabile globale
                this.nomeFileUtenteScelto = newValue;
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Aggiungi un metodo per ottenere il nome del file utente selezionato
    public String getNomeFileUtenteScelto() {
        return nomeFileUtenteScelto;
    }
}
