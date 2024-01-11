package com.spacca.controller;

import java.util.ArrayList;
import java.util.List;

import com.spacca.asset.match.Partita;
import com.spacca.asset.utente.Amministratore;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.asset.utente.giocatore.Giocatore;
import com.spacca.database.FileHandler;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PartitaController {
    FileHandler fileHandler = new FileHandler();
    Amministratore amministratore = new Amministratore();
    Partita partita;
    AbstractGiocatore YOSHI = new Giocatore("Yoshi", "");
    AbstractGiocatore MARIO = new Giocatore("Mario", "");
    List<AbstractGiocatore> giocatoriDellaPartita = new ArrayList<>(); // Inizializzazione della lista

    @FXML
    private TextField codeTextField;

    @FXML
    void nuovaPartita() {

        giocatoriDellaPartita.add(YOSHI);
        giocatoriDellaPartita.add(MARIO);

        partita = amministratore.creaPartita(giocatoriDellaPartita);

        partita.nuovoTurno();

        System.out.println("Codice:\t" + partita.getCodice());

    }

    @FXML
    private void inviaCodice() {
        String codice = codeTextField.getText();
        // Esegui la tua funzione passando il codice come parametro
        caricaPartita(codice);
    }

    @FXML
    void risultatoPartita() {
        // partita.gioca(giocatore1, 2);
        System.out.println(
                "Carte di " + YOSHI.getUsername() + "\n" + partita.getManoDellUtente(YOSHI.getUsername()));
        System.out.println(
                "Carte di " + MARIO.getUsername() + "\n" + partita.getManoDellUtente(MARIO.getUsername()));
        System.out.println(partita.getCarteSulTavolo());
        System.out.println(partita.getRisultato());
    }

    @FXML
    void rubaUnMazzo() {
        partita.rubaUnMazzo(MARIO, YOSHI);
    }

    @FXML
    void rubaMezzoMazzo() {
        partita.rubaMezzoMazzo(MARIO, YOSHI);
    }

    @FXML
    void prendiUnaCartaDalTavoloYoshi() {
        try {
            partita.prendiCartaDaTavolo(YOSHI);
            // partita.salvaPartita();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    void prendiUnaCartaDalTavoloMario() {
        try {
            partita.prendiCartaDaTavolo(MARIO);
            // partita.salvaPartita();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void caricaPartita(String codicePartita) {
        partita = amministratore.caricaPartita(codicePartita);
    }
}
