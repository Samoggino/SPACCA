package com.spacca.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.spacca.App;
import com.spacca.asset.utente.Amministratore;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.database.TorneoHandler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class EliminaTorneoController implements Initializable {

    @FXML
    private AbstractGiocatore giocatoreCorrente;
    @FXML
    private ComboBox<String> listaUtenti;

    @FXML
    private Button indietro;

    @FXML
    private Button procedi;

    @FXML
    private String nomeFileTorneoScelto;

    private Amministratore admin = new Amministratore();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Ottieni la lista dei nomi dei file JSON presenti nella cartella
            List<String> fileNames = new TorneoHandler().mostraTuttiITornei();

            // Rimuovi "user-" e ".json" dai nomi dei file
            List<String> modifiedFileNames = fileNames;
            // fileNames.stream()
            // .map(fileName -> fileName.replace(".json", ""))
            // .collect(Collectors.toList());

            // Popola il ComboBox con la lista dei nomi dei file
            listaUtenti.getItems().addAll(modifiedFileNames);

            listaUtenti.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("Torneo selezionato: " + newValue);
                // Salva il valore selezionato nella variabile globale
                this.nomeFileTorneoScelto = newValue;
            });

            if (modifiedFileNames.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("");
                alert.setHeaderText("Al momento non sono presenti partite.");
                alert.setContentText("Non puoi eliminare alcuna partita.");
                alert.showAndWait();
                App.setRoot("benvenutoAdmin");
            }

            listaUtenti.setVisibleRowCount(3);

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
        System.out.println("Siamo in procedi all'eliminazione della partita " + nomeFileTorneoScelto);
        if (nomeFileTorneoScelto == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.setContentText("Non puoi procedere alla modifica \n se non selezioni alcuna partita ! ");
            alert.showAndWait();
        } else {
            System.out.println("Torneo selezionato " + nomeFileTorneoScelto);

            System.out.print("nomeFileTorneoScelto" + nomeFileTorneoScelto);

            admin.eliminaTorneo(nomeFileTorneoScelto);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.setContentText("Torneo " + nomeFileTorneoScelto + " eliminato correttamente ! ");
            alert.showAndWait();
            handleIndietro();
        }
    }
}
