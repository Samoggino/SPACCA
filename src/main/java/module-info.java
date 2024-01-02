module com.spacca {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;   

    opens com.spacca to javafx.fxml;
    exports com.spacca;
}
