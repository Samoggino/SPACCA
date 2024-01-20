package com.spacca.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.spacca.asset.carte.Carta;
import com.spacca.asset.match.Partita;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class TavoloController implements Initializable {

    private Partita partita;

    private Carta cartaDelTavolo;
    private Carta cartaDellaMano;

    @FXML
    public Pane currentPlayerPane, playerOnTopPane, playerOnLeftPane, playerOnRightPane;

    @FXML
    public Pane tavolo;

    @FXML
    public GridPane piatto;

    @FXML
    public ImageView playerOnLeftImage, playerOnTopImage, playerOnRightImage;

    @FXML
    public FlowPane playerHand;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void initController(Partita partita) {
        try {

            this.partita = partita;
            System.out.println("Partita: " + partita.getCodice());
            // metti come titolo dello stage il codice della partita

            buildView();

        } catch (Exception e) {
            System.err.println("ERRORE (initController):\t\t " + e.getMessage());
            e.printStackTrace();
        }
    }

    void buildView() {

        try {
            buildGiocatore();
            buildHand();
            buildTable();
        } catch (Exception e) {
            System.err.println("ERRORE (buildView):\t\t " + e.getMessage());
            e.printStackTrace();
        }

    }

    void buildHand() {
        playerHand.getChildren().clear();

        for (Carta cartaDellaMano : partita.getManoDellUtente(partita.getGiocatoreCorrente()).getCarteNelMazzo()) {
            ImageView cartaView = createCartaImageView(cartaDellaMano);
            cartaView.setFitWidth(0.5 * cartaView.getImage().getWidth());
            cartaView.setFitHeight(0.5 * cartaView.getImage().getHeight());

            // Aggiungi il gestore di eventi per iniziare il trascinamento
            cartaView.setOnDragDetected(event -> iniziaTrascinamento(cartaDellaMano, cartaView));

            playerHand.getChildren().add(cartaView);
        }
    }

    void buildTable() {
        int maxCartePerRiga = 6;

        int colonna = 0;
        int riga = 0;

        for (Carta carta : partita.getCarteSulTavolo().getCarteNelMazzo()) {
            ImageView cartaView = createCartaImageView(carta);
            cartaView.setFitWidth(0.5 * cartaView.getImage().getWidth());
            cartaView.setFitHeight(0.5 * cartaView.getImage().getHeight());
            piatto.add(cartaView, colonna, riga);

            colonna++;
            if (colonna >= maxCartePerRiga) {
                colonna = 0;
                riga++;
            }
        }
    }

    void buildGiocatore() {

        switch (partita.getListaDeiGiocatori().size()) {

            case 2:
                playerOnLeftPane.setVisible(false);
                playerOnRightPane.setVisible(false);

                currentPlayerPane.setUserData(partita.getListaDeiGiocatori().get(0));
                playerOnTopPane.setUserData(partita.getListaDeiGiocatori().get(1));

                updatePlayerPanel(currentPlayerPane);
                updatePlayerPanel(playerOnTopPane);

                break;

            case 3:
                playerOnLeftPane.setVisible(false);

                currentPlayerPane.setUserData(partita.getListaDeiGiocatori().get(0));
                playerOnTopPane.setUserData(partita.getListaDeiGiocatori().get(1));
                playerOnRightPane.setUserData(partita.getListaDeiGiocatori().get(2));

                updatePlayerPanel(currentPlayerPane);
                updatePlayerPanel(playerOnTopPane);
                updatePlayerPanel(playerOnRightPane);
                break;

            case 4:

                currentPlayerPane.setUserData(partita.getListaDeiGiocatori().get(0));
                playerOnLeftPane.setUserData(partita.getListaDeiGiocatori().get(1));
                playerOnTopPane.setUserData(partita.getListaDeiGiocatori().get(2));
                playerOnRightPane.setUserData(partita.getListaDeiGiocatori().get(3));

                updatePlayerPanel(currentPlayerPane);
                updatePlayerPanel(playerOnLeftPane);
                updatePlayerPanel(playerOnTopPane);
                updatePlayerPanel(playerOnRightPane);
                break;

            default:
                break;
        }

    }

    void iniziaTrascinamento(Carta cartaDellaMano, ImageView cartaView) {
        this.cartaDellaMano = cartaDellaMano;

        cartaView.setEffect(new DropShadow());
        cartaView.setCursor(Cursor.CLOSED_HAND);

        cartaView.setOnDragDetected(event -> iniziaTrascinamento(cartaDellaMano, cartaView));
        cartaView.setOnMouseReleased(event -> rilasciaTrascinamento(cartaView, event));
        cartaView.startFullDrag();
    }

    void rilasciaTrascinamento(ImageView cartaView, MouseEvent event) {
        try {
            cartaView.setEffect(null);
            cartaView.setCursor(Cursor.DEFAULT);

            PickResult pickResult = event.getPickResult();
            Node nodoColpito = pickResult.getIntersectedNode();

            cartaLasciataSuCarta(nodoColpito);
            cartaLasciataSuTavolo(nodoColpito);

        } catch (NullPointerException e) {
            System.err.println("ERRORE (rilasciaTrascinamento - NullPointerException):\t\t " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("ERRORE (rilasciaTrascinamento):\t\t " + e.getMessage());
            e.printStackTrace();
        }
    }

    void cartaLasciataSuCarta(Node nodoColpito) {
        if (nodoColpito instanceof ImageView) {
            ImageView cartaSottoIlMouse = (ImageView) nodoColpito;
            this.cartaDelTavolo = (Carta) cartaSottoIlMouse.getUserData();

            if (checkPossoPrendereLaCarta(this.cartaDelTavolo)) {
                prendiCartaHandler(this.cartaDelTavolo, this.cartaDellaMano);
            } else {
                System.out.println("Non puoi prendere " + this.cartaDelTavolo + " con " + this.cartaDellaMano);
            }
        }
    }

    void cartaLasciataSuTavolo(Node nodoColpito) {
        if (nodoColpito instanceof GridPane) {
            // System.out.println("Puoi scartare " + this.cartaDellaMano);
            scartaCartaHandler(this.cartaDellaMano);
        }
    }

    boolean checkPossoPrendereLaCarta(Carta cartaDelTavolo) {
        return cartaDelTavolo.getValore() == this.cartaDellaMano.getValore();
    }

    ImageView createCartaImageView(Carta carta) {
        String immaginePath = "file:" + carta.getImmagine();
        Image immagine = new Image(immaginePath);
        ImageView cartaView = new ImageView(immagine);

        // Aggiungi i dati della carta all'ImageView
        cartaView.setUserData(carta);

        return cartaView;
    }

    void prendiCartaHandler(Carta cartaSelezionata, Carta cartaDallaManoDellUtente) {

        // controlla che il giocatore possa prendere quella carta
        if (partita.getManoDellUtente(partita.getGiocatoreCorrente()) == null) {
            System.out.println("Non puoi prendere carte dal tavolo");
            return;
        }
        System.out.println(cartaSelezionata);
        partita.prendiCartaConCartaDellaMano(partita.getGiocatoreCorrente(), cartaSelezionata,
                cartaDallaManoDellUtente);
        cambiaTurno();
    }

    void scartaCartaHandler(Carta cartaDellaMano) {
        partita.scarta(partita.getGiocatoreCorrente(), cartaDellaMano);
        cambiaTurno();
    }

    void cambiaTurno() {
        for (String giocatore : partita.getListaDeiGiocatori()) {
            System.out.println(giocatore);
        }
        System.out.println("Turno di " + partita.getGiocatoreCorrente() + "\n");
        partita.passaTurno();
        refreshData();
    }

    void refreshData() {
        piatto.getChildren().clear();
        buildView();
    }

    /**
     * Aggiorna le informazioni del giocatore e aggiunge i giusti bottoni
     * 
     * @param giocatore
     * @param containerPane
     */
    void updatePlayerPanel(Pane containerPane) {
        try {
            String giocatore = containerPane.getUserData().toString();

            boolean isCurrentPlayer = giocatore.equals(partita.getGiocatoreCorrente());
            Text existingText = getNomeCorrente(containerPane);

            if (existingText == null) {
                containerPane.getChildren().add(0, new Text(giocatore));
            } else {
                existingText.setText(giocatore);
            }

            Carta cartaInCimaCarta = partita.getCartaInCima(giocatore);
            if (cartaInCimaCarta != null) {
                getImmagineCorrente(containerPane).setImage(new Image("file:" + cartaInCimaCarta.getImmagine()));
                getImmagineCorrente(containerPane).setUserData(cartaInCimaCarta);
                // getImmagineCorrente(containerPane).setOnMouseReleased(event ->
                // rubaUnMazzoHandler(giocatore, event));
            } else {
                getImmagineCorrente(containerPane)
                        .setImage(new Image("file:src/main/resources/com/spacca/images/retro.png"));
            }

            if (isCurrentPlayer) {
                getButtonCorrente(containerPane).setText("Mostra le mie carte");
                getButtonCorrente(containerPane)
                        .setOnAction(
                                event -> System.out.println(partita.getManoDellUtente(partita.getGiocatoreCorrente())));
            } else {
                getButtonCorrente(containerPane).setText("Ruba a " + giocatore);
                getButtonCorrente(containerPane)
                        .setOnAction(event -> partita.rubaUnMazzo(partita.getGiocatoreCorrente(), giocatore));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    // private void rubaUnMazzoHandler(String giocatore, MouseEvent event) {
    // }

    ImageView getImmagineCorrente(Pane containerPane) {
        return containerPane.getChildren().stream()
                .filter(node -> node instanceof ImageView)
                .map(node -> (ImageView) node)
                .findFirst()
                .orElse(new ImageView());
    }

    Text getNomeCorrente(Pane containerPane) {
        return containerPane.getChildren().stream()
                .filter(node -> node instanceof Text)
                .map(node -> (Text) node)
                .findFirst()
                .orElse(new Text());
    }

    Button getButtonCorrente(Pane containerPane) {
        return containerPane.getChildren().stream()
                .filter(node -> node instanceof Button)
                .map(node -> (Button) node)
                .findFirst()
                .orElse(new Button());
    }

}
