package com.nikzzzn.hospitalclient.controller;

import com.nikzzzn.hospitalclient.MainApplication;
import com.nikzzzn.hospitalclient.helper.Connector;
import com.nikzzzn.hospitalclient.model.Doctor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DoctorInfoController extends MenuController implements Initializable {

    private Stage stage;
    private Doctor doctor;

    @FXML private Label lblName;
    @FXML private Label lblSpecialty;

    public void setParameters(Stage stage, Doctor doctor) {
        this.stage = stage;
        this.doctor = doctor;
    }

    public void menuEditClick(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("edit-doctor-view.fxml"));
        EditDoctorController continueController = new EditDoctorController();
        continueController.setDoctor(doctor);
        fxmlLoader.setController(continueController);
        try {
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hospital");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void menuDeleteClick(ActionEvent event) throws IOException {
        Connector.deleteDoctor(doctor);
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("doctors-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Hospital");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void menuPatientsInfoClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("patients-doctor-view.fxml"));
        PatientsDoctorController continueController = new PatientsDoctorController();
        continueController.setDoctor(doctor);
        fxmlLoader.setController(continueController);
        try {
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hospital");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void menuAppointmentsWeekClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("appointments-doctor-week-view.fxml"));
        AppointmentsDoctorWeekController continueController = new AppointmentsDoctorWeekController();
        continueController.setDoctor(doctor);
        fxmlLoader.setController(continueController);
        try {
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hospital");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void menuAppointmentsAllClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("appointments-doctor-view.fxml"));
        AppointmentsDoctorController continueController = new AppointmentsDoctorController();
        continueController.setDoctor(doctor);
        fxmlLoader.setController(continueController);
        try {
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hospital");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblName.setText(doctor.name);
        lblSpecialty.setText(doctor.specialty.name);
    }

}
