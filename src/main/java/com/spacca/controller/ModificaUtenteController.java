package com.spacca.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.spacca.asset.utente.giocatore.Giocatore;
import com.spacca.database.GiocatoreHandler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ModificaUtenteController implements Initializable {

    @FXML
    private String utenteScelto;

    @FXML
    private Giocatore giocatoreScelto;

    @FXML
    private Button procedi;
    @FXML
    private Button indietro;

    @FXML
    private Label labelUsername;

    @FXML
    private TextField usernameField;

    @FXML
    private Label labelEmail;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }

    @FXML
    public void initController(String nomeFile) {
        this.utenteScelto = nomeFile;

        try {

            // in base al nome del file carico i dati dell'utente e apro il suo file per la
            // modifica

            String path = "src/main/resources/com/spacca/database/giocatori/" + nomeFile;
            GiocatoreHandler giocatoreHandler = new GiocatoreHandler();

            this.giocatoreScelto = giocatoreHandler.carica(path);

            passwordField.setPromptText(giocatoreScelto.getPassword());
            usernameField.setPromptText(giocatoreScelto.getUsername());
            emailField.setPromptText(giocatoreScelto.getEmail());

        } catch (NullPointerException e) {
            System.out.println("utente scelto null" + e);
        } catch (Exception e) {
            System.out.println("ERRORE" + e);
        }

    }

}
