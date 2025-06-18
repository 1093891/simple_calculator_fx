module org.example.calculator_simple {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.calculator_simple to javafx.fxml;
    exports org.example.calculator_simple;
}