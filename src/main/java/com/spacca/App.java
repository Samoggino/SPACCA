package com.spacca;

import java.io.IOException;

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

            /*
             * Amministratore amministratore = new Amministratore();
             * AbstractGiocatore peach = (Giocatore)
             * amministratore.creaUtenteFisico("peach", "", "@");
             * AbstractGiocatore bowser = (StupidCPU) amministratore.creaCPU("bowser",
             * "StupidCPU");
             * AbstractGiocatore toad = (Giocatore) amministratore.creaUtenteFisico("toad",
             * "", "@");
             * AbstractGiocatore koopa = (SmartCPU) amministratore.creaCPU("koopa",
             * "SmartCPU");
             * 
             * List<String> giocatoriDellaPartita = new ArrayList<>();
             * 
             * giocatoriDellaPartita.add(peach.getUsername());
             * giocatoriDellaPartita.add(toad.getUsername());
             * giocatoriDellaPartita.add(bowser.getUsername());
             * giocatoriDellaPartita.add(koopa.getUsername());
             * 
             * Partita partita =
             * amministratore.creaPartita(amministratore.generaNumeroCasualePartita(),
             * giocatoriDellaPartita);
             * 
             * partita.nuovoTurno();
             * 
             * // Partita partita = amministratore.caricaPartita("p1602");
             * 
             * FXMLLoader loader = new
             * FXMLLoader(App.class.getResource("/com/spacca/pages/tavolo.fxml"));
             * Parent root = loader.load();
             * TavoloController tavolo = loader.getController();
             * loader.setController(tavolo);
             * 
             * tavolo.initController(partita);
             * 
             * 
             * stage.setTitle(partita.getCodice());
             * stage.setScene(new Scene(root));
             * stage.show();
             */

        } catch (IOException e) {
            System.err.println("ERRORE (app IO exception):\t\t " + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERRORE (app Exception):\t\t " + e.getMessage());
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