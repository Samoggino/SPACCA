module com.spacca {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.spacca to javafx.fxml;
    exports com.spacca;
}
