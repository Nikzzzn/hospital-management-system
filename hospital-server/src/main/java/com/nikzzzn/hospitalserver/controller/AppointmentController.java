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

    @GetMapping("/appointments")
    public List<Appointment> getAppointments(){
        return appointmentService.findAll();
    }

    @GetMapping("/weeks_schedule")
    public List<Appointment> getWeekSchedule(){
        return appointmentService.findForCurrentWeek();
    }

    @PostMapping("/save_appointment")
    public Appointment saveAppointment(@RequestParam Integer id,
                                       @RequestParam Integer doctorId,
                                       @RequestParam Integer patientId,
                                       @RequestParam LocalDate date,
                                       @RequestParam LocalTime time) {
        Doctor doctor = doctorService.findById(doctorId);
        Patient patient = patientService.findById(patientId);
        Appointment appointment = new Appointment(id, doctor, patient, date, time);
        return appointmentService.saveAppointment(appointment);
    }

    @GetMapping("/delete_appointment/{id}")
    public void deleteAppointment(@PathVariable(name = "id") int id) {
        appointmentService.deleteAppointment(id);
    }

    @GetMapping("/appointments_week_patient/{id}")
    public List<Appointment> getForCurrentWeekByPatientId(@PathVariable(name = "id") int id) {
        return appointmentService.findForCurrentWeekByPatientId(id);
    }

    @GetMapping("/appointments_patient/{id}")
    public List<Appointment> getAllByPatientId(@PathVariable(name = "id") int id) {
        return appointmentService.findAllByPatientId(id);
    }

    @GetMapping("/appointments_week_doctor/{id}")
    public List<Appointment> getForCurrentWeekByDoctorId(@PathVariable(name = "id") int id) {
        return appointmentService.findForCurrentWeekByDoctorId(id);
    }

    @GetMapping("/appointments_doctor/{id}")
    public List<Appointment> getAllByDoctorId(@PathVariable(name = "id") int id) {
        return appointmentService.findAllByDoctorId(id);
    }
}
