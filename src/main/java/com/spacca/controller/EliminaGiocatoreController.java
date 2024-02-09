package com.spacca.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.spacca.App;
import com.spacca.asset.utente.Amministratore;
import com.spacca.database.GiocatoreHandler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class EliminaGiocatoreController implements Initializable {

    @FXML
    private ComboBox<String> listaUtenti;

    @FXML
    private Button indietro, procedi;

    @FXML
    private String nomeFileGiocatoreScelto;

    transient private Amministratore admin = new Amministratore();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            List<String> fileNames = new GiocatoreHandler().mostraTutteGliUtenti();
            // Popola il ComboBox con la lista dei nomi dei file
            listaUtenti.getItems().addAll(fileNames);

            listaUtenti.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                // Salva il valore selezionato nella variabile globale
                this.nomeFileGiocatoreScelto = newValue;
            });

            if (fileNames.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("");
                alert.setHeaderText("Al momento non sono presenti giocatori.");
                alert.setContentText("Non puoi eliminare alcun giocatore.");
                alert.showAndWait();
                App.setRoot("benvenutoAdmin");
            }
            listaUtenti.setVisibleRowCount(3);

        } catch (NullPointerException e) {
            e.printStackTrace();
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
        if (nomeFileGiocatoreScelto == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.setContentText("Non puoi procedere alla modifica \n se non selezioni alcun giocatore! ");
            alert.showAndWait();
        } else {
            try {
                admin.eliminaGiocatore(nomeFileGiocatoreScelto);
            } catch (NullPointerException e) {
                // Gestione specifica per la NullPointerException
                System.err.println("NullPointerException: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println("Exception: " + e.getMessage());
                e.printStackTrace();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.setContentText("Giocatore " + nomeFileGiocatoreScelto + " eliminata correttamente! ");
            alert.showAndWait();
            handleIndietro();
        }
    }
}
