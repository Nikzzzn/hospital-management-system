module com.nikzzzn.hospitalclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.nikzzzn.hospitalclient to javafx.fxml;
    exports com.nikzzzn.hospitalclient;
}