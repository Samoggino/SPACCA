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

        scene = new Scene(loadFXML("login"), 600, 500);
        // scene = new Scene(loadFXML("primary"), 500, 500);

        try {
            stage.setTitle("Login APP");
            stage.setScene(scene);
            stage.show();
            Image icon = new Image(getClass().getResourceAsStream("/com/spacca/images/icone/logo.jpg"));
            stage.getIcons().add(icon);

        } catch (Exception e) {
            System.err.println("ERRORE (app):\t\t " + e.getMessage());
        }
        // aggiunge un'icona alla finestra

    }

    public static void setRoot(String fxml) throws IOException {
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