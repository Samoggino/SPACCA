package com.spacca.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.spacca.asset.carte.Carta;
import com.spacca.asset.carte.Mazzo;
import com.spacca.asset.match.Partita;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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
    public GridPane tavolo;

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
        giocatori.forEach(g -> {
            updatePlayerPanel(g, posizione.get(giocatori.indexOf(g)));
        });
        buildTable();
    }

    private void hideUnusedPlayerPanes() {
        for (int i = posizione.size() - 1; i >= giocatori.size(); i--) {
            posizione.get(i).setVisible(false);
            posizione.remove(i);
        }
    }

    Pane getPlayerPane(Pane paneName) {
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
        Mazzo carteSulTavolo = partita.getCarteSulTavolo();
        int maxCartePerRiga = 4;

        int colonna = 0;
        int riga = 0;

        for (Carta carta : carteSulTavolo.getCarteNelMazzo()) {
            ImageView cartaView = createCartaImageView(carta);

            // Associare la Carta come proprietà di ImageView
            cartaView.setUserData(carta);

            // Aggiungere l'evento di clic
            cartaView.setOnMouseClicked(event -> prendiCartaHandler((Carta) cartaView.getUserData()));

            tavolo.add(cartaView, colonna, riga);

            colonna++;
            if (colonna >= maxCartePerRiga) {
                colonna = 0;
                riga++;
            }
        }
    }

    private ImageView createCartaImageView(Carta carta) {
        String immaginePath = "file:" + carta.getImmagine();
        Image immagine = new Image(immaginePath);
        ImageView cartaView = new ImageView(immagine);

        return cartaView;
    }

    private void prendiCartaHandler(Carta posizioneCartaDaPrendere) {

        // controlla che il giocatore possa prendere quella carta
        if (partita.getManoDellUtente(giocatoreCorrente) == null) {
            System.out.println("Non puoi prendere carte dal tavolo");
            return;
        }
        System.out.println(posizioneCartaDaPrendere);
        partita.cercaCartaSulTavolo(giocatoreCorrente, posizioneCartaDaPrendere);
        refreshData();
    }

    void refreshData() {
        tavolo.getChildren().clear();
        buildView();
    }

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
