module com.example.demoapi {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.example.demoapi to javafx.fxml;
    exports com.example.demoapi;

}