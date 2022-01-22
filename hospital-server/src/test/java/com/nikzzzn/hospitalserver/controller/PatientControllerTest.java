package com.nikzzzn.hospitalserver.controller;

import com.nikzzzn.hospitalserver.model.Doctor;
import com.nikzzzn.hospitalserver.model.Patient;
import com.nikzzzn.hospitalserver.model.Specialty;
import com.nikzzzn.hospitalserver.service.AppointmentService;
import com.nikzzzn.hospitalserver.service.DoctorService;
import com.nikzzzn.hospitalserver.service.PatientService;
import com.nikzzzn.hospitalserver.service.SpecialtyService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PatientController.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @MockBean
    private DoctorService doctorService;

    List<Patient> mockPatients = List.of(new Patient(1, "name", LocalDate.of(2000,1,22), "male", "555", "address", new HashSet<>()),
            new Patient(2, "name 1", LocalDate.of(2000,1,22), "male", "555", "address", new HashSet<>()),
            new Patient(3, "something", LocalDate.of(2000,1,22), "male", "555", "address", new HashSet<>()));

    Patient mockPatient = new Patient(0, "new patient", LocalDate.of(2000,1,22), "male", "555", "address", new HashSet<>());

    @Test
    public void patients_url_should_return_json_with_patients() throws Exception {
        Mockito.when(patientService.findAll()).thenReturn(mockPatients);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/patients")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "[{\"id\":1,\"patientName\":\"name\",\"dateOfBirth\":\"2000-01-22\",\"gender\":\"male\",\"phone\":\"555\",\"address\":\"address\"}," +
                "{\"id\":2,\"patientName\":\"name 1\",\"dateOfBirth\":\"2000-01-22\",\"gender\":\"male\",\"phone\":\"555\",\"address\":\"address\"}," +
                "{\"id\":3,\"patientName\":\"something\",\"dateOfBirth\":\"2000-01-22\",\"gender\":\"male\",\"phone\":\"555\",\"address\":\"address\"}]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void save_patient_url_should_return_new_patient() throws Exception {
        Mockito.when(patientService.savePatient(Mockito.any(Patient.class))).thenReturn(mockPatient);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/save_patient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("id", "0")
                .param("name", "new patient")
                .param("dateOfBirth", "2000-01-22")
                .param("gender", "male")
                .param("phone", "555")
                .param("address", "address")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"id\":0,\"patientName\":\"new patient\",\"dateOfBirth\":\"2000-01-22\",\"gender\":\"male\",\"phone\":\"555\",\"address\":\"address\"}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void patient_search_url_should_return_found_patients() throws Exception {
        Mockito.when(patientService.findByName("name")).thenReturn(mockPatients.subList(0,2));
        Mockito.when(patientService.findByName("some")).thenReturn(List.of(mockPatients.get(2)));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/patient_search")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("name", "name")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "[{\"id\":1,\"patientName\":\"name\",\"dateOfBirth\":\"2000-01-22\",\"gender\":\"male\",\"phone\":\"555\",\"address\":\"address\"}," +
                "{\"id\":2,\"patientName\":\"name 1\",\"dateOfBirth\":\"2000-01-22\",\"gender\":\"male\",\"phone\":\"555\",\"address\":\"address\"}]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

        requestBuilder = MockMvcRequestBuilders.post("/patient_search")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("name", "some")
                .accept(MediaType.APPLICATION_JSON);
        result = mockMvc.perform(requestBuilder).andReturn();

        expected = "[{\"id\":3,\"patientName\":\"something\",\"dateOfBirth\":\"2000-01-22\",\"gender\":\"male\",\"phone\":\"555\",\"address\":\"address\"}]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

}
