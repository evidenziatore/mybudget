package com.evidenziatore.mybudget.controller;

import com.evidenziatore.mybudget.ApplicationMyBudget;
import com.evidenziatore.mybudget.database.Database;
import com.evidenziatore.mybudget.database.entity.Movimento;
import com.evidenziatore.mybudget.database.entity.Prodotto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.List;

public class ControllerProdotti {

    @FXML
    private TableView<Prodotto> tableViewProdotti;

    @FXML
    private TableColumn<Prodotto, String> colId;

    @FXML
    private TableColumn<Prodotto, String> colValore;

    @FXML
    private TableColumn<Movimento, HBox> colAzioni;

    @FXML
    public void initialize() {
        // Imposta le colonne
        colId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        colValore.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValore()));
        colAzioni.setCellValueFactory(cellData -> {
            try {
                return new javafx.beans.property.SimpleObjectProperty<>(
                        new FXMLLoader(ApplicationMyBudget.class.getResource("azioni.fxml")).load()
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        double totalWidth = colId.getPrefWidth()
                + colValore.getPrefWidth()
                + colAzioni.getPrefWidth()
                + 20;

        tableViewProdotti.setMaxWidth(totalWidth);

        // Carica i prodotti
        List<Prodotto> prodotti = Database.getAllProdotti();
        tableViewProdotti.getItems().setAll(prodotti);
    }
}