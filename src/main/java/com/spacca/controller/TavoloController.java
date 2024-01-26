package com.spacca.controller;

import java.util.ArrayList;
import java.util.List;

import com.spacca.asset.carte.Carta;
import com.spacca.asset.carte.Nome;
import com.spacca.asset.match.Partita;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.database.GiocatoreHandler;
import com.spacca.database.Handler;
import com.spacca.database.PartitaHandler;

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

public class TavoloController {

    private Partita partita;
    private Carta cartaDelTavolo, cartaDellaMano;
    private List<AbstractGiocatore> giocatori = new ArrayList<>();
    private Handler giocatoreHandler = new GiocatoreHandler();

    @FXML
    public Pane currentPlayerPane, playerOnTopPane, playerOnLeftPane, playerOnRightPane, overlay, tavolo;

    @FXML
    public GridPane piatto;

    @FXML
    public ImageView playerOnLeftImage, playerOnTopImage, playerOnRightImage;

    @FXML
    public FlowPane playerHand;

    @FXML
    public Text andTheWinnerIs, risultatoOverlay;

    public void initController(Partita partita) {
        try {

            this.partita = partita;
            System.out.println("Partita: " + partita.getCodice());

            for (String username : partita.getListaDeiGiocatori()) {
                giocatori.add((AbstractGiocatore) giocatoreHandler.carica(username));
                System.out.println("Giocatore: " + username);
            }

            buildView();

        } catch (Exception e) {
            System.err.println("ERRORE (initController):\t\t " + e.getMessage());
            e.printStackTrace();
        }
    }

    void buildView() {
        overlay.setVisible(false);
        try {
            if (partita.getVincitore() != null) {
                buildOverlay();
            } else if (partita.isNecessarioRidistribuireLeCarte()) {
                partita.nuovoTurno();
            }

            buildGiocatore();
            buildHand();
            buildTable();

            buildOverlay();

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
            cartaView.setOnMouseClicked(event -> assoPrendeTuttoHandler(cartaDellaMano));
            cartaView.setOnDragDetected(event -> iniziaTrascinamento(cartaDellaMano, cartaView));

            FlowPane.setMargin(cartaView, new Insets(5));

            playerHand.getChildren().add(cartaView);
        }
    }

