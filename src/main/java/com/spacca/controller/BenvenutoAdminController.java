package com.spacca.controller;

import java.io.IOException;

import com.spacca.App;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.asset.utente.giocatore.Giocatore;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BenvenutoAdminController {

    @FXML
    private Button bottoneCreaSingola;

    @FXML
    private Button bottoneCreaTorneo;

    @FXML
    private Button bottoneCreaUtente;

    @FXML
    private Button bottoneEliminaSingola;

    @FXML
    private Button bottoneModificaUtente;

    @FXML
    private Button bottoneEliminaTorneo;

    @FXML
    public AbstractGiocatore giocatoreCorrente;

    @FXML
    private void initialize() throws IOException {
    }

    public void initController(Giocatore giocatoreCorrente) {

        try {

            this.giocatoreCorrente = giocatoreCorrente;

        } catch (NullPointerException e) {
            System.err.println("ERRORE NullPointerException:\t\t " + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERRORE eccezione:\t\t " + e.getMessage());
        }
    }

    @FXML
    private void handleModificaUtente() {
        System.out.println("Siamo in Modifica utente" + giocatoreCorrente);
        String titlePages = "Modifica utente ";
        changeSceneModificaUtente("/com/spacca/pages/selezionaUtente.fxml", giocatoreCorrente, titlePages);
    }

    @FXML
    private void handleElimnaPartita() {
        System.out.println("Siamo in Elimina partita" + giocatoreCorrente);
        String titlePages = "Elimina partita ";
        changeSceneEliminaPartita("/com/spacca/pages/selezionaPartita.fxml", giocatoreCorrente, titlePages);
    }

    public void changeSceneModificaUtente(String fxmlPath, Object controllerData, String titlePages) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Parent root = loader.load();

            Scene currentScene = bottoneModificaUtente.getScene();

            SelezionaUenteController selezionaUenteController = loader.getController();
            loader.setController(selezionaUenteController);
            selezionaUenteController.initController((Giocatore) controllerData);

            // Ottieni lo Stage dalla scena corrente
            Stage currentStage = (Stage) currentScene.getWindow();
            currentStage.setTitle(titlePages);
            currentStage.setScene(new Scene(root));
            currentStage.show();

        } catch (NullPointerException e) {
            System.out.println("Login avvenuto con successo!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IO Errore (Benvenuto Admin controller per modifica partita): \n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore (Benvenuto Admin controller): \n" + e.getMessage());
        }
    }

    public void changeSceneEliminaPartita(String fxmlPath, Object controllerData, String titlePages) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlPath));
            Parent root = loader.load();

            Scene currentScene = bottoneModificaUtente.getScene();

            SelezionaPartitaController selezionaUenteController = loader.getController();
            loader.setController(selezionaUenteController);
            selezionaUenteController.initController((Giocatore) controllerData);

            // Ottieni lo Stage dalla scena corrente
            Stage currentStage = (Stage) currentScene.getWindow();
            currentStage.setTitle(titlePages);
            currentStage.setScene(new Scene(root));
            currentStage.show();

        } catch (NullPointerException e) {
            System.out.println("Login avvenuto con successo!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IO Errore (Benvenuto Admin controller per modifica partita): \n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Errore (Benvenuto Admin controller): \n" + e.getMessage());
        }
    }

}