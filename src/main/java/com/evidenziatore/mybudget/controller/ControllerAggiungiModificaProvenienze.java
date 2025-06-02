package com.evidenziatore.mybudget.controller;

import com.evidenziatore.mybudget.database.Database;
import com.evidenziatore.mybudget.database.entity.Provenienza;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ControllerAggiungiModificaProvenienze {

    public TextField textFieldProvenienza;
    public Button buttonAnnulla;
    @FXML
    private Button buttonConferma;

    Provenienza provenienza;

    public void setProvenienza(Provenienza provenienza) {
        this.provenienza = provenienza;
        buttonConferma.getStyleClass().remove(buttonConferma.getStyleClass().size() - 1);
        buttonConferma.getStyleClass().add("buttonDefaultBlu");
        buttonConferma.setText("Modifica");
        textFieldProvenienza.setText(provenienza.getValore());
    }

    @FXML
    public void initialize() {
        buttonConferma.getStyleClass().add("buttonConfermaVerde");
        buttonConferma.setText("Aggiungi");
        buttonConferma.setOnAction(event -> {
            if (provenienza != null) {
                Database.aggiornaRecord(
                        "provenienza",
                        new String[]{"valore"},
                        new String[]{textFieldProvenienza.getText()},
                        provenienza.getId().toString()
                );
            } else {
                Database.inserisciRecord(
                        "provenienza",
                        new String[]{"valore"},
                        new String[]{textFieldProvenienza.getText()}
                );
            }
            ((Stage) buttonConferma.getScene().getWindow()).close();
        });
        buttonAnnulla.setOnAction(event -> {
            ((Stage) buttonConferma.getScene().getWindow()).close();
        });
    }
}