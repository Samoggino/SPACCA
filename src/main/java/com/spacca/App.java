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
            // Image icon = new
            // Image(getClass().getResourceAsStream("/com/spacca/images/logo/logo.jpg"));
            scene = new Scene(loadFXML("login"), 600, 500);

            Amministratore admin = new Amministratore();
            AbstractGiocatore koopa, peach, toad, bowser, mario, luigi, yoshi, wario, donkeyKong, daisy, boo, birdo,
                    dryBones, hammerBro, blooper, diddyKong, rosalina, larry, wendy, ludwig, iggy, morton, lemmy;

            koopa = admin.creaGiocatore("koopa", "SmartCPU");
            peach = admin.creaGiocatore("peach", "SmartCPU");
            toad = admin.creaGiocatore("toad", "SmartCPU");
            bowser = admin.creaGiocatore("bowser", "StupidCPU");
            mario = admin.creaGiocatore("mario", "SmartCPU");
            luigi = admin.creaGiocatore("luigi", "SmartCPU");
            yoshi = admin.creaGiocatore("yoshi", "SmartCPU");
            wario = admin.creaGiocatore("wario", "SmartCPU");
            donkeyKong = admin.creaGiocatore("donkeyKong", "SmartCPU");
            daisy = admin.creaGiocatore("daisy", "SmartCPU");
            boo = admin.creaGiocatore("boo", "SmartCPU");
            birdo = admin.creaGiocatore("birdo", "SmartCPU");
            dryBones = admin.creaGiocatore("dryBones", "SmartCPU");
            hammerBro = admin.creaGiocatore("hammerBro", "SmartCPU");
            blooper = admin.creaGiocatore("blooper", "SmartCPU");
            diddyKong = admin.creaGiocatore("diddyKong", "SmartCPU");

            List<String> listaGiocatori = new ArrayList<>();
            listaGiocatori.add(koopa.getUsername());
            listaGiocatori.add(peach.getUsername());
            listaGiocatori.add(toad.getUsername());
            listaGiocatori.add(bowser.getUsername());
            listaGiocatori.add(mario.getUsername());
            listaGiocatori.add(luigi.getUsername());
            listaGiocatori.add(yoshi.getUsername());
            listaGiocatori.add(wario.getUsername());
            listaGiocatori.add(donkeyKong.getUsername());
            listaGiocatori.add(daisy.getUsername());
            listaGiocatori.add(boo.getUsername());
            listaGiocatori.add(birdo.getUsername());
            listaGiocatori.add(dryBones.getUsername());
            listaGiocatori.add(hammerBro.getUsername());
            listaGiocatori.add(blooper.getUsername());
            listaGiocatori.add(diddyKong.getUsername());

            Torneo torneo = admin.creaTorneo(listaGiocatori);
            System.out.println("Partecipanti al torneo:\n" + torneo.getPartecipanti());
            // Torneo torneo = admin.caricaTorneo("T5928").nuovoTurnoDelTorneo();
            esecuzioneTorneo(admin, torneo);

        } catch (IOException e) {
            System.err.println("ERRORE (app IO exception):\t\t " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    // Image icon = new
    // Image(getClass().getResourceAsStream("/com/spacca/images/logo/logo.jpg"));
    // scene = new Scene(loadFXML("login"), 600, 500);

    private void esecuzioneTorneo(Amministratore admin, Torneo torneo) throws IOException {
        for (String codicePartita : torneo.getCodiciPartite()) {
            Partita partita = admin.caricaPartita(codicePartita);

            FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/spacca/pages/tavolo.fxml"));
            Parent root = loader.load();
            TavoloController tavolo = loader.getController();
            loader.setController(tavolo);
            tavolo.initController(partita, true);

            // stage = new Stage(); // Creare una nuova finestra per ogni partita
            // stage.setTitle(partita.getCodice());
            // stage.setScene(new Scene(root));
            // stage.show();
        }
        if (torneo.getPartecipanti().size() > 1) {
            esecuzioneTorneo(admin, admin.caricaTorneo(torneo.getCodice()).nuovoTurnoDelTorneo());
        } else {
            System.out.println("Il vincitore del torneo è: " + torneo.getPartecipanti());
        }
    }

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