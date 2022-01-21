package com.nikzzzn.hospitalclient.controller;

import com.nikzzzn.hospitalclient.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    private MenuBar menuBar;

    public void menuAddAppointmentClicked(ActionEvent event) throws IOException {
        loadScene("add-appointment-view.fxml");
    }

    public void menuAddDoctorClicked(ActionEvent event) throws IOException {
        loadScene("add-doctor-view.fxml");
    }

    public void menuAddPatientClicked(ActionEvent event) throws IOException {
        loadScene("add-patient-view.fxml");
    }

    public void menuAppointmentsClicked(ActionEvent event) throws IOException {
        loadScene("appointments-view.fxml");
    }

    public void menuWeekClicked(ActionEvent event) throws IOException {
        loadScene("main-view.fxml");
    }

    public void menuDoctorsClicked(ActionEvent event) throws IOException {
        loadScene("doctors-view.fxml");
    }

    public void menuPatientsClicked(ActionEvent event) throws IOException {
        loadScene("patients-view.fxml");
    }

    private void loadScene(String name) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(name));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.setTitle("Hospital");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
