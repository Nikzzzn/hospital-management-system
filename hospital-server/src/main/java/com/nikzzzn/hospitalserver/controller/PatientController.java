package com.nikzzzn.hospitalserver.controller;

import com.nikzzzn.hospitalserver.model.Patient;
import com.nikzzzn.hospitalserver.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/new_patient")
    public String showNewPatientPage(Model model){
        Patient patient = new Patient();
        model.addAttribute("patient", patient);

        return "new_patient";
    }

    @PostMapping(value = "/save_patient")
    public void savePatient(@RequestParam Integer id,
                            @RequestParam String name,
                            @RequestParam LocalDate dateOfBirth,
                            @RequestParam String gender,
                            @RequestParam String phone,
                            @RequestParam String address) {
        Patient patient = new Patient(id, name, dateOfBirth, gender, phone, address, new HashSet<>());
        patientService.savePatient(patient);
    }

    @GetMapping("/edit_patient/{id}")
    public ModelAndView showEditPatientPage(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("edit_patient");
        Patient patient = patientService.findById(id);
        mav.addObject("patient", patient);

        return mav;
    }

    @GetMapping("/delete_patient/{id}")
    public void deletePatient(@PathVariable(name = "id") int id) {
        patientService.deletePatient(id);
    }

    @PostMapping("/patient_search")
    public List<Patient> patientSearch(@RequestParam String name) {
        return patientService.findByName(name);
    }

}
