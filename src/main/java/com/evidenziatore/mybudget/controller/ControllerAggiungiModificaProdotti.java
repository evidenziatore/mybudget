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

    public TextField textFieldProdotto;
    public TextField textFieldPeso;
    public Button buttonAnnulla;
    @FXML
    private Button buttonConferma;

    Prodotto prodotto;

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
        buttonConferma.getStyleClass().removeLast();
        buttonConferma.getStyleClass().add("buttonDefaultBlu");
        buttonConferma.setText("Modifica");
        textFieldProdotto.setText(prodotto.getValore());
        textFieldPeso.setText(prodotto.getPeso().toString());
    }

    @FXML
    public void initialize() {
        buttonConferma.getStyleClass().add("buttonConfermaVerde");
        buttonConferma.setText("Aggiungi");
        textFieldPeso.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            } else {
                return null;
            }
        }));
        buttonConferma.setOnAction(event -> {
            if (prodotto != null) {
                Database.aggiornaRecord("prodotto", new String[]{"valore", "peso"},new String[]{textFieldProdotto.getText(), textFieldPeso.getText()}, prodotto.getId().toString());
            } else {
                Database.inserisciRecord("prodotto", new String[]{"valore", "peso"},new String[]{textFieldProdotto.getText(), textFieldPeso.getText()});
            }
            ((Stage) buttonConferma.getScene().getWindow()).close();
        });
        buttonAnnulla.setOnAction(event -> {
            ((Stage) buttonConferma.getScene().getWindow()).close();
        });
    }
}