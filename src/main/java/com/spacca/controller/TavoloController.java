package com.spacca.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.spacca.asset.carte.Carta;
import com.spacca.asset.match.Partita;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TavoloController implements Initializable {

    private Partita partita;
    private List<String> giocatori;
    private String giocatoreCorrente;
    private List<Pane> posizione = new ArrayList<>();

    @FXML
    public Pane currentPlayer = new HBox();

    @FXML
    public Pane playerOnTop = new HBox();

    @FXML
    public Pane playerOnLeft = new VBox();

    @FXML
    public Pane playerOnRight = new VBox();

    // @FXML
    // public ImageView immagineCarta;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    void initController(Partita partita) {

        this.partita = partita;
        this.partita = partita;
        this.giocatori = partita.getListaDeiGiocatori();
        this.giocatoreCorrente = partita.getGiocatoreCorrente();
        try {
            this.posizione.add(currentPlayer);
            this.posizione.add(playerOnLeft);
            this.posizione.add(playerOnTop);
            this.posizione.add(playerOnRight);

            buildView();
        } catch (Exception e) {
            System.err.println("ERRORE (initController):\t\t " + e.getMessage());
            e.printStackTrace();
        }
    }

    void buildView() {

        // se il numero di giocatori è inferiore a 4, il posto di sinistra non è
        // disponibile
        if (giocatori.size() <= 4) {
            playerOnLeft.setVisible(false);
            posizione.remove(playerOnLeft);
        }

        // se il numero di giocatori è inferiore a 3, il posto a sinistra e il posto a
        // destra non sono disponibili
        if (giocatori.size() <= 2) {
            playerOnRight.setVisible(false);
            posizione.remove(playerOnRight);
        }

        for (String giocatore : giocatori) {

            if (giocatore.equals(giocatoreCorrente)) {
                showMyDeckHandler(giocatore, posizione.get(0));
            } else {
                // rubaUnMazzoHandler
            }
        }

    }

    private void buildTable() {

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
        // Pane localPane = new Pane();
        // System.out.println("image dopo averla settata: " + immagineCarta.getImage());

        // try {
        //     Carta cartaInCimaCarta = partita
        //             .getPreseDellUtente(giocatore)
        //             .getUltimaCarta();

        //     if (cartaInCimaCarta != null) {
        //         // immagineCarta = new ImageView("file:" + cartaInCimaCarta.getImmagine());
        //         System.out.println("image dopo averla settata: " + immagineCarta.getImage());
        //     }

        //     Text text = new Text(giocatore);
        //     Button myDeckButton = new Button("Le mie carte");

        //     myDeckButton.setOnAction(event -> System.out.println("\n" +
        //             partita.getManoDellUtente(giocatore)));

        //     localPane.getChildren().add(text);
        //     localPane.getChildren().add(myDeckButton);

        //     containerPane.getChildren().add(localPane);

        // } catch (Exception e) {
        //     System.err.println("ERRORE (showMyDeckHandler):\t\t " + e.getMessage());
        //     e.printStackTrace();
        // }
    }

    public Button rubaUnMazzoHandler(String playerName) {
        Button button = new Button("Player " + playerName);
        button.setOnAction(event -> partita.rubaUnMazzo(giocatoreCorrente, playerName));
        return button;
    }

}
