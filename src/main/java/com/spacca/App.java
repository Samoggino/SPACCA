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
            stage.setTitle("Login APP");
            stage.getIcons().add(icon);
            // FXMLLoader loader = new
            // FXMLLoader(getClass().getResource("/percorso/alla/schermata.fxml"));
            // Parent root = loader.load();
            stage.setScene(scene);
            stage.show();

            /*
             * Amministratore admin = new Amministratore();
             * AbstractGiocatore koopa, peach, toad, bowser, mario, luigi, yoshi, wario,
             * donkeyKong, daisy, boo, birdo,
             * dryBones, hammerBro, blooper, diddyKong, rosalina, larry, wendy, ludwig,
             * iggy, morton, lemmy;
             * 
             * koopa = admin.creaGiocatore("koopa", "StupidCPU");
             * peach = admin.creaGiocatore("peach", "StupidCPU");
             * toad = admin.creaGiocatore("toad", "StupidCPU");
             * bowser = admin.creaGiocatore("bowser", "StupidCPU");
             * mario = admin.creaGiocatore("mario", "StupidCPU");
             * luigi = admin.creaGiocatore("luigi", "StupidCPU");
             * yoshi = admin.creaGiocatore("yoshi", "StupidCPU");
             * wario = admin.creaGiocatore("wario", "StupidCPU");
             * donkeyKong = admin.creaGiocatore("donkeyKong", "StupidCPU");
             * daisy = admin.creaGiocatore("daisy", "StupidCPU");
             * boo = admin.creaGiocatore("boo", "StupidCPU");
             * birdo = admin.creaGiocatore("birdo", "StupidCPU");
             * dryBones = admin.creaGiocatore("dryBones", "StupidCPU");
             * hammerBro = admin.creaGiocatore("hammerBro", "StupidCPU");
             * blooper = admin.creaGiocatore("blooper", "StupidCPU");
             * diddyKong = admin.creaGiocatore("diddyKong", "StupidCPU");
             * rosalina = admin.creaGiocatore("rosalina", "StupidCPU");
             * 
             * List<String> listaGiocatori = new ArrayList<>();
             * listaGiocatori.add(koopa.getUsername());
             * listaGiocatori.add(peach.getUsername());
             * listaGiocatori.add(toad.getUsername());
             * listaGiocatori.add(bowser.getUsername());
             * listaGiocatori.add(mario.getUsername());
             * listaGiocatori.add(luigi.getUsername());
             * listaGiocatori.add(yoshi.getUsername());
             * listaGiocatori.add(wario.getUsername());
             * listaGiocatori.add(donkeyKong.getUsername());
             * listaGiocatori.add(daisy.getUsername());
             * listaGiocatori.add(boo.getUsername());
             * listaGiocatori.add(birdo.getUsername());
             * listaGiocatori.add(dryBones.getUsername());
             * // listaGiocatori.add(hammerBro.getUsername());
             * // listaGiocatori.add(blooper.getUsername());
             * // listaGiocatori.add(diddyKong.getUsername());
             * // listaGiocatori.add(rosalina.getUsername());
             * 
             * if (listaGiocatori.size() < 2) {
             * throw new
             * IllegalArgumentException("Non è possibile creare un torneo con meno di due giocatori"
             * );
             * }
             * 
             * if (((listaGiocatori.size() & (listaGiocatori.size() - 1)) == 0) == false) {
             * // se il numero di listaGiocatori non è una potenza di 2 allora non è
             * // possibile
             * /// creare un torneo (molto carino questo controllo, non lo conoscevo)
             * // se non è una potenza di due, aggiungi giocatori finti per arrivare alla
             * // potenza di due successiva
             * int potenzaDiDue = 1;
             * while (potenzaDiDue < listaGiocatori.size()) {
             * potenzaDiDue *= 2;
             * }
             * int giocatoriFinti = potenzaDiDue - listaGiocatori.size();
             * for (int i = 0; i < giocatoriFinti; i++) {
             * listaGiocatori.add(admin.creaGiocatore("paolone-" + i,
             * "StupidCPU").getUsername());
             * }
             * }
             * Torneo torneo = admin.creaTorneo(listaGiocatori);
             * System.out.println("Partecipanti al torneo:\n" + torneo.getPartecipanti());
             * // Torneo torneo = admin.caricaTorneo("T5928").nuovoTurnoDelTorneo();
             * stage.setTitle(torneo.getCodice() + " - Torneo");
             * stage.getIcons().add(icon);
             * stage.setScene(scene);
             * stage.show();
             * esecuzioneTorneo(admin, torneo);
             */

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