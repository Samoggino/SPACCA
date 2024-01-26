package com.spacca.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.spacca.App;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.database.PartitaHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

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

    @FXML
    public void initController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            String folderPath = "/com/spacca/database/partite";

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
                    .map(fileName -> fileName.replace("P", "").replace(".json", ""))
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

    public void handleIndietro() {
        changeScene("/com/spacca/pages/benvenutoAdmin.fxml");
    }

    public void handleProcedi() {
        System.out.println("Siamo in procedi all'eliminazione della partita " + nomeFilePartitaScelto);
        if (nomeFilePartitaScelto == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.setContentText("Non puoi procedere alla modifica \n se non selezioni alcuna partita ! ");
            alert.showAndWait();
        } else {
            System.out.println("Parita selezionata " + nomeFilePartitaScelto);

            PartitaHandler partitaHandler = new PartitaHandler();
            partitaHandler.elimina(nomeFilePartitaScelto);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.setContentText("Partita " + nomeFilePartitaScelto + " eliminata correttamente ! ");
            alert.showAndWait();
            handleIndietro();
        }
    }

    public void changeScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Parent root = loader.load();

            Scene currentScene = procedi.getScene();

            // Ottieni lo Stage dalla scena corrente
            Stage currentStage = (Stage) currentScene.getWindow();

            currentStage.setTitle("Benvenuto Admin ! ");
            currentStage.setScene(new Scene(root));
            currentStage.show();

        } catch (NullPointerException e) {
            System.out.println("Login avvenuto con successo!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IO Errore (Benvenuto Admin controller): \n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore (Benvenuto Admin controller): \n" + e.getMessage());
        }
    }

}
