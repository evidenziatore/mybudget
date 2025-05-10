package com.evidenziatore.mybudget;

import com.evidenziatore.mybudget.database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ApplicationMyBudget extends Application {

    @Override
    public void start(Stage maschera) throws IOException {
        Database.initialize();
        FXMLLoader caricatoreFXML = new FXMLLoader(ApplicationMyBudget.class.getResource("mybudget.fxml"));
        maschera.setScene(generaScena(caricatoreFXML));
        maschera.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img.png"))));
        maschera.setTitle("Welcome to MyBudget! - Gestisci le tue Finanze");
        maschera.show();
    }

    private Scene generaScena(FXMLLoader caricatoreFXML) throws IOException {
        double lunghezzaSchermo = Screen.getPrimary().getVisualBounds().getWidth();
        double altezzaSchermo = Screen.getPrimary().getVisualBounds().getHeight();
        Scene scena = new Scene(caricatoreFXML.load(), lunghezzaSchermo/1.5, altezzaSchermo/1.5);
        scena.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
        return scena;
    }

    public static void main(String[] args) {
        launch();
    }
}