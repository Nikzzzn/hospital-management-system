package com.nikzzzn.hospitalserver.controller;

import com.nikzzzn.hospitalserver.model.Appointment;
import com.nikzzzn.hospitalserver.model.Doctor;
import com.nikzzzn.hospitalserver.model.Patient;
import com.nikzzzn.hospitalserver.service.AppointmentService;
import com.nikzzzn.hospitalserver.service.DoctorService;
import com.nikzzzn.hospitalserver.service.PatientService;
import com.nikzzzn.hospitalserver.service.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private SpecialtyService specialtyService;

    @GetMapping("/")
    public List<Appointment> getAppointments(){
        return appointmentService.findAll();
    }

    /*@GetMapping("/new_appointment")
    public String showNewAppointmentPage(Model model){
        Appointment appointment = new Appointment();
        model.addAttribute("appointment", appointment);
        model.addAttribute("listOfPatients", patientService.findAll());
        model.addAttribute("listOfSpecialties", specialtyService.findAll());

        return "new_appointment";
    }*/

    @RequestMapping("/save_appointment")
    public void saveAppointment(@RequestParam Integer id,
                                @RequestParam Integer doctorId,
                                @RequestParam Integer patientId,
                                @RequestParam LocalDate date,
                                @RequestParam LocalTime time) {
        Doctor doctor = doctorService.findById(doctorId);
        Patient patient = patientService.findById(patientId);
        Appointment appointment = new Appointment(id, doctor, patient, date, time);
        appointmentService.saveAppointment(appointment);
    }

    @GetMapping("/edit_appointment/{id}")
    public ModelAndView showEditAppointmentPage(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("edit_appointment");
        Appointment appointment = appointmentService.findById(id);
        mav.addObject("appointment", appointment);
        mav.addObject("listOfDoctors", doctorService.findAll());
        mav.addObject("listOfPatients", patientService.findAll());

        return mav;
    }

    @GetMapping("/delete_appointment/{id}")
    public void deleteAppointment(@PathVariable(name = "id") int id) {
        appointmentService.deleteAppointment(id);
    }

}
