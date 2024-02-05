package com.spacca.controller;

import java.util.ArrayList;
import java.util.List;

import com.spacca.asset.carte.Carta;
import com.spacca.asset.carte.Nome;
import com.spacca.asset.match.Partita;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.asset.utente.giocatore.SmartCPU;
import com.spacca.asset.utente.giocatore.StupidCPU;
import com.spacca.database.GiocatoreHandler;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
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
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

public class TavoloController {

    @FXML
    public Pane currentPlayerPane, playerOnTopPane, playerOnLeftPane, playerOnRightPane, overlay, tavolo;

    @FXML
    TextFlow classificaFlowPane;

    @FXML
    public GridPane piatto;

    @FXML
    public FlowPane playerHand;

    @FXML
    public Button eliminaPartitaButton = new Button("Elimina partita");

    @FXML
    public Text andTheWinnerIs, risultatoOverlay;

    private Partita partita;
    private Carta cartaDelTavolo, cartaDellaMano;
    List<AbstractGiocatore> giocatori = new ArrayList<>();
    String userCorrente;
    AbstractGiocatore giocatoreCorrente;
    boolean isTorneo;
    int preventLoop = 0;

    public void initController(Partita partita, boolean isTorneo) {
        try {
            this.isTorneo = isTorneo;
            this.partita = partita;

            buildView();

        } catch (Exception e) {
            System.err.println("ERRORE (initController):\t\t " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void checkCPU() {

        try {
            preventLoop++;
            if (preventLoop < 35)
                switch (this.giocatoreCorrente.getType()) {
                    case "SmartCPU":
                        ((SmartCPU) giocatoreCorrente).gioca(partita);
                        cambiaTurno();
                        break;

                    case "StupidCPU":
                        ((StupidCPU) giocatoreCorrente).gioca(partita);
                        cambiaTurno();
                        break;

                    default:
                        break;
                }
        } catch (Exception e) {
            System.err.println("ERRORE (checkCPU):\t\t " + e.getMessage());
            e.printStackTrace();
        }
    }

    void buildView() {

        if (!partita.hasWinner()) {

            giocatoreCorrente = new GiocatoreHandler().carica(partita.getGiocatoreCorrente());
            userCorrente = giocatoreCorrente.getUsername();

            overlay.setVisible(false);
            try {
                if (partita.giocatoriNonHannoCarteInMano()) {
                    partita.nuovoTurno();
                }

                if (partita.getCarteSulTavolo().size() == 40) {
                    partita.getVincitore();
                    attesaEventoHandler();
                    return;
                } else {
                    checkCPU();
                }

                buildGiocatore();
                buildMano();
                buildTavolo();
                buildClassifica();

                buildOverlay();

            } catch (Exception e) {
                System.err.println("ERRORE (buildView):\t\t " + e.getMessage());
                e.printStackTrace();
            }
        }

    }

    /**
     * Questo metodo serve per evitare un loop infinito nelle chiamate della CPU
     */
    void attesaEventoHandler() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            // Questo è l'evento che viene eseguito dopo 5 secondi
            // Non esegue nulla qui, solo perde del tempo
        }));

        // Impostazione del ciclo dell'animazione a INDEFINITE per far sì che l'evento
        // si ripeta all'infinito
        timeline.setCycleCount(Animation.INDEFINITE);

