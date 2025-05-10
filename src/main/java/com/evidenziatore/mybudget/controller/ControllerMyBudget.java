package com.evidenziatore.mybudget.controller;

import com.evidenziatore.mybudget.ApplicationMyBudget;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;

import java.io.IOException;

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
        try {
            FXMLLoader loader =  new FXMLLoader(ApplicationMyBudget.class.getResource("movimenti.fxml"));
            Parent root = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showCategorie() {
        try {
            FXMLLoader loader =  new FXMLLoader(ApplicationMyBudget.class.getResource("categorie.fxml"));
            Parent root = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showProdotti() {
        try {
            FXMLLoader loader =  new FXMLLoader(ApplicationMyBudget.class.getResource("prodotti.fxml"));
            Parent root = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showProvenienze() {
        try {
            FXMLLoader loader =  new FXMLLoader(ApplicationMyBudget.class.getResource("provenienze.fxml"));
            Parent root = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}