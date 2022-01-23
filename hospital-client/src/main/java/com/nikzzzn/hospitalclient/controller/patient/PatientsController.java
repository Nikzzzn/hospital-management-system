package com.nikzzzn.hospitalclient.controller.patient;

import com.nikzzzn.hospitalclient.MainApplication;
import com.nikzzzn.hospitalclient.controller.MenuController;
import com.nikzzzn.hospitalclient.helper.Connector;
import com.nikzzzn.hospitalclient.model.Patient;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PatientsController extends MenuController implements Initializable {

    @FXML private TableView<Patient> patientsTable;
    @FXML private TableColumn<Patient, Integer> columnId;
    @FXML private TableColumn<Patient, String> columnName;
    @FXML private TableColumn<Patient, LocalDate> columnBirthDay;
    @FXML private TableColumn<Patient, String> columnGender;
    @FXML private TableColumn<Patient, String> columnPhone;
    @FXML private TableColumn<Patient, String> columnAddress;
    @FXML private TextField textFieldSearch;
    @FXML private Button btnClear;

    public void btnSearchClick(ActionEvent event) {
        String name = textFieldSearch.getText();
        if(name.length() > 0){
            try {
                List<Patient> list = Connector.getPatientsByName(name).orElse(new ArrayList<>());
                patientsTable.getItems().setAll(list);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        btnClear.setDisable(false);
    }

    public void btnClearClick(ActionEvent event) {
        textFieldSearch.setText("");
        try {
            List<Patient> list = Connector.getPatients().orElse(new ArrayList<>());
            patientsTable.getItems().setAll(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        btnClear.setDisable(true);
    }

    public void onPatientClick(Stage stage, Patient patient){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("patient-info-view.fxml"));
        PatientInfoController continueController = new PatientInfoController();
        continueController.setParameters(stage, patient);
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
        columnId.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().id));
        columnName.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().name));
        columnBirthDay.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().dateOfBirth));
        columnGender.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().gender));
        columnPhone.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().phone));
        columnAddress.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().address));

        patientsTable.setRowFactory(tv -> {
            TableRow<Patient> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 1) {
                    Patient clickedRow = row.getItem();
                    onPatientClick(stage, clickedRow);
                }
            });
            return row ;
        });

        try {
            List<Patient> list = Connector.getPatients().orElse(new ArrayList<>());
            patientsTable.getItems().setAll(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
