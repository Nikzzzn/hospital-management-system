package com.nikzzzn.hospitalclient.controller.appointment;

import com.nikzzzn.hospitalclient.MainApplication;
import com.nikzzzn.hospitalclient.controller.MenuController;
import com.nikzzzn.hospitalclient.helper.Connector;
import com.nikzzzn.hospitalclient.model.Appointment;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AppointmentsController extends MenuController implements Initializable {

    @FXML private TableView<Appointment> appointmentsTable;
    @FXML private TableColumn<Appointment, Integer> columnId;
    @FXML private TableColumn<Appointment, String> columnDoctor;
    @FXML private TableColumn<Appointment, String> columnPatient;
    @FXML private TableColumn<Appointment, LocalDate> columnDate;
    @FXML private TableColumn<Appointment, LocalTime> columnTime;

    public void btnWeekAppointmentsClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Hospital");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnId.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().id));
        columnDoctor.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().doctor.name));
        columnPatient.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().patient.name));
        columnDate.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().date));
        columnTime.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().time));

        columnDate.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                setText(empty ? "" : getItem().toString());
                setGraphic(null);
                TableRow<Appointment> currentRow = getTableRow();
                if (!isEmpty()) {
                    if (item.isBefore(LocalDate.now()))
                        currentRow.setStyle("-fx-background-color:darkgray");
                }
            }
        });

        appointmentsTable.setRowFactory(tv -> {
            TableRow<Appointment> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 1) {
                    Appointment clickedRow = row.getItem();
                    onAppointmentClick(stage, clickedRow);
                }
            });
            return row ;
        });

        try {
            List<Appointment> list = Connector.getAppointments().orElse(new ArrayList<>());
            appointmentsTable.getItems().setAll(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onAppointmentClick(Stage stage, Appointment appointment) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("edit-appointment-view.fxml"));
        EditAppointmentController continueController = new EditAppointmentController();
        continueController.setAppointment(appointment);
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

}
