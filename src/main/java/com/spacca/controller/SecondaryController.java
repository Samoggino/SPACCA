package com.spacca.controller;

import java.io.IOException;

import com.spacca.App;

import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("login");
    }
}