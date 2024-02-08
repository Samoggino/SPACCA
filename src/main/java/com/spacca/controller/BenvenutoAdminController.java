package com.spacca.controller;

import java.io.IOException;

import com.spacca.App;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.database.GiocatoreHandler;
import com.spacca.database.PartitaHandler;
import com.spacca.database.TorneoHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class BenvenutoAdminController {

    @FXML
    private Button bottoneCreaSingola, bottoneCreaTorneo, bottoneCreaUtente, bottoneEliminaSingola,
            bottoneModificaUtente, bottoneEliminaTorneo, bottoneModPartita;

    @FXML
    public AbstractGiocatore amministratore;

    @FXML
    private void initialize() throws IOException {
        GiocatoreHandler giocatoreHandler = new GiocatoreHandler();
        amministratore = giocatoreHandler.carica("admin");
    }

    public void initController() {
    }

    @FXML
    private void handleModificaUtente() {
        System.out.println("Siamo in Modifica utente");
        String titlePages = "Modifica utente ";
        // non mando un alert perchè c'è sempre almeno un giocatore (l'admin)
        changeSceneModificaUtente("/com/spacca/pages/selezionaUtente.fxml", titlePages);
    }

    @FXML
    private void handleElimnaPartita() throws IOException, ClassNotFoundException {
        try {
            System.out.println("Siamo in Elimina partita" + amministratore);
            PartitaHandler partitaHandler = new PartitaHandler();
            if (partitaHandler.getAllPartite().isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "Non ci sono partite da selezionare",
                        "Di conseguenza, non è possibile eliminare alcuna partita");
            } else {
                String titlePages = "Elimina partita ";
                changeSceneEliminaPartita("/com/spacca/pages/eliminaPartita.fxml", titlePages);
            }
        } catch (NullPointerException e) {
            System.err.println("Eccezione NullPointerException: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Eccezione generica: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleElimnaTorneo() {
        System.out.println("Siamo in Elimina Torneo" + amministratore);
        String titlePages = "Elimina Torneo ";
        TorneoHandler torneoHandler = new TorneoHandler();
        if (torneoHandler.mostraTuttiITornei().isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Non ci sono tornei da selezionare",
                    "Di conseguenza, non è possibile eliminare alcun torneo");
        } else {
            changeSceneEliminaTorneo("/com/spacca/pages/eliminaTorneo.fxml", titlePages);
        }
    }

    private void changeSceneEliminaTorneo(String fxmlPath, String titlePages) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Parent root = loader.load();

            Scene currentScene = bottoneModificaUtente.getScene();

            EliminaTorneoController selezionaUenteController = loader.getController();
            loader.setController(selezionaUenteController);

            // Ottieni lo Stage dalla scena corrente
            Stage currentStage = (Stage) currentScene.getWindow();
            currentStage.setTitle(titlePages);
            currentStage.setScene(new Scene(root));
            currentStage.show();

        } catch (NullPointerException e) {
            System.out.println("Elimina partita nullpointerexception! " + e);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IO Errore (Benvenuto Admin controller per elimina partita): \n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore (Benvenuto Admin controller elimina partita): \n" + e.getMessage());
        }
    }

    @FXML
    private void handleCreazioneUtente() {
        System.out.println("Siamo in Creazione utente");
        String titlePages = "Creazione nuovo utente ";
        changeSceneCreazioneUtente("/com/spacca/pages/modCreazioneUtente.fxml", titlePages);
    }

    @FXML
    private void handleCreazionePartita() {
        System.out.println("Siamo in creazione partita");
        String titlePages = "Creazione nuova partita ";
        changeSceneCreazionePartita("/com/spacca/pages/creazionePartita.fxml", titlePages);

    }

    @FXML
    private void handleCreazioneTorneo() {
        System.out.println("Siamo in creazione torneo");
        String titlePages = "Creazione nuovo torneo ";
        changeSceneCreazioneTorneo("/com/spacca/pages/creazioneTorneo.fxml", titlePages);

    }

    @FXML
    private void gioca() throws ClassNotFoundException {
        try {
            GiocatoreHandler giocatoreHandler = new GiocatoreHandler();
            AbstractGiocatore giocatore = giocatoreHandler.carica("admin");
            if (giocatore.getListaCodiciPartite().isEmpty() && giocatore.getListaCodiciTornei().isEmpty()) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setContentText(
                        "\n Siamo spiacenti, ma non sei stato inserito in alcuna partita. \n Pertanto, non hai la possibilità di giocare in questo momento \n");
                alert.showAndWait();
            } else {
                System.out.println("Siamo in creazione singola");
                changeSceneGioca();
            }
        } catch (NullPointerException e) {
            System.err.println("Giocatore caricato null: " + e.getLocalizedMessage() + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Errore: " + e.getLocalizedMessage() + e.getMessage());
            e.printStackTrace();
        }
    }

    public void changeSceneCreazioneTorneo(String fxmlPath, String titlePages) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Parent root = loader.load();

            Scene currentScene = bottoneModificaUtente.getScene();

            CreazioneTorneoController creazionePartitaController = loader.getController();
            loader.setController(creazionePartitaController);

            // Ottieni lo Stage dalla scena corrente

            Stage currentStage = (Stage) currentScene.getWindow();
            currentStage.setTitle(titlePages);
            Scene scene = new Scene(root, 700, 550);
            currentStage.setScene(scene);
            currentStage.show();
        } catch (NullPointerException e) {
            System.out.println("creazione partita nullpointerException " + e);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IO Errore (Benvenuto Admin controller per creazione partita): \n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore (Benvenuto Admin controller): \n" + e.getMessage());
        }
    }

    public void changeSceneCreazionePartita(String fxmlPath, String titlePages) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Parent root = loader.load();

            Scene currentScene = bottoneModificaUtente.getScene();

            CreazionePartitaController creazionePartitaController = loader.getController();
            loader.setController(creazionePartitaController);

            // Ottieni lo Stage dalla scena corrente

            Stage currentStage = (Stage) currentScene.getWindow();
            currentStage.setTitle(titlePages);
            Scene scene = new Scene(root, 700, 550);
            currentStage.setScene(scene);
            currentStage.show();
        } catch (NullPointerException e) {
            System.out.println("creazione partita nullpointerException " + e);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IO Errore (Benvenuto Admin controller per creazione partita): \n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore (Benvenuto Admin controller): \n" + e.getMessage());
        }
    }

    public void changeSceneModificaUtente(String fxmlPath, String titlePages) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Parent root = loader.load();

            Scene currentScene = bottoneModificaUtente.getScene();

            SelezionaUtenteController selezionaUenteController = loader.getController();
            loader.setController(selezionaUenteController);

            // Ottieni lo Stage dalla scena corrente
            Stage currentStage = (Stage) currentScene.getWindow();
            currentStage.setTitle(titlePages);
            currentStage.setScene(new Scene(root));
            currentStage.show();

        } catch (NullPointerException e) {
            System.out.println("Modifica utente nullpointerException" + e);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IO Errore (Benvenuto Admin controller per modifica utente): \n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore (Benvenuto Admin controller modifica utetne): \n" + e.getMessage());
        }
    }

    public void changeSceneEliminaPartita(String fxmlPath, String titlePages) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Parent root = loader.load();

            Scene currentScene = bottoneModificaUtente.getScene();

            EliminaPartitaController selezionaUenteController = loader.getController();
            loader.setController(selezionaUenteController);

            // Ottieni lo Stage dalla scena corrente
            Stage currentStage = (Stage) currentScene.getWindow();
            currentStage.setTitle(titlePages);
            currentStage.setScene(new Scene(root));
            currentStage.show();

        } catch (NullPointerException e) {
            System.out.println("Elimina partita nullpointerexception! " + e);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IO Errore (Benvenuto Admin controller per elimina partita): \n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore (Benvenuto Admin controller elimina partita): \n" + e.getMessage());
        }
    }

    public void changeSceneCreazioneUtente(String fxmlPath, String titlePages) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Parent root = loader.load();

            Scene currentScene = bottoneModificaUtente.getScene();

            // Ottieni lo Stage dalla scena corrente
            Stage currentStage = (Stage) currentScene.getWindow();
            currentStage.setTitle(titlePages);
            currentStage.setScene(new Scene(root));
            currentStage.show();

        } catch (NullPointerException e) {
            System.out.println("Creazione utetne nullpointerexception " + e);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IO Errore (Benvenuto Admin controller per creazione utente ): \n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore (Benvenuto Admin controller creazione utente): \n" + e.getMessage());
        }
    }

    public void changeSceneGioca() {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/spacca/pages/modpartita.fxml"));
        try {
            Parent root = loader.load();
            Scene currentScene = bottoneModificaUtente.getScene();
            ModPartitaController controller = loader.getController();
            loader.setController(controller);
            controller.initController(amministratore);
            Stage currentStage = (Stage) currentScene.getWindow();
            currentStage.setTitle("Gioca");
            currentStage.setScene(new Scene(root));
            currentStage.show();
        } catch (IOException e) {
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
}