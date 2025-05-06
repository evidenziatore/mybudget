module com.evidenziatore.mybudget {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.evidenziatore.mybudget to javafx.fxml;
    exports com.evidenziatore.mybudget;
    exports com.evidenziatore.mybudget.controller;
    opens com.evidenziatore.mybudget.controller to javafx.fxml;
    exports com.evidenziatore.mybudget.task;
    opens com.evidenziatore.mybudget.task to javafx.fxml;
    exports com.evidenziatore.mybudget.entita;
    opens com.evidenziatore.mybudget.entita to javafx.fxml;
}