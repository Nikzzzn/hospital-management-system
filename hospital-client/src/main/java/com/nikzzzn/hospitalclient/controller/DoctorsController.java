package com.nikzzzn.hospitalclient.controller;

import com.nikzzzn.hospitalclient.MainApplication;
import com.nikzzzn.hospitalclient.helper.Connector;
import com.nikzzzn.hospitalclient.model.Appointment;
import com.nikzzzn.hospitalclient.model.Doctor;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DoctorsController extends MenuController implements Initializable {

    @FXML private TableView<Doctor> doctorsTable;
    @FXML private TableColumn<Doctor, Integer> columnId;
    @FXML private TableColumn<Doctor, String> columnName;
    @FXML private TableColumn<Doctor, String> columnSpecialty;

    public void btnNewDoctorClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("add-doctor-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Hospital");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void onDoctorClick(Stage stage, Doctor doctor){
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnId.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().id));
        columnName.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().name));
        columnSpecialty.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().specialty.name));

        doctorsTable.setRowFactory(tv -> {
            TableRow<Doctor> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 1) {
                    Doctor clickedRow = row.getItem();
                    onDoctorClick(stage, clickedRow);
                }
            });
            return row ;
        });

        try {
            List<Doctor> list = Connector.getDoctors().orElse(new ArrayList<>());
            doctorsTable.getItems().setAll(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
