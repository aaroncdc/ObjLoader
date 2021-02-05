module es.enigmatico.objloader {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens es.enigmatico.objloader to javafx.fxml;
    exports es.enigmatico.objloader;
}
