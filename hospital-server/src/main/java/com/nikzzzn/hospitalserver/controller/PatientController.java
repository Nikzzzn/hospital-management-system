package com.nikzzzn.hospitalserver.controller;

import com.nikzzzn.hospitalserver.model.Patient;
import com.nikzzzn.hospitalserver.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public String savePatient(@ModelAttribute("patient") Patient patient) {
        patientService.savePatient(patient);

        return "redirect:/patients";
    }

    @GetMapping("/edit_patient/{id}")
    public ModelAndView showEditPatientPage(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("edit_patient");
        Patient patient = patientService.findById(id);
        mav.addObject("patient", patient);

        return mav;
    }

    @GetMapping("/delete_patient/{id}")
    public String deletePatient(@PathVariable(name = "id") int id) {
        patientService.deletePatient(id);
        return "redirect:/patients";
    }

    @PostMapping("/patient_search")
    public String patientSearch(Model model, @RequestParam String patientName) {
        List<Patient> foundPatients = patientService.findByName(patientName);
        model.addAttribute("foundPatients", foundPatients);
        StringBuilder sb = new StringBuilder("Found results for query \"");
        sb.append(patientName)
          .append("\"");
        model.addAttribute("query", sb.toString());

        return "patient_search";
    }

}
