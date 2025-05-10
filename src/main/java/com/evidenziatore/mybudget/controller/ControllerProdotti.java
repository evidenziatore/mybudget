package com.evidenziatore.mybudget.controller;

import com.evidenziatore.mybudget.ApplicationMyBudget;
import com.evidenziatore.mybudget.database.Database;
import com.evidenziatore.mybudget.database.entity.Movimento;
import com.evidenziatore.mybudget.database.entity.Prodotto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
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
    private TableColumn<Prodotto, String> colPeso;

    @FXML
    private TableColumn<Movimento, HBox> colAzioni;

    @FXML
    public void initialize() {
        // Imposta le colonne
        colId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        colId.setVisible(false);
        colValore.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValore()));
        colPeso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPeso()));
        colAzioni.setCellValueFactory(cellData -> {
            Button buttonModifica = new Button("Modifica");
            buttonModifica.getStyleClass().add("buttonDefaultBlu");
            Button buttonElimina = new Button("Elimina");
            buttonElimina.getStyleClass().add("buttonAnnullaRosso");
            //TODO azioni
            HBox hBoxAzioni = new HBox(buttonModifica,buttonElimina);
            hBoxAzioni.setSpacing(5);
            return new javafx.beans.property.SimpleObjectProperty<>((HBox) hBoxAzioni);
        });
        double totalWidth = colId.getPrefWidth()
                + colValore.getPrefWidth()
                + colPeso.getPrefWidth()
                + colAzioni.getPrefWidth()
                - 120;

        tableViewProdotti.setMaxWidth(totalWidth);

        // Carica i prodotti
        List<Prodotto> prodotti = Database.getAllProdotti();
        tableViewProdotti.getItems().setAll(prodotti);
    }
}