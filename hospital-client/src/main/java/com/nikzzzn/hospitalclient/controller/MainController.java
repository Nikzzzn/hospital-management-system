package com.nikzzzn.hospitalclient.controller;

import com.nikzzzn.hospitalclient.MainApplication;
import com.nikzzzn.hospitalclient.helper.Connector;
import com.nikzzzn.hospitalclient.holder.AppointmentHolder;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML private TableView<AppointmentHolder> appointmentsTable;

    @FXML private TableColumn<AppointmentHolder, String> columnId;

    @FXML private TableColumn<AppointmentHolder, String> columnDoctor;

    @FXML private TableColumn<AppointmentHolder, String> columnPatient;

    @FXML private TableColumn<AppointmentHolder, String> columnDate;

    @FXML private TableColumn<AppointmentHolder, String> columnTime;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnId.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().id()));
        columnDoctor.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().doctor()));
        columnPatient.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().patient()));
        columnDate.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().date()));
        columnTime.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().time()));

        try {
            List<AppointmentHolder> list = Connector.getAppointments().orElse(new ArrayList<>());
            appointmentsTable.getItems().setAll(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnNewAppointmentClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("add-appointment-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Hospital");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}