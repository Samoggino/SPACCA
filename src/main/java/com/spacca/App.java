package com.spacca;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.spacca.asset.match.Partita;
import com.spacca.asset.utente.Amministratore;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.controller.TavoloController;

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
            //
            // List<String> listaGiocatori = new ArrayList<>();
            // listaGiocatori.add(koopa.getUsername());
            // listaGiocatori.add(peach.getUsername());
            // listaGiocatori.add(toad.getUsername());
            // listaGiocatori.add(bowser.getUsername());
            //
            // Partita partita = admin.creaPartita(listaGiocatori);
            // partita.nuovoTurno();
            //
            // FXMLLoader loader = new
            // FXMLLoader(App.class.getResource("/com/spacca/pages/tavolo.fxml"));
            // Parent root = loader.load();
            // TavoloController tavolo = loader.getController();
            // loader.setController(tavolo);
            //
            // tavolo.initController(partita);
            //
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