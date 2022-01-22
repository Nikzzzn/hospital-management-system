package com.nikzzzn.hospitalclient.controller;

import com.nikzzzn.hospitalclient.MainApplication;
import com.nikzzzn.hospitalclient.helper.Connector;
import com.nikzzzn.hospitalclient.helper.FxUtil;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditDoctorController extends MenuController implements Initializable {

    private Doctor doctor;

    @FXML
    private ComboBox<Specialty> comboBoxSpecialty;

    @FXML
    private TextField textFieldName;

    public void setDoctor(Doctor doctor){
        this.doctor = doctor;
    }

    public void btnSaveClick(ActionEvent event) throws IOException {
        doctor.name = textFieldName.getText();
        doctor.specialty = comboBoxSpecialty.getValue();
        Connector.saveDoctor(doctor);

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("doctors-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Hospital");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FxUtil.autoCompleteComboBoxPlus(comboBoxSpecialty, (typedText, itemToCompare) -> itemToCompare.name.toLowerCase().contains(typedText.toLowerCase()));

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

        try {
            List<Specialty> listOfSpecialties = Connector.getSpecialties().orElse(new ArrayList<>());
            comboBoxSpecialty.getItems().addAll(listOfSpecialties);
        } catch (IOException e) {
            e.printStackTrace();
        }

        comboBoxSpecialty.setValue(doctor.specialty);
        textFieldName.setText(doctor.name);
    }
}
