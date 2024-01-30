package com.spacca.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.spacca.asset.utente.Amministratore;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import java.nio.file.*;

public class SelezionaPartitaController implements Initializable {

    @FXML
    private AbstractGiocatore giocatoreCorrente;
    @FXML
    private ChoiceBox<String> listaUtenti;

    @FXML
    private Button indietro;

    @FXML
    private Button procedi;

    @FXML
    private String nomeFilePartitaScelto;

    transient private Amministratore admin = new Amministratore();

    @FXML
    public void initController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            String folderPath = "/com/spacca/database/partite/";

            // Ottieni il percorso completo della cartella delle risorse
            Path resourceFolder = Paths.get(getClass().getResource(folderPath).toURI());

            // Ottieni la lista dei nomi dei file JSON presenti nella cartella
            List<String> fileNames = Files.walk(resourceFolder, 1)
                    .filter(path -> !path.getFileName().toString().equals("template-partita.json"))
                    .filter(path -> path.toString().endsWith(".json") && Files.isRegularFile(path))
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());

            // Rimuovi "user-" e ".json" dai nomi dei file
            List<String> modifiedFileNames = fileNames.stream()
                    .map(fileName -> fileName.replace(".json", ""))
                    .collect(Collectors.toList());

            // Popola il ChoiceBox con la lista dei nomi dei file
            listaUtenti.getItems().addAll(modifiedFileNames);

            listaUtenti.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("Partita selezionata: " + newValue);
                // Salva il valore selezionato nella variabile globale
                this.nomeFilePartitaScelto = newValue;
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleIndietro() throws IOException {
        admin.ritornaBenvenutoAdmin(indietro.getScene());
    }

    public void handleProcedi() throws IOException {
        System.out.println("Siamo in procedi all'eliminazione della partita " + nomeFilePartitaScelto);
        if (nomeFilePartitaScelto == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.setContentText("Non puoi procedere alla modifica \n se non selezioni alcuna partita ! ");
            alert.showAndWait();
        } else {
            System.out.println("Parita selezionata " + nomeFilePartitaScelto);

            System.out.print("nomeFilePartitaScelto" + nomeFilePartitaScelto);
            admin.eliminaPartita(nomeFilePartitaScelto);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.setContentText("Partita " + nomeFilePartitaScelto + " eliminata correttamente ! ");
            alert.showAndWait();
            handleIndietro();
        }
    }
}
