package com.nikzzzn.hospitalserver.controller;

import com.nikzzzn.hospitalserver.model.Appointment;
import com.nikzzzn.hospitalserver.model.Doctor;
import com.nikzzzn.hospitalserver.model.Specialty;
import com.nikzzzn.hospitalserver.service.AppointmentService;
import com.nikzzzn.hospitalserver.service.DoctorService;
import com.nikzzzn.hospitalserver.service.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Stream;

@RestController
public class DoctorController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private SpecialtyService specialtyService;

    @GetMapping("/doctors")
    public List<Doctor> getDoctors(){
        return doctorService.findAll();
    }

    @GetMapping("/doctors_by_specialty")
    public List<Doctor> getDoctorsBySpecialty(@RequestParam Integer id){
        return doctorService.findBySpecialtyId(id);
    }

    @GetMapping("/doctors_by_id")
    public Doctor getDoctorById(@RequestParam Integer id){
        return doctorService.findById(id);
    }

    @GetMapping("/available_doctors")
    public Map<Integer, List<LocalTime>> getDoctorsWith(@RequestParam LocalDate date,
                                                        @RequestParam Integer specialty){
        List<Doctor> listOfDoctors = doctorService.findBySpecialtyId(specialty);
        Map<Integer, List<LocalTime>> doctorsAndHours = new Hashtable<>();
        Stream<LocalTime> beforeLunchTime = Stream.iterate(LocalTime.of(9, 0),
                        t -> t.plusMinutes(30))
                .limit(8);
        Stream<LocalTime> afterLunchTime = Stream.iterate(LocalTime.of(14, 0),
                        t -> t.plusMinutes(30))
                .limit(8);
        List<LocalTime> allAvailableHours = Stream.concat(beforeLunchTime, afterLunchTime).toList();
        for(Doctor d : listOfDoctors){
            List<LocalTime> availableHours = new ArrayList<>(allAvailableHours);
            List<Appointment> bookedAppointments =
                    appointmentService.findAllByDoctorIdAndDate(d.getId(), date);
            for(Appointment a : bookedAppointments){
                availableHours.removeIf(t -> t.equals(a.getAppointmentTime()));
            }
            doctorsAndHours.put(d.getId(), availableHours);
        }
        return doctorsAndHours;
    }

    @PostMapping("/save_doctor")
    public Doctor saveDoctor(@RequestParam Integer id,
                           @RequestParam String name,
                           @RequestParam Integer specialtyId) {
        Doctor doctor = doctorService.findById(id);
        doctor = (doctor != null) ? doctor : new Doctor();
        doctor.setDoctorName(name);
        Specialty specialty = specialtyService.findById(specialtyId);
        doctor.setSpecialty(specialty);
        return doctorService.saveDoctor(doctor);
    }

    @GetMapping("/delete_doctor/{id}")
    public void deleteDoctor(@PathVariable(name = "id") int id) {
        doctorService.deleteDoctor(id);
    }

    @PostMapping("/doctor_search")
    public List<Doctor> doctorSearch(@RequestParam String name) {
        return doctorService.findByName(name);
    }

    @GetMapping("/patient_doctors/{id}")
    public List<Doctor> getDoctorsByPatientId(@PathVariable(name = "id") int id){
        return doctorService.findByPatientId(id);
    }

}
