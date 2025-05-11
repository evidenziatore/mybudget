package com.evidenziatore.mybudget.controller;

import com.evidenziatore.mybudget.database.Database;
import com.evidenziatore.mybudget.database.entity.Categoria;
import com.evidenziatore.mybudget.database.entity.Movimento;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ControllerAggiungiModificaCategorie {

    @FXML
    private Button buttonConferma;

    Categoria categoria;

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
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