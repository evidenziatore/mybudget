package com.evidenziatore.mybudget.controller;

import com.evidenziatore.mybudget.ApplicationMyBudget;
import com.evidenziatore.mybudget.database.Database;
import com.evidenziatore.mybudget.database.entity.Movimento;
import com.evidenziatore.mybudget.database.entity.Provenienza;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.List;

public class ControllerProvenienze {

    @FXML
    private TableView<Provenienza> tableViewProvenienze;

    @FXML
    private TableColumn<Provenienza, String> colId;

    @FXML
    private TableColumn<Provenienza, String> colValore;

    @FXML
    private TableColumn<Movimento, HBox> colAzioni;

    @FXML
    public void initialize() {
        // Imposta le colonne
        colId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        colId.setVisible(false);
        colValore.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValore()));
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
                + colAzioni.getPrefWidth()
                + 20;

        tableViewProvenienze.setMaxWidth(totalWidth);

        // Carica le provenienze
        List<Provenienza> provenienze = Database.getAllProvenienze();
        tableViewProvenienze.getItems().setAll(provenienze);
    }
}