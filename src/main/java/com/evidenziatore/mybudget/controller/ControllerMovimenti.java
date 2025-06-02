package com.evidenziatore.mybudget.controller;

import com.evidenziatore.mybudget.ApplicationMyBudget;
import com.evidenziatore.mybudget.database.Database;
import com.evidenziatore.mybudget.database.entity.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class ControllerMovimenti {

    public TextField textFieldValore;
    public TextField textFieldValore1;
    public TextField textFieldValutazione;
    public TextField textFieldValutazione1;
    public DatePicker datePickerData;
    public DatePicker datePickerData1;
    public ComboBox<Provenienza> comboBoxProvenienza;
    public ComboBox<Categoria> comboBoxCategoria;
    public ComboBox<Tipologia> comboBoxTipologia;
    public ComboBox<Prodotto> comboBoxProdotto;

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

    List<Movimento> movimentiList = new ArrayList<>();

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
        comboBoxProvenienza.setItems(FXCollections.observableArrayList(Database.getAllProvenienze()));
        comboBoxProvenienza.getItems().add(0, null);
        comboBoxCategoria.setItems(FXCollections.observableArrayList(Database.getAllCategorie()));
        comboBoxCategoria.getItems().add(0, null);
        comboBoxTipologia.setItems(FXCollections.observableArrayList(Database.getAllTipologie()));
        comboBoxTipologia.getItems().add(0, null);
        comboBoxProdotto.setItems(FXCollections.observableArrayList(Database.getAllProdotti()));
        comboBoxProdotto.getItems().add(0, null);
        textFieldValore.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*(\\.\\d*)?")) {
                return change;
            } else {
                return null;
            }
        }));
        textFieldValutazione.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            } else {
                return null;
            }
        }));
        textFieldValore1.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*(\\.\\d*)?")) {
                return change;
            } else {
                return null;
            }
        }));
        textFieldValutazione1.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            } else {
                return null;
            }
        }));
        textFieldValore.textProperty().addListener((obs, oldVal, newVal) -> {
            filterAction(null);
        });
        textFieldValore1.textProperty().addListener((obs, oldVal, newVal) -> {
            filterAction(null);
        });
        textFieldValutazione.textProperty().addListener((obs, oldVal, newVal) -> {
            filterAction(null);
        });
        textFieldValutazione1.textProperty().addListener((obs, oldVal, newVal) -> {
            filterAction(null);
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
                    Database.eliminaRecord("movimento_magazzino", cellData.getValue().getId());
                    movimentiList = Database.getAllMovimentiCompletamente();
                    filter();
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
                movimentiList = Database.getAllMovimentiCompletamente();
                filter();
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
            movimentiList = Database.getAllMovimentiCompletamente();
            filter();
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

        movimentiList = Database.getAllMovimentiCompletamente();
        filter();
    }

    public void filterAction(ActionEvent actionEvent) {
        filter();
    }

    private void filter() {
        String valoreText = textFieldValore.getText().trim();
        String valutazioneText = textFieldValutazione.getText().trim();
        String valoreText1 = textFieldValore1.getText().trim();
        String valutazioneText1 = textFieldValutazione1.getText().trim();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date dataInput = datePickerData.getValue() != null ? Date.from(datePickerData.getValue().atStartOfDay(defaultZoneId).toInstant()) : null;
        Date dataInput1 = datePickerData1.getValue() != null ? Date.from(datePickerData1.getValue().atStartOfDay(defaultZoneId).toInstant()) : null;
        Provenienza selProvenienza = comboBoxProvenienza.getValue();
        Categoria selCategoria = comboBoxCategoria.getValue();
        Tipologia selTipologia = comboBoxTipologia.getValue();
        Prodotto selProdotto = comboBoxProdotto.getValue();

        List<Movimento> filteredMovimenti = movimentiList.stream().filter(m -> {
            if (!valoreText.isEmpty()) {
                try {
                    double valoreFiltro = Double.parseDouble(valoreText);
                    if (m.getValore()<valoreFiltro) {
                        return false;
                    }
                } catch (NumberFormatException ex) {
                    return false;
                }
            }

            if (!valutazioneText.isEmpty()) {
                try {
                    double valutazioneFiltro = Double.parseDouble(valutazioneText);
                    if (m.getValutazione()<valutazioneFiltro) {
                        return false;
                    }
                } catch (NumberFormatException ex) {
                    return false;
                }
            }

            if (!valoreText1.isEmpty()) {
                try {
                    double valoreFiltro = Double.parseDouble(valoreText1);
                    if (m.getValore()>valoreFiltro) {
                        return false;
                    }
                } catch (NumberFormatException ex) {
                    return false;
                }
            }

            if (!valutazioneText1.isEmpty()) {
                try {
                    double valutazioneFiltro = Double.parseDouble(valutazioneText1);
                    if (m.getValutazione()>valutazioneFiltro) {
                        return false;
                    }
                } catch (NumberFormatException ex) {
                    return false;
                }
            }

            if (dataInput != null) {
                if (dataInput.after(m.getData())) {
                    return false;
                }
            }

            if (dataInput1 != null) {
                if (dataInput1.before(m.getData())) {
                    return false;
                }
            }

            if (selProvenienza != null) {
                if (!selProvenienza.getId().equals(m.getProvenienza().getId())) {
                    return false;
                }
            }

            if (selCategoria != null) {
                if (!selCategoria.getId().equals(m.getCategoria().getId())) {
                    return false;
                }
            }

            if (selTipologia != null) {
                if (!selTipologia.getId().equals(m.getTipologia().getId())) {
                    return false;
                }
            }

            if (selProdotto != null) {
                if (!selProdotto.getId().equals(m.getProdotto().getId())) {
                    return false;
                }
            }

            return true;
        }).collect(Collectors.toList());

        tableViewMovimenti.getItems().setAll(filteredMovimenti);
    }

}