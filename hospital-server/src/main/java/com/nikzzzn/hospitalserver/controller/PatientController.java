package com.nikzzzn.hospitalserver.controller;

import com.nikzzzn.hospitalserver.model.Doctor;
import com.nikzzzn.hospitalserver.model.Patient;
import com.nikzzzn.hospitalserver.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/patients")
    public List<Patient> getPatients(){
        return patientService.findAll();
    }

    @PostMapping(value = "/save_patient")
    public Patient savePatient(@RequestParam Integer id,
                               @RequestParam String name,
                               @RequestParam LocalDate dateOfBirth,
                               @RequestParam String gender,
                               @RequestParam String phone,
                               @RequestParam String address) {
        Patient patient = patientService.findById(id);
        patient = (patient != null) ? patient : new Patient();
        patient.setPatientName(name);
        patient.setDateOfBirth(dateOfBirth);
        patient.setGender(gender);
        patient.setPhone(phone);
        patient.setAddress(address);
        return patientService.savePatient(patient);
    }

    @GetMapping("/delete_patient/{id}")
    public void deletePatient(@PathVariable(name = "id") int id) {
        patientService.deletePatient(id);
    }

    @PostMapping("/patient_search")
    public List<Patient> patientSearch(@RequestParam String name) {
        return patientService.findByName(name);
    }

    @GetMapping("/doctor_patients/{id}")
    public List<Patient> getPatientsByDoctorId(@PathVariable(name = "id") int id){
        return patientService.findByDoctorId(id);
    }

}
