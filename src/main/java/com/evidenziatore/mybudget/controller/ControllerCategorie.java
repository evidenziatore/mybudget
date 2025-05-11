package com.evidenziatore.mybudget.controller;

import com.evidenziatore.mybudget.ApplicationMyBudget;
import com.evidenziatore.mybudget.database.Database;
import com.evidenziatore.mybudget.database.entity.Categoria;
import com.evidenziatore.mybudget.database.entity.Movimento;
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
    private TableColumn<Categoria, HBox> colAzioni;

    @FXML
    private Button buttonAggiungi;

    @FXML
    public void initialize() {
        // Imposta le colonne
        colId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        colId.setVisible(false);
        colValore.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValore()));
        colImportanza.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getImportanza().getValore()));
        colAzioni.setCellValueFactory(cellData -> {
            Button buttonModifica = new Button("Modifica");
            buttonModifica.getStyleClass().add("buttonDefaultBlu");
            Button buttonElimina = new Button("Elimina");
            buttonElimina.getStyleClass().add("buttonAnnullaRosso");
            buttonElimina.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Conferma eliminazione");
                alert.setHeaderText("Sei sicuro di voler eliminare la categoria\n"+cellData.getValue().toString()+"?");
                alert.setContentText("Questa azione non può essere annullata.");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                alert.getDialogPane().getScene().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
                stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img.png"))));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Database.eliminaRecord("categoria", cellData.getValue().getId());
                    List<Categoria> categorie = Database.getAllCategorie();
                    tableViewCategorie.getItems().setAll(categorie);
                }
            });
            buttonModifica.setOnAction(event -> {
                FXMLLoader loader = new FXMLLoader(ApplicationMyBudget.class.getResource("aggiungiModificaCategoria.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                ControllerAggiungiModificaCategorie controller = loader.getController();
                controller.setCategoria(cellData.getValue());

                Stage stage = new Stage();
                stage.setTitle("Modifica Categoria");
                stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img.png"))));
                Scene scene = new Scene(root);
                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            });
            HBox hBoxAzioni = new HBox(buttonModifica,buttonElimina);
            hBoxAzioni.setSpacing(5);
            return new javafx.beans.property.SimpleObjectProperty<>((HBox) hBoxAzioni);
        });
        buttonAggiungi.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(ApplicationMyBudget.class.getResource("aggiungiModificaCategoria.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Stage stage = new Stage();
            stage.setTitle("Aggiungi Categoria");
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img.png"))));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        });
        double totalWidth = colId.getPrefWidth()
                + colValore.getPrefWidth()
                + colImportanza.getPrefWidth()
                + colAzioni.getPrefWidth()
                - 120;

        tableViewCategorie.setMaxWidth(totalWidth);

        // Carica le categorie
        List<Categoria> categorie = Database.getAllCategorie();
        tableViewCategorie.getItems().setAll(categorie);
    }
}