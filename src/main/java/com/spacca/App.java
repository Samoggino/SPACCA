package com.spacca;

import java.io.IOException;
import com.spacca.database.TorneoHandler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        try {
            Image icon = new Image(getClass().getResourceAsStream("/com/spacca/images/logo/logo.jpg"));
            scene = new Scene(loadFXML("login"), 600, 500);

            Amministratore admin = new Amministratore();
            AbstractGiocatore koopa, peach, toad, bowser;

            koopa = admin.creaGiocatore("koopa", "Giocatore");
            peach = admin.creaGiocatore("peach", "SmartCPU");
            toad = admin.creaGiocatore("toad", "StupidCPU");
            bowser = admin.creaGiocatore("bowser", "SmartCPU");

            List<String> listaGiocatori = new ArrayList<>();
            listaGiocatori.add(koopa.getUsername());
            listaGiocatori.add(peach.getUsername());
            listaGiocatori.add(toad.getUsername());
            listaGiocatori.add(bowser.getUsername());

            TorneoHandler torneoHandler = new TorneoHandler();

            Torneo torneo = admin.creaTorneo(listaGiocatori);

            System.out.println(torneoHandler.mostraTuttiITornei());
            
            torneoHandler.elimina(torneo.getCodice());
            System.out.println(torneoHandler.mostraTuttiITornei());

            stage.setTitle("Login APP");
            stage.getIcons().add(icon);

            stage.setScene(scene);
            stage.show();

            // Amministratore admin = new Amministratore();
            // AbstractGiocatore koopa, peach, toad, bowser;

            // koopa = admin.creaGiocatore("koopa", "Giocatore");
            // peach = admin.creaGiocatore("peach", "SmartCPU");
            // toad = admin.creaGiocatore("toad", "StupidCPU");
            // bowser = admin.creaGiocatore("bowser", "SmartCPU");

            // List<String> listaGiocatori = new ArrayList<>();
            // listaGiocatori.add(koopa.getUsername());
            // listaGiocatori.add(peach.getUsername());
            // listaGiocatori.add(toad.getUsername());
            // listaGiocatori.add(bowser.getUsername());
            // // Torneo torneo = admin.caricaTorneo("T2656");

            // Partita partita = null;
            // try {
            // Torneo torneo = admin.creaTorneo(listaGiocatori);
            // partita = admin.caricaPartita(torneo.getCodiciPartite().get(0));
            // System.out.println("Codice partita: " + partita.getCodice());
            // System.out.println("Codice torneo: " + torneo.getCodice());
            // // new TorneoHandler().elimina(torneo.getCodice());
            // System.out.println("Torneo eliminato");

            // } catch (Exception e) {
            // System.err.println("ERRORE (ziopera):\t\t " + e.getMessage());
            // e.printStackTrace();
            // }

            // // partita.nuovoTurno();
            // FXMLLoader loader = new
            // FXMLLoader(App.class.getResource("/com/spacca/pages/tavolo.fxml"));
            // Parent root = loader.load();
            // TavoloController tavolo = loader.getController();
            // loader.setController(tavolo);
            // tavolo.initController(partita);
            // stage.setTitle(partita.getCodice());
            // stage.setScene(new Scene(root));
            // stage.show();

        } catch (IOException e) {
            System.err.println("ERRORE (app IO exception):\t\t " + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERRORE (app Exception):\t\t " + e.getMessage());
        }

    }
    // Image icon = new
    // Image(getClass().getResourceAsStream("/com/spacca/images/logo/logo.jpg"));
    // scene = new Scene(loadFXML("login"), 600, 500);

    // stage.setTitle("Login APP");
    // stage.getIcons().add(icon);

    //

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