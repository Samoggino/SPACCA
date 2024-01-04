module com.spacca {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;  
    requires com.google.gson;

    opens com.spacca to javafx.fxml;
    exports com.spacca;
}
