package com.spacca.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.spacca.asset.match.Partita;
import com.spacca.asset.utente.Amministratore;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PartitaController implements Initializable {

    @FXML
    private TextField codeTextField;

    Amministratore amministratore = new Amministratore();
    Partita partita;
    // AbstractGiocatore YOSHI = new Giocatore("Yoshi", "", "");
    // AbstractGiocatore MARIO = new Giocatore("Mario", "", "");
    List<AbstractGiocatore> giocatoriDellaPartita;
    AbstractGiocatore giocatoreCorrente;

    public PartitaController() {
        // TODO Auto-generated constructor stub
    }

    public void initController(AbstractGiocatore giocatoreCorrente, List<AbstractGiocatore> giocatoriDellaPartita) {
        this.giocatoreCorrente = giocatoreCorrente;
        this.giocatoriDellaPartita = giocatoriDellaPartita;
        partita = amministratore.creaPartita(giocatoriDellaPartita); // oppure carica la partita
        partita.setTokenTurno(giocatoreCorrente.getUsername());
    }

    @FXML
    private VBox partitaControllerVBox;

    public void setVBox(VBox vBox) {
        this.partitaControllerVBox = vBox;
    }

    public void setGiocatoreCorrente(AbstractGiocatore giocatoreCorrente) {
        this.giocatoreCorrente = giocatoreCorrente;
    }

    @FXML
    void nuovaPartita() {

        partita.nuovoTurno();
        System.out.println(partita);

    }

    @FXML
    private void inviaCodice() {
        String codice = codeTextField.getText();
        caricaPartita(codice);
    }

    @FXML
    void risultatoPartita() {

        // System.out.println(
        // "Carte di " + YOSHI.getUsername() + "\n" +
        // partita.getManoDellUtente(YOSHI.getUsername()));
        // System.out.println(
        // "Carte di " + MARIO.getUsername() + "\n" +
        // partita.getManoDellUtente(MARIO.getUsername()));
        System.out.println(partita.getCarteSulTavolo());
        System.out.println(partita.getRisultato());
    }

    @FXML
    void rubaUnMazzo() {
        // partita.rubaUnMazzo(this.giocatoreCorrente, YOSHI);
    }

    @FXML
    void rubaMezzoMazzo() {
        // partita.rubaMezzoMazzo(this.giocatoreCorrente, YOSHI);
    }

    @FXML
    void prendiUnaCartaDalTavoloYoshi() {
        try {
            partita.prendiCartaDaTavolo(this.giocatoreCorrente);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    void prendiUnaCartaDalTavoloMario() {
        try {
            partita.prendiCartaDaTavolo(this.giocatoreCorrente);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void caricaPartita(String codicePartita) {
        partita = amministratore.caricaPartita(codicePartita);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
    }

}
