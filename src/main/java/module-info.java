module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens com.example.demo to javafx.fxml, java.sql;
    exports com.example.demo;
    opens com.example.demo.controller to java.sql, javafx.fxml;
    exports com.example.demo.entity;
    opens com.example.demo.entity to java.sql, javafx.fxml;
    exports com.example.demo.controller;
}