        // Avvio dell'animazione
        timeline.play();
    }

    void buildMano() {
        playerHand.getChildren().clear();

        for (Carta cartaDellaMano : partita.getManoDellUtente(userCorrente).getCarteNelMazzo()) {
            ImageView cartaView = createCartaImageView(cartaDellaMano);
            cartaView.setFitWidth(0.45 * cartaView.getImage().getWidth());
            cartaView.setFitHeight(0.45 * cartaView.getImage().getHeight());

            // Aggiungi il gestore di eventi per iniziare il trascinamento
            cartaView.setOnMouseClicked(event -> assoPrendeTuttoHandler(cartaDellaMano));
            cartaView.setOnDragDetected(event -> iniziaTrascinamento(cartaDellaMano, cartaView));

            FlowPane.setMargin(cartaView, new Insets(5));

            playerHand.getChildren().add(cartaView);
        }
    }

    void buildTavolo() {
        int maxCartePerRiga = 8;

        int colonna = 0;
        int riga = 0;

        // Definisci la quantità di spazio tra le carte
        double spazioTraCarte = 10.0;

        for (Carta carta : partita.getCarteSulTavolo().getCarteNelMazzo()) {
            ImageView cartaView = createCartaImageView(carta);
            cartaView.setFitWidth(0.4 * cartaView.getImage().getWidth());
            cartaView.setFitHeight(0.4 * cartaView.getImage().getHeight());

            // Aggiungi uno spazio tra le carte tramite un margine
            GridPane.setMargin(cartaView, new Insets(spazioTraCarte));

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

    @FXML
    void buildClassifica() {

        classificaFlowPane.getChildren().clear();
        classificaFlowPane.setVisible(true);

        classificaFlowPane.getChildren().add(new Text("Classifica:\n"));
        partita.getClassifica().forEach(
                (giocatore, punti) -> classificaFlowPane.getChildren()
                        .add(new Text(giocatore + ": " + punti + " punti" + "\n")));

        for (String giocatore : partita.getListaDeiGiocatori()) {
            if (partita.has2Bastoni(giocatore)) {
                classificaFlowPane.getChildren().add(new Text(giocatore + " ha 2 bastoni" + "\n"));
            }
        }

    }

    void buildOverlay() {
        try {
            if (partita.getCarteSulTavolo().size() == 0
                    && partita.getMazzoDiGioco().size() == 0
                    && partita.giocatoriNonHannoCarteInMano()) {
                overlay.setVisible(true);
                andTheWinnerIs.setText(partita.getVincitore());

                String classifica = partita.classifica.toString();

                // Centra il testo orizzontalmente e verticalmente

                risultatoOverlay.setText(classifica);
                risultatoOverlay.setTextAlignment(TextAlignment.CENTER);

                if (isTorneo) {
                    eliminaPartitaButton.setVisible(false);
                }
                preventLoop = 100;

            }

        } catch (Exception e) {
            System.err.println("ERRORE (buildOverlay):\t\t " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void eliminaPartita() {
        partita.fine();
        eliminaPartitaButton.setVisible(false);
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

            if (nodoColpito instanceof ImageView) {
                ImageView cartaSottoIlMouse = (ImageView) nodoColpito;
                this.cartaDelTavolo = (Carta) cartaSottoIlMouse.getUserData();

                prendiCartaHandler(this.cartaDelTavolo, this.cartaDellaMano);
            }

            if (nodoColpito instanceof GridPane) {
                scartaCartaHandler(this.cartaDellaMano);
            }

        } catch (NullPointerException e) {
            System.err.println("ERRORE (rilasciaTrascinamento - NullPointerException):\t\t " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("ERRORE (rilasciaTrascinamento):\t\t " + e.getMessage());
            e.printStackTrace();
        }
    }

    ImageView createCartaImageView(Carta carta) {
        String immaginePath = "file:" + carta.getImmagine();
        Image immagine = new Image(immaginePath);
        ImageView cartaView = new ImageView(immagine);

        // Aggiungi i dati della carta all'ImageView
        cartaView.setUserData(carta);

        return cartaView;
    }

    void assoPrendeTuttoHandler(Carta cartaDellaMano) {

        if (cartaDellaMano.getNome().equals(Nome.ASSO)) {
            if (partita.getCarteSulTavolo().size() == 0) {
                scartaCartaHandler(cartaDellaMano);
            } else {
                giocatoreCorrente.assoPrendeTutto(partita, cartaDellaMano);
                cambiaTurno();
            }
        }
    }

    void prendiCartaHandler(Carta cartaDiDestinazione, Carta cartaDallaManoDellUtente) {
        boolean cartaPresa = giocatoreCorrente.prendi(partita, cartaDiDestinazione, cartaDallaManoDellUtente);
        if (cartaPresa) {
            cambiaTurno();
        }
    }

    void scartaCartaHandler(Carta cartaDellaMano) {
        giocatoreCorrente.scarta(partita, cartaDellaMano);
        cambiaTurno();
    }

    public void cambiaTurno() {

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
            String giocatoreDelPane = (String) containerPane.getUserData();

            boolean isCurrentPlayer = giocatoreDelPane.equals(userCorrente);

            getNomeCorrente(containerPane)
                    .setText(giocatoreDelPane + "\n" + partita.getPreseDellUtente(giocatoreDelPane).size() + " carte");
            Carta cartaInCimaCarta = partita.getCartaInCima(giocatoreDelPane);

            // Rimuovi il gestore di eventi setOnDragDetected
            getImmagineCorrente(containerPane).setOnDragDetected(null);

            if (cartaInCimaCarta != null) {
                getImmagineCorrente(containerPane).setImage(new Image("file:" + cartaInCimaCarta.getImmagine()));
                getImmagineCorrente(containerPane).setUserData(cartaInCimaCarta);

                // Aggiungi il nuovo gestore di eventi setOnMouseDragReleased
                getImmagineCorrente(containerPane).setOnMouseDragReleased(event -> {
                    if (isCardOnTop(getImmagineCorrente(containerPane), containerPane)
                            && !isCurrentPlayer) {
                        rubaUnMazzoHandler(giocatoreDelPane,
                                cartaDellaMano, cartaInCimaCarta);
                    }
                });

            } else {
                // serve per evitare che l'immagine della carta rimanga in cache e venga
                // visualizzata per tutti i giocatori successivi
                getImmagineCorrente(containerPane)
                        .setImage(new Image("file:src/main/resources/com/spacca/images/retro.png"));
            }

        } catch (Exception e) {
            System.err.println("ERRORE (updatePlayerPanel):\t\t " + e.getMessage());
            e.printStackTrace();
        }

    }

    private boolean isCardOnTop(ImageView cartaImageView, Pane pane) {
        try {
            Carta cartaSottoIlMouse = (Carta) cartaImageView.getUserData();
            return cartaSottoIlMouse.equals(partita.getCartaInCima((String) pane.getUserData()));
        } catch (NullPointerException e) {
            return false;
        }
    }

    public void rubaUnMazzoHandler(String scammato, Carta cartaCheRuba, Carta cartaInCima) {

        if (cartaCheRuba.getNome().equals(Nome.SETTE)) {
            trascinoUnSette(scammato, cartaCheRuba);
            cambiaTurno();
        } else if (cartaCheRuba.getNome().equals(cartaInCima.getNome()) && userCorrente != scammato) {
            giocatoreCorrente.rubaUnMazzo(partita, scammato, cartaCheRuba);
            cambiaTurno();
        }
    }

    public void trascinoUnSette(String scammato, Carta cartaCheRuba) {
        switch (cartaCheRuba.getSeme()) {

            case DENARA:
                giocatoreCorrente.rubaMezzoMazzo(partita, scammato, cartaCheRuba);
                break;

            case SPADE:
                giocatoreCorrente.rubaMezzoMazzo(partita, scammato, cartaCheRuba);
                break;

            case BASTONI:
                giocatoreCorrente.rubaUnMazzo(partita, scammato, cartaCheRuba);
                break;

            case COPPE:
                giocatoreCorrente.rubaUnMazzo(partita, scammato, cartaCheRuba);
                break;

            default:
                break;
        }
    }

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
}
