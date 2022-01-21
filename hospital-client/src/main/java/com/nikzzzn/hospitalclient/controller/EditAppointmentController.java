package com.nikzzzn.hospitalclient.controller;

import com.nikzzzn.hospitalclient.MainApplication;
import com.nikzzzn.hospitalclient.helper.Connector;
import com.nikzzzn.hospitalclient.helper.FxUtil;
import com.nikzzzn.hospitalclient.model.Appointment;
import com.nikzzzn.hospitalclient.model.Patient;
import com.nikzzzn.hospitalclient.model.Specialty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditAppointmentController extends MenuController implements Initializable {

    private Appointment appointment;

    @FXML
    private ComboBox<Patient> comboBoxPatient;

    @FXML
    private ComboBox<Specialty> comboBoxSpecialty;

    @FXML
    private DatePicker datePicker;

    public void btnContinueClick(ActionEvent event) throws IOException {
        appointment.patient = comboBoxPatient.getValue();
        appointment.date = datePicker.getValue();
        Specialty specialty = comboBoxSpecialty.getValue();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("edit-appointment-continue-view.fxml"));
        EditAppointmentContinueController continueController = new EditAppointmentContinueController();
        continueController.initializeElements(appointment, specialty);
        fxmlLoader.setController(continueController);

        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Hospital");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void btnDeleteClick(ActionEvent event) throws IOException {
        Connector.deleteAppointment(appointment);
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Hospital");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void setAppointment(Appointment appointment){
        this.appointment = appointment;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FxUtil.autoCompleteComboBoxPlus(comboBoxPatient, (typedText, itemToCompare) -> itemToCompare.name.toLowerCase().contains(typedText.toLowerCase()));
        FxUtil.autoCompleteComboBoxPlus(comboBoxSpecialty, (typedText, itemToCompare) -> itemToCompare.name.toLowerCase().contains(typedText.toLowerCase()));

        comboBoxPatient.setConverter(new StringConverter<>() {
            @Override
            public String toString(Patient object) {
                return object != null ? object.name : "";
            }

            @Override
            public Patient fromString(String string) {
                return comboBoxPatient.getItems().stream().filter(object ->
                        object.name.equals(string)).findFirst().orElse(null);
            }
        });

        comboBoxSpecialty.setConverter(new StringConverter<>() {
            @Override
            public String toString(Specialty object) {
                return object != null ? object.name : "";
            }

            @Override
            public Specialty fromString(String string) {
                return comboBoxSpecialty.getItems().stream().filter(object ->
                        object.name.equals(string)).findFirst().orElse(null);
            }
        });

        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });

        try {
            List<Patient> listOfPatients = Connector.getPatients().orElse(new ArrayList<>());
            List<Specialty> listOfSpecialties = Connector.getSpecialties().orElse(new ArrayList<>());
            comboBoxPatient.getItems().addAll(listOfPatients);
            comboBoxSpecialty.getItems().addAll(listOfSpecialties);
        } catch (IOException e) {
            e.printStackTrace();
        }

        comboBoxPatient.setValue(appointment.patient);
        comboBoxSpecialty.setValue(appointment.doctor.specialty);
        datePicker.setValue(appointment.date);
    }

}
