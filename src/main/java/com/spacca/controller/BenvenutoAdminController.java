package com.spacca.controller;

import java.io.IOException;

import com.spacca.App;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.database.GiocatoreHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        changeSceneModificaUtente("/com/spacca/pages/selezionaUtente.fxml", titlePages);
    }

    @FXML
    private void handleElimnaPartita() {
        System.out.println("Siamo in Elimina partita" + amministratore);
        String titlePages = "Elimina partita ";
        changeSceneEliminaPartita("/com/spacca/pages/eliminaPartita.fxml", titlePages);
    }

    @FXML
    private void handleElimnaTorneo() {
        System.out.println("Siamo in Elimina Torneo" + amministratore);
        String titlePages = "Elimina Torneo ";
        changeSceneEliminaTorneo("/com/spacca/pages/eliminaTorneo.fxml", titlePages);
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
    private void gioca() {
        System.out.println("Siamo in creazione singola");
        changeSceneGioca();
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
}