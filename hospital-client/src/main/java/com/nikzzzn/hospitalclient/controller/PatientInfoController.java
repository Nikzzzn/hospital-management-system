package com.nikzzzn.hospitalclient.controller;

import com.nikzzzn.hospitalclient.MainApplication;
import com.nikzzzn.hospitalclient.helper.Connector;
import com.nikzzzn.hospitalclient.model.Patient;
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

public class PatientInfoController extends MenuController implements Initializable {

    private Patient patient;
    private Stage stage;

    @FXML private Label lblName;
    @FXML private Label lblBirthDay;
    @FXML private Label lblGender;
    @FXML private Label lblPhone;
    @FXML private Label lblAddress;

    public void setParameters(Stage stage, Patient patient){
        this.stage = stage;
        this.patient = patient;
    }

    public void menuEditClick(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("edit-patient-view.fxml"));
        EditPatientController continueController = new EditPatientController();
        continueController.setPatient(patient);
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
        Connector.deletePatient(patient);
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("patients-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Hospital");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void menuDoctorsInfoClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("doctors-patient-view.fxml"));
        DoctorsPatientController continueController = new DoctorsPatientController();
        continueController.setPatient(patient);
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
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("appointments-patient-week-view.fxml"));
        AppointmentsPatientWeekController continueController = new AppointmentsPatientWeekController();
        continueController.setPatient(patient);
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
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("appointments-patient-view.fxml"));
        AppointmentsPatientController continueController = new AppointmentsPatientController();
        continueController.setPatient(patient);
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
        lblName.setText(patient.name);
        lblBirthDay.setText(patient.dateOfBirth.toString());
        lblGender.setText(patient.gender);
        lblPhone.setText(patient.phone);
        lblAddress.setText(patient.address);
    }

}
