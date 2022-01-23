package com.nikzzzn.hospitalclient.controller.patient;

import com.nikzzzn.hospitalclient.MainApplication;
import com.nikzzzn.hospitalclient.controller.MenuController;
import com.nikzzzn.hospitalclient.helper.Connector;
import com.nikzzzn.hospitalclient.model.Doctor;
import com.nikzzzn.hospitalclient.model.Patient;
import com.nikzzzn.hospitalclient.model.Specialty;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddPatientController extends MenuController implements Initializable {

    @FXML private TextField textFieldName;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> comboBoxGender;
    @FXML private TextField textFieldPhone;
    @FXML private TextField textFieldAddress;

    public void btnAddClick(ActionEvent event) throws IOException {
        String name = textFieldName.getText();
        LocalDate dateOfBirth = datePicker.getValue();
        String gender = comboBoxGender.getValue();
        String phone = textFieldPhone.getText();
        String address = textFieldAddress.getText();
        Patient patient = new Patient(0, name, dateOfBirth, gender, phone, address);
        Connector.savePatient(patient);

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
    }

}
