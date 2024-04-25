module com.melocode.crudapp2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.melocode.crudapp2 to javafx.fxml;
    exports com.melocode.crudapp2;
}