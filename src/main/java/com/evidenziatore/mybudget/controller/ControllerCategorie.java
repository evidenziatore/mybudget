package com.evidenziatore.mybudget.controller;

import com.evidenziatore.mybudget.ApplicationMyBudget;
import com.evidenziatore.mybudget.database.Database;
import com.evidenziatore.mybudget.database.entity.Categoria;
import com.evidenziatore.mybudget.database.entity.Movimento;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.List;

public class ControllerCategorie {

    @FXML
    private TableView<Categoria> tableViewCategorie;

    @FXML
    private TableColumn<Categoria, String> colId;

    @FXML
    private TableColumn<Categoria, String> colValore;

    @FXML
    private TableColumn<Categoria, String> colImportanza;

    @FXML
    private TableColumn<Movimento, HBox> colAzioni;

    @FXML
    public void initialize() {
        // Imposta le colonne
        colId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        colValore.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValore()));
        colImportanza.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getImportanza().getValore()));
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
                + colImportanza.getPrefWidth()
                + colAzioni.getPrefWidth()
                + 20;

        tableViewCategorie.setMaxWidth(totalWidth);

        // Carica le categorie
        List<Categoria> categorie = Database.getAllCategorie();
        tableViewCategorie.getItems().setAll(categorie);
    }
}