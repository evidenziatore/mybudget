package com.evidenziatore.mybudget.controller;

import com.evidenziatore.mybudget.database.Database;
import com.evidenziatore.mybudget.database.entity.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class ControllerAggiungiModificaMovimenti {

    public TextField textFieldValore;
    public TextField textFieldValutazione;
    public DatePicker datePickerData;
    public ComboBox<Provenienza> comboBoxProvenienza;
    public ComboBox<Categoria> comboBoxCategoria;
    public ComboBox<Tipologia> comboBoxTipologia;
    public ComboBox<Prodotto> comboBoxProdotto;
    public Button buttonAnnulla;
    @FXML
    private Button buttonConferma;

    Movimento movimento;

    public void setMovimento(Movimento movimento) {
        this.movimento = movimento;
        buttonConferma.getStyleClass().removeLast();
        buttonConferma.getStyleClass().add("buttonDefaultBlu");
        buttonConferma.setText("Modifica");
        textFieldValore.setText(movimento.getValore().toString());
        textFieldValutazione.setText(movimento.getValutazione().toString());
        for (Provenienza provenienza : comboBoxProvenienza.getItems()) {
            if (Objects.equals(provenienza.getId(), movimento.getProvenienza().getId())) {
                comboBoxProvenienza.getSelectionModel().select(provenienza);
            }
        }
        for (Categoria categoria : comboBoxCategoria.getItems()) {
            if (Objects.equals(categoria.getId(), movimento.getCategoria().getId())) {
                comboBoxCategoria.getSelectionModel().select(categoria);
            }
        }
        for (Tipologia tipologia : comboBoxTipologia.getItems()) {
            if (Objects.equals(tipologia.getId(), movimento.getTipologia().getId())) {
                comboBoxTipologia.getSelectionModel().select(tipologia);
            }
        }
        for (Prodotto prodotto : comboBoxProdotto.getItems()) {
            if (Objects.equals(prodotto.getId(), movimento.getProdotto().getId())) {
                comboBoxProdotto.getSelectionModel().select(prodotto);
            }
        }
        Instant instant = movimento.getData().toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();

        datePickerData.setValue(localDate);
    }

    @FXML
    public void initialize() {
        buttonConferma.getStyleClass().add("buttonConfermaVerde");
        buttonConferma.setText("Aggiungi");
        comboBoxProvenienza.setItems(FXCollections.observableArrayList(Database.getAllProvenienze()));
        comboBoxCategoria.setItems(FXCollections.observableArrayList(Database.getAllCategorie()));
        comboBoxTipologia.setItems(FXCollections.observableArrayList(Database.getAllTipologie()));
        comboBoxProdotto.setItems(FXCollections.observableArrayList(Database.getAllProdotti()));
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
            if (newText.matches("\\d*")) { // Solo cifre
                return change; // Permetti il cambiamento
            } else {
                return null; // Scarta il cambiamento
            }
        }));
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        ZoneId defaultZoneId = ZoneId.systemDefault(); // Fuso orario di sistema
        buttonConferma.setOnAction(event -> {
            if (movimento != null) {
                Database.aggiornaRecord("movimento",
                        new String[]{"tipologia_id", "categoria_id", "provenienza_id", "prodotto_id", "data", "valutazione", "valore"},
                        new String[]{comboBoxTipologia.getSelectionModel().getSelectedItem().getId().toString(),
                                comboBoxCategoria.getSelectionModel().getSelectedItem().getId().toString(),
                                comboBoxProvenienza.getSelectionModel().getSelectedItem().getId().toString(),
                                comboBoxProdotto.getSelectionModel().getSelectedItem().getId().toString(),
                                formatter.format(Date.from(datePickerData.getValue().atStartOfDay(defaultZoneId).toInstant())), textFieldValutazione.getText(), textFieldValore.getText()},
                        movimento.getId().toString());
            } else {
                Database.inserisciRecord("movimento",
                        new String[]{"tipologia_id", "categoria_id", "provenienza_id", "prodotto_id", "data", "valutazione", "valore"},
                        new String[]{comboBoxTipologia.getSelectionModel().getSelectedItem().getId().toString(),
                                comboBoxCategoria.getSelectionModel().getSelectedItem().getId().toString(),
                                comboBoxProvenienza.getSelectionModel().getSelectedItem().getId().toString(),
                                comboBoxProdotto.getSelectionModel().getSelectedItem().getId().toString(),
                                formatter.format(Date.from(datePickerData.getValue().atStartOfDay(defaultZoneId).toInstant())), textFieldValutazione.getText(), textFieldValore.getText()});
            }
            ((Stage) buttonConferma.getScene().getWindow()).close();
        });
        buttonAnnulla.setOnAction(event -> {
            ((Stage) buttonConferma.getScene().getWindow()).close();
        });
    }
}