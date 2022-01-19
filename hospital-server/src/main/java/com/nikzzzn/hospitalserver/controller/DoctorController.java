package com.nikzzzn.hospitalserver.controller;

import com.nikzzzn.hospitalserver.model.Doctor;
import com.nikzzzn.hospitalserver.service.DoctorService;
import com.nikzzzn.hospitalserver.service.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private SpecialtyService specialtyService;

    @GetMapping("/doctors")
    public String showDoctors(Model model){
        List<Doctor> listOfDoctors = doctorService.findAll();
        model.addAttribute("listOfDoctors", listOfDoctors);

        return "doctors";
    }

    @GetMapping("/new_doctor")
    public String showNewDoctorPage(Model model){
        Doctor doctor = new Doctor();
        model.addAttribute("doctor", doctor);
        model.addAttribute("listOfSpecialties", specialtyService.findAll());

        return "new_doctor";
    }

    @PostMapping(value = "/save_doctor")
    public String saveDoctor(@ModelAttribute("doctor") Doctor doctor) {
        doctorService.saveDoctor(doctor);

        return "redirect:/doctors";
    }

    @GetMapping("/edit_doctor/{id}")
    public ModelAndView showEditDoctorPage(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("edit_doctor");
        Doctor doctor = doctorService.findById(id);
        mav.addObject("doctor", doctor);
        mav.addObject("listOfSpecialties", specialtyService.findAll());

        return mav;
    }

    @GetMapping("/delete_doctor/{id}")
    public String deleteDoctor(@PathVariable(name = "id") int id) {
        doctorService.deleteDoctor(id);
        return "redirect:/doctors";
    }

    @PostMapping("/doctor_search")
    public String doctorSearch(Model model, @RequestParam String doctorName) {
        List<Doctor> foundDoctors = doctorService.findByName(doctorName);
        model.addAttribute("foundDoctors", foundDoctors);
        StringBuilder sb = new StringBuilder("Found results for query \"");
        sb.append(doctorName)
                .append("\"");
        model.addAttribute("query", sb.toString());

        return "doctor_search";
    }

}
