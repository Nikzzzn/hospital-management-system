package com.nikzzzn.hospitalserver.controller;

import com.nikzzzn.hospitalserver.model.Appointment;
import com.nikzzzn.hospitalserver.model.Doctor;
import com.nikzzzn.hospitalserver.model.Patient;
import com.nikzzzn.hospitalserver.model.Specialty;
import com.nikzzzn.hospitalserver.service.AppointmentService;
import com.nikzzzn.hospitalserver.service.DoctorService;
import com.nikzzzn.hospitalserver.service.PatientService;
import com.nikzzzn.hospitalserver.service.SpecialtyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;



@RunWith(SpringRunner.class)
@WebMvcTest(value = AppointmentController.class)
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorService doctorService;

    @MockBean
    private AppointmentService appointmentService;

    @MockBean
    private SpecialtyService specialtyService;

    @MockBean
    private PatientService patientService;

    List<Doctor> mockDoctors = List.of(new Doctor(1, "doctor 1", new Specialty(1,"specialty 1", new HashSet<>()), new HashSet<>()),
            new Doctor(2, "doctor 2", new Specialty(2,"specialty 2", new HashSet<>()), new HashSet<>()),
            new Doctor(3, "doctor 3", new Specialty(1,"specialty 1", new HashSet<>()), new HashSet<>()));

    List<Patient> mockPatients = List.of(new Patient(1, "name", LocalDate.of(2000,1,22), "male", "555", "address", new HashSet<>()),
            new Patient(2, "name 1", LocalDate.of(2000,1,22), "male", "555", "address", new HashSet<>()),
            new Patient(3, "something", LocalDate.of(2000,1,22), "male", "555", "address", new HashSet<>()));

    List<Appointment> mockAppointments = List.of(new Appointment(1, mockDoctors.get(0), mockPatients.get(1), LocalDate.of(2022,1,22), LocalTime.of(10,0)),
            new Appointment(2, mockDoctors.get(2), mockPatients.get(0), LocalDate.of(2022,1,22), LocalTime.of(10,0)),
            new Appointment(3, mockDoctors.get(1), mockPatients.get(2), LocalDate.of(2021,1,22), LocalTime.of(10,0)));

    Appointment mockAppointment = new Appointment(4, mockDoctors.get(2), mockPatients.get(2), LocalDate.of(2022,1,28), LocalTime.of(10,0));

    @Test
    public void appointments_url_should_return_json_with_appointments() throws Exception {
        Mockito.when(appointmentService.findAll()).thenReturn(mockAppointments);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/appointments")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "[{\"id\":1," +
                "\"doctor\":{\"id\":1,\"doctorName\":\"doctor 1\",\"specialty\":" +
                    "{\"id\":1,\"specialty\":\"specialty 1\"}}," +
                "\"patient\":{\"id\":2,\"patientName\":\"name 1\",\"dateOfBirth\":\"2000-01-22\",\"gender\":\"male\",\"phone\":\"555\",\"address\":\"address\"}," +
                "\"appointmentDate\":\"2022-01-22\",\"appointmentTime\":\"10:00:00\"}," +
                "{\"id\":2," +
                "\"doctor\":{\"id\":3,\"doctorName\":\"doctor 3\"," +
                    "\"specialty\":{\"id\":1,\"specialty\":\"specialty 1\"}}," +
                "\"patient\":{\"id\":1,\"patientName\":\"name\",\"dateOfBirth\":\"2000-01-22\",\"gender\":\"male\",\"phone\":\"555\",\"address\":\"address\"}," +
                "\"appointmentDate\":\"2022-01-22\",\"appointmentTime\":\"10:00:00\"}," +
                "{\"id\":3," +
                "\"doctor\":{\"id\":2,\"doctorName\":\"doctor 2\"," +
                    "\"specialty\":{\"id\":2,\"specialty\":\"specialty 2\"}}," +
                "\"patient\":{\"id\":3,\"patientName\":\"something\",\"dateOfBirth\":\"2000-01-22\",\"gender\":\"male\",\"phone\":\"555\",\"address\":\"address\"}," +
                "\"appointmentDate\":\"2021-01-22\",\"appointmentTime\":\"10:00:00\"}]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void weeks_schedule_url_should_return_json_with_weeks_schedule() throws Exception {
        Mockito.when(appointmentService.findForCurrentWeek()).thenReturn(mockAppointments.subList(0, 2));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/weeks_schedule")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "[{\"id\":1," +
                "\"doctor\":{\"id\":1,\"doctorName\":\"doctor 1\",\"specialty\":" +
                "{\"id\":1,\"specialty\":\"specialty 1\"}}," +
                "\"patient\":{\"id\":2,\"patientName\":\"name 1\",\"dateOfBirth\":\"2000-01-22\",\"gender\":\"male\",\"phone\":\"555\",\"address\":\"address\"}," +
                "\"appointmentDate\":\"2022-01-22\",\"appointmentTime\":\"10:00:00\"}," +
                "{\"id\":2," +
                "\"doctor\":{\"id\":3,\"doctorName\":\"doctor 3\"," +
                "\"specialty\":{\"id\":1,\"specialty\":\"specialty 1\"}}," +
                "\"patient\":{\"id\":1,\"patientName\":\"name\",\"dateOfBirth\":\"2000-01-22\",\"gender\":\"male\",\"phone\":\"555\",\"address\":\"address\"}," +
                "\"appointmentDate\":\"2022-01-22\",\"appointmentTime\":\"10:00:00\"}]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void save_appointment_url_should_return_json_with_new_appointment() throws Exception {
        Mockito.when(appointmentService.saveAppointment(Mockito.any(Appointment.class))).thenReturn(mockAppointment);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/save_appointment")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("id", "4")
                .param("doctorId", "3")
                .param("patientId", "3")
                .param("date", "2022-01-28")
                .param("time", "10:00")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"id\":4,\"doctor\":{\"id\":3,\"doctorName\":\"doctor 3\",\"specialty\":{\"id\":1,\"specialty\":\"specialty 1\"}},\"patient\":{\"id\":3,\"patientName\":\"something\",\"dateOfBirth\":\"2000-01-22\",\"gender\":\"male\",\"phone\":\"555\",\"address\":\"address\"},\"appointmentDate\":\"2022-01-28\",\"appointmentTime\":\"10:00:00\"}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

}
