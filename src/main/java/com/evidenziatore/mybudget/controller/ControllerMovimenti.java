package com.evidenziatore.mybudget.controller;

import com.evidenziatore.mybudget.ApplicationMyBudget;
import com.evidenziatore.mybudget.database.Database;
import com.evidenziatore.mybudget.database.entity.Movimento;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.io.IOException;

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
    private TableColumn<Movimento, HBox> colAzioni;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colData.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getData().toString()));
        colTipologia.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTipologia().getValore()));
        colCategoria.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCategoria().getValore()));
        colImportanza.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCategoria().getImportanza().getValore()));
        colProvenienza.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getProvenienza().getValore()));
        colProdotto.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getProdotto().getValore()));
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
                + colData.getPrefWidth()
                + colTipologia.getPrefWidth()
                + colCategoria.getPrefWidth()
                + colImportanza.getPrefWidth()
                + colProvenienza.getPrefWidth()
                + colProdotto.getPrefWidth()
                + colAzioni.getPrefWidth()
                + 20;

        tableViewMovimenti.setMaxWidth(totalWidth);

        ObservableList<Movimento> movimentiList = FXCollections.observableArrayList(Database.getAllMovimentiCompletamente());

        tableViewMovimenti.setItems(movimentiList);

    }
}