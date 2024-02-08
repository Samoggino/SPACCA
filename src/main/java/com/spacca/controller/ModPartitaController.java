package com.spacca.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.ResourceBundle;

import com.spacca.App;
import com.spacca.asset.match.Partita;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.asset.utente.giocatore.Giocatore;
import com.spacca.database.GiocatoreHandler;
import com.spacca.database.PartitaHandler;

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ModPartitaController implements Initializable {

    List<AbstractGiocatore> giocatoriDellaPartita = new ArrayList<>(); // Inizializzazione della lista

    AbstractGiocatore giocatoreCorrente;

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

    @FXML
    private String codicePartita;

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
            PartitaHandler partitaHandler = new PartitaHandler();
            Partita partita = null;

            if (listaCodici.getValue() == null) {
                avvioButton.setDisable(true);
                if (listaCodici.getItems().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Non ci sono partite da selezionare",
                            "Ci dispiaca, ma non possiedi alcun codice per giocare !");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Errore",
                            "Non hai selezionato alcun codice");
                }
            } else {
                avvioButton.setDisable(false);
                this.codicePartita = listaCodici.getValue();

                if (singolaScelta.isSelected()) {
                    labelSelezione.setText("Selezione il codice della parita alla quale vuoi giocare : ");
                    System.out.println("Scelta singola selezionata");
                    partita = partitaHandler.carica(codicePartita);
                    // isTorneo false
                    changeScene(partita, false);

                } else if (torneoScelta.isSelected()) {
                    labelSelezione.setText("Selezione il codice del torneo a cui vuoi giocare : ");
                    System.out.println("Scelta torneo selezionata");

                    // ricerco nella lista dei codici della partita il torneo selezionato
                    // dall'utente
                    for (String codice : giocatoreCorrente.getListaCodiciPartite()) {
                        if (codice.contains(codicePartita)) {
                            codicePartita = codice; // Codice della partita associata al torneo selezionato
                            break; // Esci dal ciclo una volta trovata la partita
                        }
                    }
                    partita = partitaHandler.carica(codicePartita);
                    // isTorneo true
                    changeScene(partita, true);
                }
            }
        } catch (IOException e) {
            System.err.println("Errore durante il caricamento di partita.fxml: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(AlertType tipo, String titolo, String contenuto) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(contenuto);
        alert.showAndWait();
    }

    private void changeScene(Partita partita, Boolean tipoPartita) throws IOException {
        try {

            FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/spacca/pages/tavolo.fxml"));
            Parent root = loader.load();
            TavoloController tavolo = loader.getController();
            loader.setController(tavolo);
            tavolo.initController(new PartitaHandler().carica(partita.getCodice()), tipoPartita, giocatoreCorrente);

            // Ottieni la scena corrente
            Scene currentScene = indietroButton.getScene();

            // Ottieni lo Stage dalla scena corrente
            Stage currentStage = (Stage) currentScene.getWindow();

            // Imposta la nuova scena sulla finestra di scena corrente
            currentStage.setScene(new Scene(root));

            listaCodici.getItems().clear();

            currentStage.show();
        } catch (IOException e) {
            System.err.println("Errore durante il caricamento del file FXML: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println("Errore di stato illegale: " + e.getMessage());
        } catch (ClassCastException e) {
            System.err.println("Errore di cast: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore generico: " + e.getMessage());
        }

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

            listaCodici.getItems().clear();
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
    private void handleCheckBoxAction(MouseEvent event) throws IOException {
        if (event.getSource() instanceof CheckBox) {
            CheckBox clickedComboBox = (CheckBox) event.getSource();
            if (clickedComboBox.isSelected()) {
                labelSelezione.setVisible(true);
                listaCodici.setVisible(true);
                listaCodici.getItems().clear();
                avvioButton.setDisable(false);
                // inizializza
                if (clickedComboBox == singolaScelta) {
                    labelSelezione.setText("Seleziona il codice della  partita da giocare : ");
                    torneoScelta.setSelected(false);
                    // popola il combox con i codici delle partite singole
                    popolaListaPartite();
                } else if (clickedComboBox == torneoScelta) {
                    labelSelezione.setText("Seleziona il codice del torneo da giocare : ");
                    singolaScelta.setSelected(false);

                    avvioButton.setDisable(false);
                    // popola il combox con i codici del torneo
                    popolaListaTorneo();
                }
            } else {
                labelSelezione.setVisible(false);
                listaCodici.setVisible(false);
                avvioButton.setDisable(true);
            }
        }
    }

    private void popolaListaTorneo() throws IOException {
        try {
            // devo filtrare togliendo le partite non del torneo
            List<String> listaPartite = new GiocatoreHandler().carica(giocatoreCorrente.getUsername())
                    .getListaCodiciTornei();
            listaCodici.getItems().addAll(listaPartite);

        } catch (NullPointerException e) {
            System.err.println("" + e);
            e.printStackTrace();
        } catch (ConcurrentModificationException e) {
            System.err.println("Errore nella modifica corrente della lista " + e);
            e.printStackTrace();

        } catch (Exception e) {
            System.err.println("" + e);
            e.printStackTrace();
        }
    }

    private void popolaListaPartite() throws IOException {
        try {
            // devo filtrare togliendo le partite del torneo
            List<String> listaPartite = new ArrayList<>();
            for (String partita : new GiocatoreHandler()
                    .carica(giocatoreCorrente.getUsername())
                    .getListaCodiciPartite()) {
                if (!partita.contains("tornei/")) {
                    listaPartite.add(partita);
                }
            }
            listaCodici.getItems().addAll(listaPartite);

        } catch (NullPointerException e) {
            System.err.println("" + e);
            e.printStackTrace();
        } catch (ConcurrentModificationException e) {
            System.err.println("Errore nella modifica corrente della lista " + e);
            e.printStackTrace();

        } catch (Exception e) {
            System.err.println("" + e);
            e.printStackTrace();
        }
    }

    public void initController(AbstractGiocatore giocatoreCorrente) {
        try {
            this.giocatoreCorrente = giocatoreCorrente;
        } catch (NullPointerException e) {
            System.err.println("Il giocatore corrente passato è nullo " + e);
            e.printStackTrace();
        }
    }

}