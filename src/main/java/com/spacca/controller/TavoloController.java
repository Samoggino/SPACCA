package com.spacca.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.spacca.asset.match.Partita;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
            System.out.println("Rimosso il giocatore a sinistra");
        }

        // se il numero di giocatori è inferiore a 3, il posto a sinistra e il posto a
        // destra non sono disponibili
        if (giocatori.size() <= 2) {
            playerOnRight.setVisible(false);
            posizione.remove(playerOnRight);
            System.out.println("Rimosso il giocatore a destra");
        }

        for (String giocatore : giocatori) {

            if (giocatore.equals(giocatoreCorrente)) {
                showMyDeckHandler(giocatore, posizione.get(0));
            } else {
                // rubaUnMazzoHandler
            }
        }

    }

    void showMyDeckHandler(String giocatore, Pane containerPane) {
        try {
            Text text = new Text(giocatore);
            Button myDeckButton = new Button("Le mie carte");
            myDeckButton.setOnAction(event -> System.out.println("\n" + partita.getManoDellUtente(giocatore)));

            containerPane.getChildren().add(text);
            containerPane.getChildren().add(myDeckButton);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Button rubaUnMazzoHandler(String playerName) {
        Button button = new Button("Player " + playerName);
        button.setOnAction(event -> partita.rubaUnMazzo(giocatoreCorrente, playerName));
        return button;
    }

}
