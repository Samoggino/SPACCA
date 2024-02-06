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
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        try {

            // Amministratore admin = new Amministratore();
            // AbstractGiocatore yoshi = admin.caricaUtente("yoshi");
            // AbstractGiocatore koopa = admin.caricaUtente("koopa")    ;

            // List<String> listaGiocatori = new ArrayList<>();
            // listaGiocatori.add(yoshi.getUsername());
            // listaGiocatori.add(koopa.getUsername());
            // Partita partita = admin.creaPartita(listaGiocatori);
            // FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/spacca/pages/tavolo.fxml"));
            // Parent root = loader.load();
            // TavoloController tavolo = loader.getController();
            // loader.setController(tavolo);
            // tavolo.initController(partita, false, yoshi);

            scene = new Scene(loadFXML("login"), 600, 500);
            // stage.setTitle(partita.getCodice());
            // stage.setScene(new Scene(root));
            stage.setScene(scene);            
            stage.show();

            // Image icon = new
            // Image(getClass().getResourceAsStream("/com/spacca/images/logo/logo.jpg"));
            // scene = new Scene(loadFXML("login"), 600, 500);

            // Amministratore admin = new Amministratore();
            // AbstractGiocatore koopa;
            // , peach, toad, yoshi,
            // bowser, mario, luigi, wario,
            // donkeyKong, daisy, boo, birdo,
            // dryBones, hammerBro, blooper, diddyKong, rosalina, larry, wendy, ludwig,
            // iggy, morton, lemmy;

            // koopa = admin.creaGiocatore("koopa", "Giocatore");
            // peach = admin.creaGiocatore("peach", "StupidCPU");
            // toad = admin.creaGiocatore("toad", "StupidCPU");
            // yoshi = admin.creaGiocatore("yoshi", "StupidCPU");
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

            // List<String> listaGiocatori = new ArrayList<>();
            // listaGiocatori.add(koopa.getUsername());
            // listaGiocatori.add(peach.getUsername());
            // listaGiocatori.add(yoshi.getUsername());
            // listaGiocatori.add(toad.getUsername());
            // listaGiocatori.add(bowser.getUsername());
            // listaGiocatori.add(mario.getUsername());
            // // listaGiocatori.add(luigi.getUsername());
            // // listaGiocatori.add(wario.getUsername());
            // // listaGiocatori.add(donkeyKong.getUsername());
            // // listaGiocatori.add(daisy.getUsername());
            // // listaGiocatori.add(boo.getUsername());
            // // listaGiocatori.add(birdo.getUsername());
            // // listaGiocatori.add(dryBones.getUsername());
            // // listaGiocatori.add(hammerBro.getUsername());
            // // listaGiocatori.add(blooper.getUsername());
            // // listaGiocatori.add(diddyKong.getUsername());
            // // listaGiocatori.add(rosalina.getUsername());

            // // Torneo torneo = admin.creaTorneo(listaGiocatori);
            // Torneo torneo = admin.caricaTorneo("T" + "7413").nuovoTurnoDelTorneo();

            // torneo.aggiornaLeaderboard();
            // System.out.println(torneo.getLeaderboard());

            // torneo.simulaPartiteCPU();

            // Partita partita = torneo.getPartitaDelGiocatore(yoshi);
            // if (torneo.getCodiciPartite().size() > 0) {
            // admin.caricaPartita(torneo.getCodiciPartite().get(0));
            // }

            // FXMLLoader loader = new
            // FXMLLoader(App.class.getResource("/com/spacca/pages/tavolo.fxml"));
            // Parent root = loader.load();
            // TavoloController tavolo = loader.getController();
            // loader.setController(tavolo);
            // if (partita != null) {
            // tavolo.initController(partita, true);
            // stage.setTitle(partita.getCodice());
            // // stage.getIcons().add(icon);
            // stage.setScene(new Scene(root));
            // if (!yoshi.isCPU()) {
            // stage.show();
            // }
            // }

            // // System.out.println("Partecipanti al torneo:\n" +
            // torneo.getPartecipanti());

        } catch (IOException e) {
            System.err.println("ERRORE (app IO exception):\t\t " + e.getMessage());
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