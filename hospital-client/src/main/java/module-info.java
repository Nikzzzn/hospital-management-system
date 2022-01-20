module com.nikzzzn.hospitalclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires org.apache.httpcomponents.httpclient;

    opens com.nikzzzn.hospitalclient to javafx.fxml;
    opens com.nikzzzn.hospitalclient.holder to javafx.base;
    exports com.nikzzzn.hospitalclient;
    exports com.nikzzzn.hospitalclient.model to com.fasterxml.jackson.databind;
    exports com.nikzzzn.hospitalclient.controller;
    opens com.nikzzzn.hospitalclient.controller to javafx.fxml;
    exports com.nikzzzn.hospitalclient.helper;
    opens com.nikzzzn.hospitalclient.helper to javafx.fxml;
}