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
            AbstractGiocatore koopa, peach, toad, bowser, mario, luigi, yoshi, wario,
                    donkeyKong, daisy, boo, birdo,
                    dryBones, hammerBro, blooper, diddyKong, rosalina, larry, wendy, ludwig,
                    iggy, morton, lemmy;

            koopa = admin.creaGiocatore("koopa", "SmartCPU");
            peach = admin.creaGiocatore("peach", "SmartCPU");
            toad = admin.creaGiocatore("toad", "SmartCPU");
            yoshi = admin.creaGiocatore("yoshi", "Giocatore");
            // bowser = admin.creaGiocatore("bowser", "StupidCPU");
            // mario = admin.creaGiocatore("mario", "StupidCPU");
            // luigi = admin.creaGiocatore("luigi", "StupidCPU");
            // wario = admin.creaGiocatore("wario", "StupidCPU");
            // donkeyKong = admin.creaGiocatore("donkeyKong", "StupidCPU");
            // daisy = admin.creaGiocatore("daisy", "StupidCPU");
            // boo = admin.creaGiocatore("boo", "StupidCPU");
            // birdo = admin.creaGiocatore("birdo", "StupidCPU");
            // dryBones = admin.creaGiocatore("dryBones", "StupidCPU");
            // hammerBro = admin.creaGiocatore("hammerBro", "StupidCPU");
            // blooper = admin.creaGiocatore("blooper", "StupidCPU");
            // diddyKong = admin.creaGiocatore("diddyKong", "StupidCPU");
            // rosalina = admin.creaGiocatore("rosalina", "StupidCPU");

            List<String> listaGiocatori = new ArrayList<>();
            listaGiocatori.add(koopa.getUsername());
            listaGiocatori.add(peach.getUsername());
            listaGiocatori.add(yoshi.getUsername());
            listaGiocatori.add(toad.getUsername());
            // listaGiocatori.add(bowser.getUsername());
            // listaGiocatori.add(mario.getUsername());
            // listaGiocatori.add(luigi.getUsername());
            // listaGiocatori.add(wario.getUsername());
            // listaGiocatori.add(donkeyKong.getUsername());
            // listaGiocatori.add(daisy.getUsername());
            // listaGiocatori.add(boo.getUsername());
            // listaGiocatori.add(birdo.getUsername());
            // listaGiocatori.add(dryBones.getUsername());
            // listaGiocatori.add(hammerBro.getUsername());
            // listaGiocatori.add(blooper.getUsername());
            // listaGiocatori.add(diddyKong.getUsername());
            // listaGiocatori.add(rosalina.getUsername());

            // Torneo torneo = admin.creaTorneo(listaGiocatori);
            Torneo torneo = admin.caricaTorneo("T" + "6495").nuovoTurnoDelTorneo();

            System.out.println("Partecipanti al torneo:\n" + torneo.getPartecipanti());
            System.out.println(yoshi);

            torneo.simulaPartiteCPU();

            Partita partita = torneo.getPartitaDelGiocatore(yoshi);
            // admin.caricaPartita(torneo.getCodiciPartite().get(0));

            FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/spacca/pages/tavolo.fxml"));
            Parent root = loader.load();
            TavoloController tavolo = loader.getController();
            loader.setController(tavolo);
            if (partita == null) {
                System.out.println("Partita non ancora creata");
            } else {
                tavolo.initController(partita, true);
                stage.setTitle(partita.getCodice());
                stage.getIcons().add(icon);
                stage.setScene(new Scene(root));
                stage.show();
            }

            // System.out.println("Partecipanti al torneo:\n" + torneo.getPartecipanti());

        } catch (IOException e) {
            System.err.println("ERRORE (app IO exception):\t\t " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
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