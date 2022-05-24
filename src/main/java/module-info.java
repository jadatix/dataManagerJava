module com.manager.datamanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.manager.datamanager to javafx.fxml;
    exports com.manager.datamanager;
    exports com.manager.datamanager.controllers;
    opens com.manager.datamanager.controllers to javafx.fxml;
}