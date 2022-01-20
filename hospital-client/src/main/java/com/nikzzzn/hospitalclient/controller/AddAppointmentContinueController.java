package com.nikzzzn.hospitalclient.controller;

import com.nikzzzn.hospitalclient.MainApplication;
import com.nikzzzn.hospitalclient.helper.Connector;
import com.nikzzzn.hospitalclient.helper.FxUtil;
import com.nikzzzn.hospitalclient.model.Doctor;
import com.nikzzzn.hospitalclient.model.Patient;
import com.nikzzzn.hospitalclient.model.Specialty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class AddAppointmentContinueController implements Initializable {

    private Map<Integer, List<LocalTime>> availableDoctors;
    private Patient patient;
    private LocalDate date;

    @FXML
    private ComboBox<Doctor> comboBoxDoctor;

    @FXML
    private ComboBox<LocalTime> comboBoxTime;

    public void btnAddClick(ActionEvent event) throws IOException {
        Doctor doctor = comboBoxDoctor.getValue();
        LocalTime time = comboBoxTime.getValue();
        Connector.saveAppointment(doctor.id, patient.id, date, time);

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Hospital");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void initializeElements(Patient patient, Specialty specialty, LocalDate date) throws IOException {
        availableDoctors = Connector.getAvailableDoctors(date, specialty.id).orElse(new HashMap<>());
        this.patient = patient;
        this.date = date;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FxUtil.autoCompleteComboBoxPlus(comboBoxDoctor, (typedText, itemToCompare) -> itemToCompare.name.toLowerCase().contains(typedText.toLowerCase()));

        comboBoxDoctor.setConverter(new StringConverter<>() {
            @Override
            public String toString(Doctor object) {
                return object != null ? object.name : "";
            }

            @Override
            public Doctor fromString(String string) {
                return comboBoxDoctor.getItems().stream().filter(object ->
                        object.name.equals(string)).findFirst().orElse(null);
            }
        });

        comboBoxDoctor.valueProperty().addListener((ov, t, t1) -> {
            Doctor selected = comboBoxDoctor.getValue();
            comboBoxTime.getItems().clear();
            comboBoxTime.getItems().setAll(availableDoctors.get(selected.id));
        });

        try{
            for(Map.Entry<Integer, List<LocalTime>> doctor: availableDoctors.entrySet()){
                Doctor d = Connector.getDoctorById(doctor.getKey()).orElse(new Doctor());
                comboBoxDoctor.getItems().add(d);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
