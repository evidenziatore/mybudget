package com.evidenziatore.mybudget.controller;

import com.evidenziatore.mybudget.ApplicationMyBudget;
import com.evidenziatore.mybudget.database.Database;
import com.evidenziatore.mybudget.database.entity.Movimento;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ControllerMovimenti {

    @FXML
    private TableView<Movimento> tableViewMovimenti;

    @FXML
    private TableColumn<Movimento, Integer> colId;

    @FXML
    private TableColumn<Movimento, Date> colData;

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
    private TableColumn<Movimento, Double> colValore;

    @FXML
    private TableColumn<Movimento, Integer> colValutazione;

    @FXML
    private TableColumn<Movimento, Integer> colPeso;

    @FXML
    private TableColumn<Movimento, HBox> colAzioni;

    @FXML
    private Button buttonAggiungi;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setVisible(false);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        colData.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getData()));

        colData.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(formatter.format(item));
                }
            }
        });
        colValore.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValore()));
        colValutazione.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValutazione()));
        colPeso.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getProdotto().getPeso()));
        colTipologia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipologia().getValore()));
        colCategoria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoria().getValore()));
        colImportanza.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoria().getImportanza().getValore()));
        colProvenienza.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProvenienza().getValore()));
        colProdotto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProdotto().getValore()));
        colAzioni.setCellValueFactory(cellData -> {
                Button buttonModifica = new Button("Modifica");
                buttonModifica.getStyleClass().add("buttonDefaultBlu");
                Button buttonElimina = new Button("Elimina");
                buttonElimina.getStyleClass().add("buttonAnnullaRosso");
            buttonElimina.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Conferma eliminazione");
                alert.setHeaderText("Sei sicuro di voler eliminare il movimento\n"+cellData.getValue().toString()+"?");
                alert.setContentText("Questa azione non può essere annullata.");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                alert.getDialogPane().getScene().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
                stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img.png"))));
                Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                okButton.getStyleClass().add("buttonAnnullaRosso");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Database.eliminaRecord("movimento", cellData.getValue().getId());
                    List<Movimento> movimentiList = Database.getAllMovimentiCompletamente();
                    tableViewMovimenti.getItems().setAll(movimentiList);
                }
            });
            buttonModifica.setOnAction(event -> {
                FXMLLoader loader = new FXMLLoader(ApplicationMyBudget.class.getResource("aggiungiModificaMovimento.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                ControllerAggiungiModificaMovimenti controller = loader.getController();
                controller.setMovimento(cellData.getValue());

                Stage stage = new Stage();
                stage.setTitle("Modifica Movimento");
                stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img.png"))));
                Scene scene = new Scene(root);
                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                List<Movimento> movimentiList = Database.getAllMovimentiCompletamente();
                tableViewMovimenti.getItems().setAll(movimentiList);
            });
                HBox hBoxAzioni = new HBox(buttonModifica,buttonElimina);
                hBoxAzioni.setSpacing(5);
                return new javafx.beans.property.SimpleObjectProperty<>((HBox) hBoxAzioni);
        });
        buttonAggiungi.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(ApplicationMyBudget.class.getResource("aggiungiModificaMovimento.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Stage stage = new Stage();
            stage.setTitle("Aggiungi Movimento");
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img.png"))));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            List<Movimento> movimentiList = Database.getAllMovimentiCompletamente();
            tableViewMovimenti.getItems().setAll(movimentiList);
        });
        double totalWidth = colId.getPrefWidth()
                + colData.getPrefWidth()
                + colValore.getPrefWidth()
                + colValutazione.getPrefWidth()
                + colTipologia.getPrefWidth()
                + colCategoria.getPrefWidth()
                + colImportanza.getPrefWidth()
                + colProvenienza.getPrefWidth()
                + colProdotto.getPrefWidth()
                + colPeso.getPrefWidth()
                + colAzioni.getPrefWidth()
                - 120;

        tableViewMovimenti.setMaxWidth(totalWidth);

        ObservableList<Movimento> movimentiList = FXCollections.observableArrayList(Database.getAllMovimentiCompletamente());
        tableViewMovimenti.setItems(movimentiList);

    }
}