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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ModPartitaController implements Initializable {

    List<AbstractGiocatore> giocatoriDellaPartita = new ArrayList<>(); // Inizializzazione della lista
    AbstractGiocatore giocatoreCorrente;
    PartitaController partitaController;

    @FXML
    private VBox buttonContainer;

    // Lista degli utenti
    // private List<String> userList = new ArrayList<>();

    @FXML
    private CheckBox singolaScelta;

    @FXML
    private CheckBox torneoScelta;

    @FXML
    private Button avvioButton;

    // @FXML
    // private ChoiceBox menuTendina;

    @FXML
    private Button indietro;

    private Stage popupStage; // Riferimento allo Stage del popup

    @FXML
    private void handleAvviaButton() {

        // Chiudi lo stage principale
        indietro.getScene().getWindow().getScene().getWindow().hide();

        try {
            if (singolaScelta.isSelected()) {
                System.out.println("Scelta singola selezionata");
                // devo andare a prendere il file con il codice della
                // partita selezionato nel menu a tendina dall'utente
            } else if (torneoScelta.isSelected()) {
                System.out.println("Scelta torneo selezionata");
                String fxmlPath = "/com/spacca/pages/partita.fxml";
                FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
                Parent root = loader.load();

                // Logica per inizializzare il controller se necessario
                // PartitaController partitaController = loader.getController();
                // loader.setController(partitaController);

                Stage mainStage = App.getStage();

                if (mainStage != null) {
                    // Carica la schermata Partita nel riquadro della finestra principale
                    mainStage.setScene(new Scene(root));
                    mainStage.show();
                }
                // App.setRoot(fxmlPath);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText(null);
                alert.setContentText("Per avviare una partita devi per forza selezionare un'opzione");
                alert.showAndWait();
            }
            changeScene();
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
        Scene currentScene = indietro.getScene();

        // Ottieni lo Stage dalla scena corrente
        Stage currentStage = (Stage) currentScene.getWindow();

        // Imposta la nuova scena sulla finestra di scena corrente
        currentStage.setScene(new Scene(root));
        currentStage.show();
    }

    // private void popolaChoiceBoxConUtenti() {
    /*
     * // Imposta il percorso della cartella degli utenti
     * String folderPath = "src/main/resources/com/spacca/database/giocatori/";
     * 
     * // Ottieni la lista di file nella cartella
     * File folder = new File(folderPath);
     * File[] files = folder.listFiles();
     * 
     * if (files != null) {
     * List<String> nomiUtenti = new ArrayList<>();
     * 
     * // Filtra e leggi i file JSON
     * for (File file : files) {
     * if (file.isFile() && file.getName().endsWith(".json")) {
     * try {
     * // Leggi il giocatore dal file
     * FileHandler fileHandler = new FileHandler();
     * Giocatore giocatore = fileHandler.leggiUtente(file);
     * 
     * // Aggiungi il nome utente alla lista
     * nomiUtenti.add(giocatore.getUsername());
     * } catch (Exception e) {
     * e.printStackTrace();
     * // Gestisci eventuali errori di lettura
     * }
     * }
     * }
     * 
     * // Popola il ChoiceBox con la lista di nomi utenti
     * listaCodici.getItems().addAll(nomiUtenti);
     * }
     */
    // }

    @FXML
    private void handleIndietroButton() {
        indietro.getScene().getWindow().getScene().getWindow().hide();
    }

    @FXML
    private void handleCheckBoxAction(MouseEvent event) {
        if (event.getSource() instanceof CheckBox) {
            CheckBox clickedCheckBox = (CheckBox) event.getSource();
            if (clickedCheckBox.isSelected()) {
                if (clickedCheckBox == singolaScelta) {
                    torneoScelta.setSelected(false);
                } else if (clickedCheckBox == torneoScelta) {
                    singolaScelta.setSelected(false);
                }
            }
        }
    }

    public void initController(AbstractGiocatore giocatoreCorrente) {

        this.giocatoreCorrente = giocatoreCorrente;
        popupStage = new Stage();
        popupStage.setTitle("Modalità partita");

        Image icon = new Image(getClass().getResourceAsStream("/com/spacca/images/icone/logo.jpg"));
        popupStage.getIcons().add(icon);

        // popolaChoiceBoxConUtenti();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
