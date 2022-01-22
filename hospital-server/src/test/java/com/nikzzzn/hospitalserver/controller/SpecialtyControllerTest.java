package com.nikzzzn.hospitalserver.controller;

import com.nikzzzn.hospitalserver.model.Specialty;
import com.nikzzzn.hospitalserver.service.AppointmentService;
import com.nikzzzn.hospitalserver.service.DoctorService;
import com.nikzzzn.hospitalserver.service.PatientService;
import com.nikzzzn.hospitalserver.service.SpecialtyService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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
@WebMvcTest(value = SpecialtyController.class)
class SpecialtyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpecialtyService specialtyService;

    @MockBean
    private AppointmentService appointmentService;

    @MockBean
    private DoctorService doctorService;

    @MockBean
    private PatientService patientService;

    List<Specialty> mockSpecialties = List.of(new Specialty(1, "specialty 1", new HashSet<>()),
            new Specialty(2, "specialty 2", new HashSet<>()),
            new Specialty(3, "specialty 3", new HashSet<>()));

    @Test
    public void specialties_url_should_return_json_with_specialties() throws Exception {
        Mockito.when(specialtyService.findAll()).thenReturn(mockSpecialties);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/specialties")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "[{\"id\":1,\"specialty\":\"specialty 1\"}," +
                "{\"id\":2,\"specialty\":\"specialty 2\"}," +
                "{\"id\":3,\"specialty\":\"specialty 3\"}]";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

}