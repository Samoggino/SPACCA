package com.spacca.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.spacca.App;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.asset.utente.giocatore.Giocatore;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ModPartitaController implements Initializable {

    List<AbstractGiocatore> giocatoriDellaPartita = new ArrayList<>(); // Inizializzazione della lista
    AbstractGiocatore giocatoreCorrente;
    PartitaController partitaController;

    @FXML
    private VBox buttonContainer;

    @FXML
    private CheckBox singolaScelta;

    @FXML
    private CheckBox torneoScelta;

    @FXML
    private Button avvioButton;

    @FXML
    private ComboBox<String> listaCodici;

    @FXML
    private Button indietroButton;

    @FXML
    private Label labelSelezione;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            labelSelezione.setVisible(false);
            listaCodici.setVisible(false);
            avvioButton.setDisable(true);
        } catch (NullPointerException e) {
            System.out.println("Elementi grafici null " + e);
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Errore nell'init del modPartitaController " + e.getLocalizedMessage());
        }

    }

    @FXML
    private void handleAvviaButton() {
        try {
            if (singolaScelta.isSelected()) {
                labelSelezione.setText("Selezione il codice della parita alla quale vuoi giocare : ");
                System.out.println("Scelta singola selezionata");
                // devo andare a prendere il file con il codice della
                // partita selezionato nel menu a tendina dall'utente
            } else if (torneoScelta.isSelected()) {
                labelSelezione.setText("Selezione il codice del torneo a cui vuoi giocare : ");
                System.out.println("Scelta torneo selezionata");
                changeScene();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText(null);
                alert.setContentText("Per avviare una partita devi per forza selezionare un'opzione");
                alert.showAndWait();
            }
        } catch (IOException e) {
            System.err.println("Errore durante il caricamento di partita.fxml: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void changeScene() throws IOException {
        // Carica il nuovo layout FXML per il PartitaController
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/spacca/pages/partita.fxml"));
        Parent root = loader.load();
        // Logica per inizializzare il controller se necessario
        PartitaController partitaController = loader.getController();
        loader.setController(partitaController);

        // non è il punto corretto ma almeno non dà errore
        AbstractGiocatore YOSHI = new Giocatore("Yoshi", "", "");
        AbstractGiocatore MARIO = new Giocatore("Mario", "", "");
        AbstractGiocatore LUIGI = new Giocatore("Luigi", "", "");

        giocatoriDellaPartita.add(giocatoreCorrente);
        giocatoriDellaPartita.add(YOSHI);
        giocatoriDellaPartita.add(MARIO);
        giocatoriDellaPartita.add(LUIGI);

        partitaController.initController(giocatoreCorrente, giocatoriDellaPartita);

        // Ottieni la scena corrente
        Scene currentScene = indietroButton.getScene();

        // Ottieni lo Stage dalla scena corrente
        Stage currentStage = (Stage) currentScene.getWindow();

        // Imposta la nuova scena sulla finestra di scena corrente
        currentStage.setScene(new Scene(root));
        currentStage.show();
    }

    @FXML
    private void handleIndietroButton() {
        try {
            changeSceneUtente(("Benvenuto " + giocatoreCorrente.getUsername() + " !"),
                    "/com/spacca/pages/benvenutoUtente.fxml",
                    giocatoreCorrente);

        } catch (NullPointerException e) {
            System.out.println("Erorre nel passaggio del giocatore ! " + e.getLocalizedMessage());
        } catch (Exception e) {
            System.err.println("Errore (changeScene mod partita): \n" + e.getMessage());
        }

    }

    public void changeSceneUtente(String titolo, String fxmlPath, Object controllerData) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Parent root = loader.load();
            // Logica per inizializzare il controller se necessario
            BenvenutoUtenteController prePartita = loader.getController();
            loader.setController(prePartita);
            prePartita.initController((Giocatore) controllerData);

            Scene currentScene = indietroButton.getScene();

            // Ottieni lo Stage dalla scena corrente
            Stage currentStage = (Stage) currentScene.getWindow();

            currentStage.setTitle(titolo);
            currentStage.setScene(new Scene(root));
            currentStage.show();
        } catch (NullPointerException e) {
            System.out.println("mmod partita non trovato !");
        } catch (IOException e) {
            System.err.println("Errore (changeScene mod partita): \n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore (changeScene mod partita): \n" + e.getMessage());
        }
    }

    @FXML
    private void handleCheckBoxAction(MouseEvent event) {
        if (event.getSource() instanceof CheckBox) {
            CheckBox clickedComboBox = (CheckBox) event.getSource();
            if (clickedComboBox.isSelected()) {
                labelSelezione.setVisible(true);
                listaCodici.setVisible(true);
                if (clickedComboBox == singolaScelta) {
                    labelSelezione.setText("Seleziona il codice della  partita da giocare : ");
                    torneoScelta.setSelected(false);
                } else if (clickedComboBox == torneoScelta) {
                    labelSelezione.setText("Seleziona il codice del torneo da giocare : ");
                    singolaScelta.setSelected(false);
                }
            }
        } else {

        }
    }

    public void initController(AbstractGiocatore giocatoreCorrente) {

        this.giocatoreCorrente = giocatoreCorrente;
        // TOO-DOO popolaChoiceBoxConUtenti();
    }

}
