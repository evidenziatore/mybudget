package com.evidenziatore.mybudget.controller;

import com.evidenziatore.mybudget.database.Database;
import com.evidenziatore.mybudget.database.entity.Movimento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControllerMovimenti {

    @FXML
    private TableView<Movimento> tableViewMovimenti;

    @FXML
    private TableColumn<Movimento, Integer> colId;

    @FXML
    private TableColumn<Movimento, String> colData;

    @FXML
    private TableColumn<Movimento, String> colTipologia;

    @FXML
    private TableColumn<Movimento, String> colCategoria;

    @FXML
    private TableColumn<Movimento, String> colImportanza;

    @FXML
    private TableColumn<Movimento, String> colProvenienza;

    @FXML
    private TableColumn<Movimento, String> colProdotto;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colData.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getData().toString()));
        colTipologia.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTipologia().getValore()));
        colCategoria.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCategoria().getValore()));
        colImportanza.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCategoria().getImportanza().getValore()));
        colProvenienza.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getProvenienza().getValore()));
        colProdotto.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getProdotto().getValore()));

        double totalWidth = colId.getPrefWidth()
                + colData.getPrefWidth()
                + colTipologia.getPrefWidth()
                + colCategoria.getPrefWidth()
                + colImportanza.getPrefWidth()
                + colProvenienza.getPrefWidth()
                + colProdotto.getPrefWidth()
                + 20;

        tableViewMovimenti.setMaxWidth(totalWidth);

        ObservableList<Movimento> movimentiList = FXCollections.observableArrayList(Database.getAllMovimentiCompletamente());

        tableViewMovimenti.setItems(movimentiList);
    }
}