package com.spacca.controller;

import java.util.ArrayList;
import java.util.List;

import com.spacca.asset.match.Partita;
import com.spacca.asset.utente.Amministratore;
import com.spacca.asset.utente.giocatore.AbstractGiocatore;
import com.spacca.asset.utente.giocatore.Giocatore;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PartitaController {
    Amministratore amministratore = new Amministratore();
    Partita partita;
    AbstractGiocatore YOSHI = new Giocatore("Yoshi", "", "");
    AbstractGiocatore MARIO = new Giocatore("Mario", "", "");
    List<AbstractGiocatore> giocatoriDellaPartita = new ArrayList<>(); // Inizializzazione della lista
    AbstractGiocatore giocatoreCorrente;

    @FXML
    private TextField codeTextField;

    @FXML
    private VBox buttonContainer;

    // Lista degli utenti
    private List<String> userList = new ArrayList<>();

    // Funzione chiamata all'inizializzazione del controller
    @FXML
    private void initialize() {
        // Esempio: popola la lista degli utenti
        giocatoriDellaPartita.add(YOSHI);
        giocatoriDellaPartita.add(MARIO);

        for (AbstractGiocatore giocatore : giocatoriDellaPartita) {
            userList.add(giocatore.getUsername());
        }

        // Crea dinamicamente i bottoni e aggiungili al container
        for (String username : userList) {
            Button button = new Button(username);
            button.setOnAction(this::handleButtonAction);
            buttonContainer.getChildren().add(button);
        }
    }

    public void setGiocatoreCorrente(AbstractGiocatore giocatoreCorrente) {
        this.giocatoreCorrente = giocatoreCorrente;
    }

    // Metodo gestore per tutti i bottoni
    private void handleButtonAction(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        stampa(sourceButton.getText());
    }

    // Funzione "stampa" che riceve una stringa come argomento e la stampa
    private void stampa(String text) {
        System.out.println("Hai premuto il bottone con l'username: " + text);
    }

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
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    void prendiUnaCartaDalTavoloMario() {
        try {
            partita.prendiCartaDaTavolo(MARIO);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void caricaPartita(String codicePartita) {
        partita = amministratore.caricaPartita(codicePartita);
    }

}
