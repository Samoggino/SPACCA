package com.spacca;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.spacca.asset.match.Partita;
import com.spacca.asset.match.Torneo;
import com.spacca.asset.utente.Amministratore;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.controller.TavoloController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        try {

            // Amministratore amministratore = new Amministratore();
            // AbstractGiocatore admin = amministratore.caricaUtente("admin");
            // AbstractGiocatore yoshi = amministratore.caricaUtente("yoshi");
            // AbstractGiocatore peach = amministratore.caricaUtente("peach");
            // AbstractGiocatore toad = amministratore.caricaUtente("toad");
            // List<String> listaGiocatori = new ArrayList<>();
            // listaGiocatori.add(admin.getUsername());
            // listaGiocatori.add(yoshi.getUsername());
            // listaGiocatori.add(peach.getUsername());
            // listaGiocatori.add(toad.getUsername());

            // int numeroGiocatoriScelto = 8;
            // System.out.println("Numero selezionato: " + numeroGiocatoriScelto);
            // System.out.println("Nuemro giocatori inseriti " + listaGiocatori.size());
            // Torneo torneo = amministratore.creaTorneo(listaGiocatori,
            // numeroGiocatoriScelto);

            // System.out.println("Torneo creato con questi partecipanti: " +
            // torneo.getPartecipanti() + "\t"
            // + torneo.getPartecipanti().size());

            // Partita partita =
            // amministratore.caricaPartita(torneo.getCodiciPartite().get(0));

            // new GiocatoreHandler().elimina(toad.getUsername());

            // partita = amministratore.caricaPartita(partita.getCodice());

            // Amministratore admin = new Amministratore();
            // Partita partita = admin.caricaPartita("tornei/T7094/TP3497");

            // FXMLLoader loader = new
            // FXMLLoader(App.class.getResource("/com/spacca/pages/tavolo.fxml"));
            // Parent root = loader.load();
            // TavoloController tavolo = loader.getController();
            // loader.setController(tavolo);
            // tavolo.initController(partita, false, amministratore);
            scene = new Scene(loadFXML("login"), 600, 500);
            // stage.setTitle(partita.getCodice());
            // stage.setScene(new Scene(root));
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("ERRORE (app IO exception):\t\t " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Scene getScene() {
        return scene;
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/spacca/pages/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Stage getStage() {
        return (Stage) scene.getWindow();
    }

}