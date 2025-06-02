package com.evidenziatore.mybudget.controller;

import com.evidenziatore.mybudget.database.Database;
import com.evidenziatore.mybudget.database.entity.Categoria;
import com.evidenziatore.mybudget.database.entity.Importanza;
import com.evidenziatore.mybudget.database.entity.Movimento;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ControllerAggiungiModificaCategorie {

    public TextField textFieldCategoria;
    public ComboBox<Importanza> comboBoxImportanza;
    public Button buttonAnnulla;
    @FXML
    private Button buttonConferma;

    Categoria categoria;

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
        buttonConferma.getStyleClass().remove(buttonConferma.getStyleClass().size() - 1);
        buttonConferma.getStyleClass().add("buttonDefaultBlu");
        buttonConferma.setText("Modifica");
        textFieldCategoria.setText(categoria.getValore());
        for (Importanza importanza : comboBoxImportanza.getItems()) {
            if (Objects.equals(importanza.getId(), categoria.getImportanza().getId())) {
                comboBoxImportanza.getSelectionModel().select(importanza);
            }
        }
    }

    @FXML
    public void initialize() {
        buttonConferma.getStyleClass().add("buttonConfermaVerde");
        buttonConferma.setText("Aggiungi");
        comboBoxImportanza.setItems(FXCollections.observableArrayList(Database.getAllImportanze()));
        buttonConferma.setOnAction(event -> {
            if (categoria != null) {
                Database.aggiornaRecord(
                        "categoria_movimento",
                        new String[]{"valore", "importanza_id"},
                        new String[]{
                                textFieldCategoria.getText(),
                                comboBoxImportanza.getSelectionModel().getSelectedItem().getId().toString()
                        },
                        categoria.getId().toString()
                );
            } else {
                Database.inserisciRecord(
                        "categoria_movimento",
                        new String[]{"valore", "importanza_id"},
                        new String[]{
                                textFieldCategoria.getText(),
                                comboBoxImportanza.getSelectionModel().getSelectedItem().getId().toString()
                        }
                );
            }
            ((Stage) buttonConferma.getScene().getWindow()).close();
        });
        buttonAnnulla.setOnAction(event -> {
            ((Stage) buttonConferma.getScene().getWindow()).close();
        });
    }
}