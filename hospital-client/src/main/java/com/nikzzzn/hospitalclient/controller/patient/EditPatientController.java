package com.nikzzzn.hospitalclient.controller.patient;

import com.nikzzzn.hospitalclient.MainApplication;
import com.nikzzzn.hospitalclient.controller.MenuController;
import com.nikzzzn.hospitalclient.helper.Connector;
import com.nikzzzn.hospitalclient.model.Doctor;
import com.nikzzzn.hospitalclient.model.Patient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditPatientController extends MenuController implements Initializable {

    private Patient patient;

    @FXML private TextField textFieldName;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> comboBoxGender;
    @FXML private TextField textFieldPhone;
    @FXML private TextField textFieldAddress;

    public void setPatient(Patient patient){
        this.patient = patient;
    }

    public void btnSaveClick(ActionEvent event) throws IOException {
        patient.name = textFieldName.getText();
        patient.dateOfBirth = datePicker.getValue();
        patient.gender = comboBoxGender.getValue();
        patient.phone = textFieldPhone.getText();
        patient.address = textFieldAddress.getText();
        Connector.savePatient(patient);

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("patients-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Hospital");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void btnDeleteClick(ActionEvent event) throws IOException {
        Connector.deletePatient(patient);
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("patients-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Hospital");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> genders = List.of("Male", "Female");
        comboBoxGender.getItems().addAll(genders);

        textFieldName.setText(patient.name);
        datePicker.setValue(patient.dateOfBirth);
        comboBoxGender.setValue(patient.gender);
        textFieldPhone.setText(patient.phone);
        textFieldAddress.setText(patient.address);
    }

}
