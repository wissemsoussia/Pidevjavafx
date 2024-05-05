module com.example.pidev {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires java.datatransfer;
    requires java.desktop;
    requires org.apache.poi.poi;
    requires org.controlsfx.controls;


    opens com.example.pidev to javafx.fxml;
    opens com.example.pidev.entities to javafx.fxml;
    opens com.example.pidev.controllers to javafx.fxml;
    opens com.example.pidev.controllers.back to javafx.fxml;
    opens com.example.pidev.controllers.front to javafx.fxml;
    opens com.example.pidev.controllers.front.sponsor to javafx.fxml;
    opens com.example.pidev.controllers.front.association to javafx.fxml;
    opens com.example.pidev.controllers.back.sponsor to javafx.fxml;
    opens com.example.pidev.controllers.back.association to javafx.fxml;

    exports com.example.pidev;
    exports com.example.pidev.entities;
    exports com.example.pidev.controllers;
    exports com.example.pidev.controllers.back;
    exports com.example.pidev.controllers.front;
    exports com.example.pidev.controllers.front.sponsor;
    exports com.example.pidev.controllers.front.association;
    exports com.example.pidev.controllers.back.sponsor;
    exports com.example.pidev.controllers.back.association;
}
