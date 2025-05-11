package com.evidenziatore.mybudget.controller;

import com.evidenziatore.mybudget.ApplicationMyBudget;
import com.evidenziatore.mybudget.database.Database;
import com.evidenziatore.mybudget.database.entity.Movimento;
import com.evidenziatore.mybudget.database.entity.Provenienza;
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

public class ControllerProvenienze {

    @FXML
    private TableView<Provenienza> tableViewProvenienze;

    @FXML
    private TableColumn<Provenienza, String> colId;

    @FXML
    private TableColumn<Provenienza, String> colValore;

    @FXML
    private TableColumn<Provenienza, HBox> colAzioni;

    @FXML
    private Button buttonAggiungi;

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
            buttonElimina.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Conferma eliminazione");
                alert.setHeaderText("Sei sicuro di voler eliminare la provenienza\n"+cellData.getValue().toString()+"?");
                alert.setContentText("Questa azione non può essere annullata.");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                alert.getDialogPane().getScene().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
                stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img.png"))));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Database.eliminaRecord("provenienza", cellData.getValue().getId());
                    List<Provenienza> provenienze = Database.getAllProvenienze();
                    tableViewProvenienze.getItems().setAll(provenienze);
                }
            });
            buttonModifica.setOnAction(event -> {
                FXMLLoader loader = new FXMLLoader(ApplicationMyBudget.class.getResource("aggiungiModificaProvenienza.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                ControllerAggiungiModificaProvenienze controller = loader.getController();
                controller.setProvenienza(cellData.getValue());

                Stage stage = new Stage();
                stage.setTitle("Modifica Provenienza");
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
            FXMLLoader loader = new FXMLLoader(ApplicationMyBudget.class.getResource("aggiungiModificaProvenienza.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Stage stage = new Stage();
            stage.setTitle("Aggiungi Provenienza");
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img.png"))));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        });
        double totalWidth = colId.getPrefWidth()
                + colValore.getPrefWidth()
                + colAzioni.getPrefWidth()
                - 120;

        tableViewProvenienze.setMaxWidth(totalWidth);

        // Carica le provenienze
        List<Provenienza> provenienze = Database.getAllProvenienze();
        tableViewProvenienze.getItems().setAll(provenienze);
    }
}