    void buildTable() {
        int maxCartePerRiga = 6;

        int colonna = 0;
        int riga = 0;

        // Definisci la quantità di spazio tra le carte
        double spazioTraCarte = 10.0;

        for (Carta carta : partita.getCarteSulTavolo().getCarteNelMazzo()) {
            ImageView cartaView = createCartaImageView(carta);
            cartaView.setFitWidth(0.5 * cartaView.getImage().getWidth());
            cartaView.setFitHeight(0.5 * cartaView.getImage().getHeight());

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

    void buildOverlay() {
        // FIXME: la stampa del andTheWinnerIs non funziona, non viene visualizzato il
        // nome del vincitore
        try {
            if (partita.getCarteSulTavolo().getCarteNelMazzo().size() == 0
                    && partita.getMazzoDiGioco().getCarteNelMazzo().size() == 0) {
                overlay.setVisible(true);
                andTheWinnerIs.setText(partita.getVincitore());

                String risultato = partita.getRisultato();

                // Centra il testo orizzontalmente e verticalmente

                risultatoOverlay.setText(risultato);
                risultatoOverlay.setTextAlignment(TextAlignment.CENTER);
                finePartitaHandler();
            }

        } catch (Exception e) {
            System.err.println("ERRORE (buildOverlay):\t\t " + e.getMessage());
            e.printStackTrace();
        }
    }

    void finePartitaHandler() {
        // rimuovi il codice dalle liste dei giocatori
        for (AbstractGiocatore abstractGiocatore : giocatori) {
            abstractGiocatore.getListaCodiciPartite().remove(partita.getCodice());
            giocatoreHandler.salva(abstractGiocatore, abstractGiocatore.getUsername());
        }

        // elimina il file della partita
        Handler partitaHandler = new PartitaHandler();
        try {
            partitaHandler.elimina(partita.getCodice());
        } catch (UnsupportedOperationException e) {
            System.err.println("Il metodo non è andora stato implementato");
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

            prendiCartaHandler(this.cartaDelTavolo, this.cartaDellaMano);
        }
    }

    void cartaLasciataSuTavolo(Node nodoColpito) {
        if (nodoColpito instanceof GridPane) {
            // System.out.println("Puoi scartare " + this.cartaDellaMano);
            scartaCartaHandler(this.cartaDellaMano);
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
            partita.assoPrendeTutto(partita.getGiocatoreCorrente(), cartaDellaMano);
            System.out.println("Puoi prendere tutto con " + cartaDellaMano);
            cambiaTurno();
        }
    }

    void prendiCartaHandler(Carta cartaDiDestinazione, Carta cartaDallaManoDellUtente) {

        System.out.println(cartaDiDestinazione);

        boolean cartaPresa = partita.prendiCartaConCartaDellaMano(partita.getGiocatoreCorrente(), cartaDiDestinazione,
                cartaDallaManoDellUtente);
        if (cartaPresa) {
            cambiaTurno();
        }
        // finePartitaHandler();
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

            ImageView cartaImageView = getImmagineCorrente(containerPane);

            // Rimuovi il gestore di eventi setOnDragDetected
            cartaImageView.setOnDragDetected(null);

            if (cartaInCimaCarta != null) {
                getImmagineCorrente(containerPane).setImage(new Image("file:" + cartaInCimaCarta.getImmagine()));
                getImmagineCorrente(containerPane).setUserData(cartaInCimaCarta);

                // Aggiungi il nuovo gestore di eventi setOnMouseDragReleased
                cartaImageView.setOnMouseDragReleased(event -> {
                    if (isCardOnTop(cartaImageView, containerPane)
                            && !giocatore.equals(partita.getGiocatoreCorrente())) {
                        rubaUnMazzoHandler(partita.getGiocatoreCorrente(), giocatore,
                                cartaDellaMano, cartaInCimaCarta);
                    }
                });

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
                // Aggiungi il nuovo gestore di eventi setOnMouseDragReleased
                if (cartaImageView != null) {

                    cartaImageView.setOnMouseDragReleased(event -> {
                        if (isCardOnTop(cartaImageView, containerPane)
                                && !giocatore.equals(partita.getGiocatoreCorrente())) {
                            rubaUnMazzoHandler(partita.getGiocatoreCorrente(), giocatore,
                                    cartaDellaMano, cartaInCimaCarta);
                        }
                    });
                }

            }
        } catch (Exception e) {
            System.err.println("ERRORE (updatePlayerPanel):\t\t " + e.getMessage());
            e.printStackTrace();
        }

    }

    private void rubaUnMazzoHandler(String giocatoreCorrente, String giocatore, Carta cartaCheRuba, Carta cartaInCima) {

        if (cartaCheRuba.getNome().equals(cartaInCima.getNome()) && giocatoreCorrente != giocatore) {
            partita.rubaUnMazzo(giocatoreCorrente, giocatore, cartaCheRuba);
            System.out.println("Hai rubato mezzo mazzo a " + giocatore);
            cambiaTurno();
        } else {
            System.out.println("Non puoi rubare il mazzo a " + giocatore + " con " + cartaCheRuba);
        }
    }

    private boolean isCardOnTop(ImageView cartaImageView, Pane pane) {
        Carta cartaSottoIlMouse = (Carta) cartaImageView.getUserData();
        return cartaSottoIlMouse.equals(partita.getCartaInCima((String) pane.getUserData()));
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

    Button getButtonCorrente(Pane containerPane) {
        return containerPane.getChildren().stream()
                .filter(node -> node instanceof Button)
                .map(node -> (Button) node)
                .findFirst()
                .orElse(new Button());
    }

}
