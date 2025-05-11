package com.evidenziatore.mybudget.controller;

import com.evidenziatore.mybudget.database.Database;
import com.evidenziatore.mybudget.database.entity.Movimento;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class ControllerAggiungiModificaMovimenti {

    Movimento movimento;

    public void setMovimento(Movimento movimento) {
        this.movimento = movimento;
    }

    @FXML
    public void initialize() {

    }
}