package com.evidenziatore.mybudget.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;

public class ControllerMyBudget {

    @FXML
    private VBox contentArea;

    @FXML
    private ListView<String> menuListView;

    @FXML
    public void initialize() {
        menuListView.setItems(FXCollections.observableArrayList(
                "Movimenti",
                "Prodotti",
                "Categorie",
                "Provenienze"
        ));

        // Seleziona "Movimenti" di default
        menuListView.getSelectionModel().select(0);
        showMovimenti();

        // Listener cambiamento selezione menu
        menuListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                switch (newVal) {
                    case "Movimenti" -> showMovimenti();
                    case "Prodotti" -> showProdotti();
                    case "Categorie" -> showCategorie();
                    case "Provenienze" -> showProvenienze(); // Nuovo caso
                }
            }
        });

        menuListView.setFixedCellSize(32);
        menuListView.prefHeightProperty().bind(
                Bindings.size(menuListView.getItems()).multiply(menuListView.getFixedCellSize()+1)
        );
    }

    private void showMovimenti() {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(new Label("Schermata Movimenti"));
    }

    private void showCategorie() {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(new Label("Schermata Categorie"));
    }

    private void showProdotti() {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(new Label("Schermata Prodotti"));
    }

    private void showProvenienze() {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(new Label("Schermata Provenienze"));
    }
}