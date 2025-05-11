package com.evidenziatore.mybudget.controller;

import com.evidenziatore.mybudget.database.Database;
import com.evidenziatore.mybudget.database.entity.Prodotto;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ControllerAggiungiModificaProdotti {

    @FXML
    private Button buttonConferma;

    Prodotto prodotto;

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
        buttonConferma.getStyleClass().removeLast();
            buttonConferma.getStyleClass().add("buttonDefaultBlu");
            buttonConferma.setText("Modifica");
    }

    @FXML
    public void initialize() {
        buttonConferma.getStyleClass().add("buttonConfermaVerde");
        buttonConferma.setText("Aggiungi");
    }
}