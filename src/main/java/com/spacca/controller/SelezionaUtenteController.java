package com.spacca.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.spacca.App;
import com.spacca.asset.utente.Amministratore;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.database.GiocatoreHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class SelezionaUtenteController implements Initializable {
    transient private Amministratore admin = new Amministratore();

    @FXML
    private AbstractGiocatore giocatoreCorrente;
    @FXML
    private ChoiceBox<String> listaUtenti;

    @FXML
    private Button indietro, procedi;

    @FXML
    private String username;

    @FXML
    public void initController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        GiocatoreHandler handler = new GiocatoreHandler();

        // Popola il ChoiceBox con la lista dei nomi dei file con i robot
        listaUtenti.getItems().addAll(handler.getAllGiocatoriUtenti());

        listaUtenti.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    System.out.println("Utente selezionato: " + newValue);
                    // Salva il valore selezionato nella variabile globale
                    this.username = newValue;
                });
    }

    public void handleIndietro() {
        admin.ritornaBenvenutoAdmin(indietro.getScene());
    }

    public void handleProcedi() {
        System.out.println("Siamo in procedi della selezione utente" + username);
        if (username == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.setContentText("Non puoi procedere alla modifica \n se non selezioni alcun utente ! ");
            alert.showAndWait();
        } else {
            changeSceneModificaUTente(username);
        }
    }

    private void changeSceneModificaUTente(String username) {
        try {

            // Carica il nuovo layout FXML per il PartitaController
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/spacca/pages/modificaUtente.fxml"));
            Parent root = loader.load();

            ModificaUtenteController modificaUtenteController = loader.getController();
            loader.setController(modificaUtenteController);

            modificaUtenteController.initController(username);

            // Ottieni la scena corrente
            Scene currentScene = procedi.getScene();

            // Ottieni lo Stage dalla scena corrente
            Stage currentStage = (Stage) currentScene.getWindow();

            currentStage.setTitle("Modifica utente " + username);
            currentStage.setScene(new Scene(root));
            currentStage.show();

        } catch (NullPointerException e) {
            System.out.println("NullpointerException in selezione utente!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IO Errore (Selezione utente controller): \n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore (Selezione utente controller): \n" + e.getMessage());
            e.printStackTrace();
        }
    }

}
