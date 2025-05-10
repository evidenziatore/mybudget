package com.evidenziatore.mybudget.controller;

import com.evidenziatore.mybudget.database.Database;
import com.evidenziatore.mybudget.database.entity.Provenienza;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class ControllerProvenienze {

    @FXML
    private TableView<Provenienza> tableViewProvenienze;

    @FXML
    private TableColumn<Provenienza, String> colId;

    @FXML
    private TableColumn<Provenienza, String> colValore;

    @FXML
    public void initialize() {
        // Imposta le colonne
        colId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        colValore.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValore()));

        double totalWidth = colId.getPrefWidth()
                + colValore.getPrefWidth()
                + 20;

        tableViewProvenienze.setMaxWidth(totalWidth);

        // Carica le provenienze
        List<Provenienza> provenienze = Database.getAllProvenienze();
        tableViewProvenienze.getItems().setAll(provenienze);
    }
}