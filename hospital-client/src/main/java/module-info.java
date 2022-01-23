module com.nikzzzn.hospitalclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires org.apache.httpcomponents.httpclient;

    opens com.nikzzzn.hospitalclient to javafx.fxml;
    exports com.nikzzzn.hospitalclient;
    exports com.nikzzzn.hospitalclient.model to com.fasterxml.jackson.databind;
    exports com.nikzzzn.hospitalclient.controller;
    opens com.nikzzzn.hospitalclient.controller to javafx.fxml;
    exports com.nikzzzn.hospitalclient.helper;
    opens com.nikzzzn.hospitalclient.helper to javafx.fxml;
    exports com.nikzzzn.hospitalclient.controller.appointment;
    opens com.nikzzzn.hospitalclient.controller.appointment to javafx.fxml;
    exports com.nikzzzn.hospitalclient.controller.doctor;
    opens com.nikzzzn.hospitalclient.controller.doctor to javafx.fxml;
    exports com.nikzzzn.hospitalclient.controller.patient;
    opens com.nikzzzn.hospitalclient.controller.patient to javafx.fxml;
}