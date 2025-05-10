module com.evidenziatore.mybudget {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.evidenziatore.mybudget to javafx.fxml;
    exports com.evidenziatore.mybudget;
    exports com.evidenziatore.mybudget.controller;
    opens com.evidenziatore.mybudget.controller to javafx.fxml;
}