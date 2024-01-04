module com.spacca {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.graphics;
    requires com.google.gson;

    opens com.spacca.controller to javafx.fxml;

    exports com.spacca;

    exports com.spacca.controller;
    exports com.spacca.database;

    exports com.spacca.asset;
    exports com.spacca.asset.carte;
    exports com.spacca.asset.giocatore;
}
