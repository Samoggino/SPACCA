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
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class TavoloController implements Initializable {

    private Partita partita;
    private List<String> giocatori;
    private String giocatoreCorrente;
    private List<Pane> posizione = new ArrayList<>();

    @FXML
    public Pane currentPlayerPane, playerOnTopPane, playerOnLeftPane, playerOnRightPane;

    @FXML
    public ImageView playerOnLeftImage, playerOnTopImage, playerOnRightImage;

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

        hideUnusedPlayerPanes();
        // buildTable();
        giocatori.forEach(g -> {
            updatePlayerPanel(g, posizione.get(giocatori.indexOf(g)));
        });
    }

    private void hideUnusedPlayerPanes() {
        for (int i = 3; i > giocatori.size() - 1; i--) {
            posizione.get(i).setVisible(false);
            posizione.remove(i);
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

    /**
     * void buildTable() {
     * 
     * try {
     * for (Carta carta : partita.getCarteSulTavolo().getCarteNelMazzo()) { //
     * prende le carte sul tavolo
     * Image image = new Image("file:" + carta.getImmagine()); // prende l'immagine
     * della carta
     * Background background = new Background(new BackgroundImage(image, null, null,
     * null, null)); // crea lo
     * Pane pane = new Pane(); // crea un pannello
     * pane.setBackground(background); // aggiunge lo sfondo al pannello
     * pane.setPrefSize(100, 150); // imposta le dimensioni del pannello
     * posizione.get(0).getChildren().add(pane); // aggiunge il pannello alla
     * posizione 0
     * }
     * } catch (Exception e) {
     * System.err.println("ERRORE (buildTable):\t\t " + e.getMessage());
     * e.printStackTrace();
     * }
     * }
     */

    /**
     * Aggiorna le informazioni del giocatore e aggiunge i giusti bottoni
     * 
     * @param giocatore
     * @param containerPane
     */
    public void updatePlayerPanel(String giocatore, Pane containerPane) {
        boolean isCurrentPlayer = giocatore.equals(giocatoreCorrente);
        Text existingText = getNomeCorrente(containerPane);

        if (existingText == null) {
            containerPane.getChildren().add(0, new Text(giocatore));
        } else {
            existingText.setText(giocatore);
        }

        Carta cartaInCimaCarta = partita.getCartaInCima(giocatore);
        if (cartaInCimaCarta != null) {
            getImmagineCorrente(containerPane).setImage(new Image("file:" + cartaInCimaCarta.getImmagine()));
        }

        if (isCurrentPlayer) {
            getButtonCorrente(containerPane).setText("Mostra le mie carte");
            getButtonCorrente(containerPane)
                    .setOnAction(event -> System.out.println(partita.getManoDellUtente(giocatoreCorrente)));
        } else {
            getButtonCorrente(containerPane).setText("Ruba a " + giocatore);
            getButtonCorrente(containerPane).setOnAction(event -> partita.rubaUnMazzo(giocatoreCorrente, giocatore));
        }

    }

    private ImageView getImmagineCorrente(Pane containerPane) {
        return containerPane.getChildren().stream()
                .filter(node -> node instanceof ImageView)
                .map(node -> (ImageView) node)
                .findFirst()
                .orElse(new ImageView());
    }

    private Text getNomeCorrente(Pane containerPane) {
        return containerPane.getChildren().stream()
                .filter(node -> node instanceof Text)
                .map(node -> (Text) node)
                .findFirst()
                .orElse(new Text());
    }

    private Button getButtonCorrente(Pane containerPane) {
        return containerPane.getChildren().stream()
                .filter(node -> node instanceof Button)
                .map(node -> (Button) node)
                .findFirst()
                .orElse(new Button());
    }

}
