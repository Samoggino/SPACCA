package com.spacca.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.spacca.asset.carte.Carta;
import com.spacca.asset.match.Partita;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class TavoloController implements Initializable {

    private Partita partita;
    private List<String> giocatori;
    private String giocatoreCorrente;
    private List<Pane> posizione = new ArrayList<>();

    @FXML
    public Pane currentPlayerPane;

    @FXML
    public Pane playerOnTopPane;

    @FXML
    public Pane playerOnLeftPane;

    @FXML
    public Pane playerOnRightPane;

    @FXML
    public ImageView playerOnLeftImage;

    @FXML
    public ImageView playerOnTopImage;

    @FXML
    public ImageView playerOnRightImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    void initController(Partita partita) {
        try {

            this.partita = partita;
            this.partita = partita;
            this.giocatori = partita.getListaDeiGiocatori();
            this.giocatoreCorrente = partita.getGiocatoreCorrente();

            this.posizione.add(currentPlayerPane);

            this.posizione.add(playerOnTopPane);
            this.posizione.add(playerOnRightPane);
            this.posizione.add(playerOnLeftPane);

            for (String string : giocatori) {
                System.out.println(string);
            }

            buildView();
        } catch (Exception e) {
            System.err.println("ERRORE (initController):\t\t " + e.getMessage());
            e.printStackTrace();
        }
    }

    void buildView() {

        // se il numero di giocatori è inferiore a 4, il posto di sinistra non è
        // disponibile
        if (giocatori.size() < 4) {
            playerOnLeftPane.setVisible(false);
            posizione.remove(playerOnLeftPane);
        }

        // se il numero di giocatori è inferiore a 3, il posto a sinistra e il posto a
        // destra non sono disponibili
        if (giocatori.size() < 3) {
            playerOnRightPane.setVisible(false);
            posizione.remove(playerOnRightPane);
        }

        for (int i = 0; i < giocatori.size(); i++) {

            if (giocatori.get(i).equals(giocatoreCorrente)) {
                showMyDeckHandler(giocatoreCorrente, currentPlayerPane);
            } else {
                giocatoreNonCorrente(giocatori.get(i), posizione.get(i));
            }
        }

    }

    public Pane getPlayerPane(Pane paneName) {
        switch (paneName.getId()) {
            case "currentPlayer":
                return currentPlayerPane;
            case "playerOnTop":
                return playerOnTopPane;
            case "playerOnLeft":
                return playerOnLeftPane;
            case "playerOnRight":
                return playerOnRightPane;
            default:
                return null;
        }
    }

    void buildTable() {

        try {
            for (Carta carta : partita.getCarteSulTavolo().getCarteNelMazzo()) { // prende le carte sul tavolo
                Image image = new Image("file:" + carta.getImmagine()); // prende l'immagine della carta
                Background background = new Background(new BackgroundImage(image, null, null, null, null)); // crea lo
                Pane pane = new Pane(); // crea un pannello
                pane.setBackground(background); // aggiunge lo sfondo al pannello
                pane.setPrefSize(100, 150); // imposta le dimensioni del pannello
                posizione.get(0).getChildren().add(pane); // aggiunge il pannello alla posizione 0
            }
        } catch (Exception e) {
            System.err.println("ERRORE (buildTable):\t\t " + e.getMessage());
            e.printStackTrace();
        }
    }

    void showMyDeckHandler(String giocatore, Pane containerPane) {

        try {
            Carta cartaInCima = partita.getCartaInCima(giocatore);

            if (cartaInCima != null) {
                System.out.println("Carta in cima: " + cartaInCima);
            }

            Text text = new Text(giocatore);

            containerPane.getChildren().add(text);

        } catch (Exception e) {
            System.err.println("ERRORE (showMyDeckHandler):\t\t " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Tutto quello che c'è da fare è aggiungere un bottone che permetta di rubare
     * un mazzo ad un altro giocatore
     * 
     * @param giocatore
     * @param containerPane
     */
    public void giocatoreNonCorrente(String giocatore, Pane containerPane) {

        Carta cartaInCimaCarta = partita.getCartaInCima(giocatore);

        if (cartaInCimaCarta != null) {
            getImmagineCartaInCimaDaUnPannello(containerPane)
                    .setImage(new Image("file:" + cartaInCimaCarta.getImmagine()));
            // playerOnRightPane.getChildren()..setImage(new Image("file:" +
            // cartaInCimaCarta.getImmagine()));
        }
        Text text = new Text(giocatore);

        containerPane.getChildren().add(text);

        Button myDeckButton = new Button("Ruba a " + giocatore);
        myDeckButton.setOnAction(event -> partita.getManoDellUtente(giocatore));
        containerPane.getChildren().add(myDeckButton);

    }

    public Button rubaUnMazzoHandler(String playerName, Pane containerPane) {
        Button button = new Button("Player " + playerName);
        button.setOnAction(event -> partita.rubaUnMazzo(giocatoreCorrente, playerName));
        return button;
    }

    private ImageView getImmagineCartaInCimaDaUnPannello(Pane containerPane) {
        for (Node node : containerPane.getChildren()) {
            if (node instanceof ImageView) {
                return (ImageView) node;
            }
        }
        return new ImageView();
    }

}
