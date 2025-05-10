package com.evidenziatore.mybudget.controller;

import com.evidenziatore.mybudget.database.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMyBudget implements Initializable {

    @FXML
    private VBox mainVbox;

    @Override
    public void initialize(URL url, ResourceBundle caricatoreRisorse) {
        Database.getAllMovimentiCompletamente();
    }

}