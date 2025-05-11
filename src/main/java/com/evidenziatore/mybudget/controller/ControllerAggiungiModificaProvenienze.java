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

    Provenienza provenienza;

    public void setProvenienza(Provenienza provenienza) {
        this.provenienza = provenienza;
    }

    @FXML
    public void initialize() {

    }
}