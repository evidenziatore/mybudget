package com.evidenziatore.mybudget.controller;

import com.evidenziatore.mybudget.ApplicationMyBudget;
import com.evidenziatore.mybudget.database.Database;
import com.evidenziatore.mybudget.database.entity.Movimento;
import com.evidenziatore.mybudget.database.entity.Prodotto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    private TableColumn<Prodotto, HBox> colAzioni;

    @FXML
    private Button buttonAggiungi;

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
            buttonElimina.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Conferma eliminazione");
                alert.setHeaderText("Sei sicuro di voler eliminare il prodotto\n"+cellData.getValue().toString()+"?");
                alert.setContentText("Questa azione non può essere annullata.");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img.png"))));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Database.eliminaRecord("prodotto", cellData.getValue().getId());
                    List<Prodotto> prodotti = Database.getAllProdotti();
                    tableViewProdotti.getItems().setAll(prodotti);
                }
            });
            buttonModifica.setOnAction(event -> {
                FXMLLoader loader = new FXMLLoader(ApplicationMyBudget.class.getResource("aggiungiModificaProdotto.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                ControllerAggiungiModificaProdotti controller = loader.getController();
                controller.setProdotto(cellData.getValue());

                Stage stage = new Stage();
                stage.setTitle("Modifica Prodotto");
                stage.setScene(new Scene(root));
                stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img.png"))));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            });
            HBox hBoxAzioni = new HBox(buttonModifica,buttonElimina);
            hBoxAzioni.setSpacing(5);
            return new javafx.beans.property.SimpleObjectProperty<>((HBox) hBoxAzioni);
        });
        buttonAggiungi.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(ApplicationMyBudget.class.getResource("aggiungiModificaProdotto.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Stage stage = new Stage();
            stage.setTitle("Aggiungi Prodotto");
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img.png"))));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
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