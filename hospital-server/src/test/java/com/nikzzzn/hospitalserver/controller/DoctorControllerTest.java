package com.nikzzzn.hospitalserver.controller;

import com.nikzzzn.hospitalserver.model.Doctor;
import com.nikzzzn.hospitalserver.model.Patient;
import com.nikzzzn.hospitalserver.model.Specialty;
import com.nikzzzn.hospitalserver.service.AppointmentService;
import com.nikzzzn.hospitalserver.service.DoctorService;
import com.nikzzzn.hospitalserver.service.PatientService;
import com.nikzzzn.hospitalserver.service.SpecialtyService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
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

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = DoctorController.class)
class DoctorControllerTest {

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

    Doctor mockDoctor = new Doctor(4, "new doctor", new Specialty(3,"specialty 3", new HashSet<>()), new HashSet<>());

    @Test
    public void doctors_url_should_return_json_with_doctors() throws Exception {
        Mockito.when(doctorService.findAll()).thenReturn(mockDoctors);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/doctors")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "[{\"id\":1,\"doctorName\":\"doctor 1\",\"specialty\":{\"id\":1,\"specialty\":\"specialty 1\"}}," +
                "{\"id\":2,\"doctorName\":\"doctor 2\",\"specialty\":{\"id\":2,\"specialty\":\"specialty 2\"}}," +
                "{\"id\":3,\"doctorName\":\"doctor 3\",\"specialty\":{\"id\":1,\"specialty\":\"specialty 1\"}}]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void doctors_by_specialty_url_should_return_json_with_doctors() throws Exception {
        Mockito.when(doctorService.findBySpecialtyId(Mockito.any())).thenReturn(List.of(mockDoctors.get(0), mockDoctors.get(2)));
        Mockito.when(doctorService.findBySpecialtyId(2)).thenReturn(List.of(mockDoctors.get(1)));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/doctors_by_specialty")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("id", "1")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "[{\"id\":1,\"doctorName\":\"doctor 1\",\"specialty\":{\"id\":1,\"specialty\":\"specialty 1\"}}," +
                "{\"id\":3,\"doctorName\":\"doctor 3\",\"specialty\":{\"id\":1,\"specialty\":\"specialty 1\"}}]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

        requestBuilder = MockMvcRequestBuilders.get("/doctors_by_specialty")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("id", "2")
                .accept(MediaType.APPLICATION_JSON);
        result = mockMvc.perform(requestBuilder).andReturn();

        expected = "[{\"id\":2,\"doctorName\":\"doctor 2\",\"specialty\":{\"id\":2,\"specialty\":\"specialty 2\"}}]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void doctors_by_id_url_should_return_json_doctor() throws Exception {
        Mockito.when(doctorService.findById(1)).thenReturn(mockDoctors.get(0));
        Mockito.when(doctorService.findById(2)).thenReturn(mockDoctors.get(1));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/doctors_by_id")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("id", "1")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"id\":1,\"doctorName\":\"doctor 1\",\"specialty\":{\"id\":1,\"specialty\":\"specialty 1\"}}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

        requestBuilder = MockMvcRequestBuilders.get("/doctors_by_id")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("id", "2")
                .accept(MediaType.APPLICATION_JSON);
        result = mockMvc.perform(requestBuilder).andReturn();

        expected = "{\"id\":2,\"doctorName\":\"doctor 2\",\"specialty\":{\"id\":2,\"specialty\":\"specialty 2\"}}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void available_doctors_url_should_return_json_with_doctor_id_and_hours() throws Exception {
        Mockito.when(doctorService.findBySpecialtyId(1)).thenReturn(List.of(mockDoctors.get(0), mockDoctors.get(2)));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/available_doctors")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("date", "2022-01-22")
                .param("specialty", "1")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"3\":[\"09:00:00\",\"09:30:00\",\"10:00:00\",\"10:30:00\",\"11:00:00\",\"11:30:00\",\"12:00:00\",\"12:30:00\"," +
                "\"14:00:00\",\"14:30:00\",\"15:00:00\",\"15:30:00\",\"16:00:00\",\"16:30:00\",\"17:00:00\",\"17:30:00\"]," +
                "\"1\":[\"09:00:00\",\"09:30:00\",\"10:00:00\",\"10:30:00\",\"11:00:00\",\"11:30:00\",\"12:00:00\",\"12:30:00\"," +
                "\"14:00:00\",\"14:30:00\",\"15:00:00\",\"15:30:00\",\"16:00:00\",\"16:30:00\",\"17:00:00\",\"17:30:00\"]}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void save_doctor_url_should_return_json_with_new_doctor() throws Exception {
        Mockito.when(doctorService.saveDoctor(Mockito.any(Doctor.class))).thenReturn(mockDoctor);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/save_doctor")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("id", "4")
                .param("name", "new doctor")
                .param("specialtyId", "3")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"id\":4,\"doctorName\":\"new doctor\",\"specialty\":{\"id\":3,\"specialty\":\"specialty 3\"}}";
        System.out.println(result.getResponse().getContentAsString());
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void patient_search_url_should_return_found_patients() throws Exception {
        Mockito.when(doctorService.findByName("doctor")).thenReturn(mockDoctors);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/doctor_search")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("name", "doctor")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "[{\"id\":1,\"doctorName\":\"doctor 1\",\"specialty\":{\"id\":1,\"specialty\":\"specialty 1\"}}," +
                "{\"id\":2,\"doctorName\":\"doctor 2\",\"specialty\":{\"id\":2,\"specialty\":\"specialty 2\"}}," +
                "{\"id\":3,\"doctorName\":\"doctor 3\",\"specialty\":{\"id\":1,\"specialty\":\"specialty 1\"}}]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

        requestBuilder = MockMvcRequestBuilders.post("/doctor_search")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("name", "patient")
                .accept(MediaType.APPLICATION_JSON);
        result = mockMvc.perform(requestBuilder).andReturn();

        expected = "[]";
        Assertions.assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

}