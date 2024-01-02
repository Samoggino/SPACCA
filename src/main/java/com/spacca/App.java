package com.spacca;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.*;

import java.io.File;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 500, 500);
        try {

            /**
             * Sei
             * Pronto
             * A
             * Combattere
             * Contro gli
             * Avversari?
             */

        } catch (Exception e) {
            System.err.println("ERRORE:\t\t " + e.getMessage());
        }
        // aggiunge un'icona alla finestra
        Image icon = new Image(getClass().getResourceAsStream("/com/spacca/img/logo.png"));
        stage.getIcons().add(icon);

        // titolo della finestra
        stage.setTitle("SPACCA");
        // imposta la scena nello stage
        stage.setScene(scene);
        // mosta la finestra
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}