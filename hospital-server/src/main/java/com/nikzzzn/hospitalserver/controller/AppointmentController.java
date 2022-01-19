package com.nikzzzn.hospitalserver.controller;

import com.nikzzzn.hospitalserver.model.Appointment;
import com.nikzzzn.hospitalserver.model.Doctor;
import com.nikzzzn.hospitalserver.service.AppointmentService;
import com.nikzzzn.hospitalserver.service.DoctorService;
import com.nikzzzn.hospitalserver.service.PatientService;
import com.nikzzzn.hospitalserver.service.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping(value = {"/", "/index"})
    public List<Appointment> showPatients(Model model){
        List<Appointment> listOfAppointments = appointmentService.findAll();

        return listOfAppointments;
    }

    @GetMapping("/new_appointment")
    public String showNewAppointmentPage(Model model){
        Appointment appointment = new Appointment();
        model.addAttribute("appointment", appointment);
        model.addAttribute("listOfPatients", patientService.findAll());
        model.addAttribute("listOfSpecialties", specialtyService.findAll());

        return "new_appointment";
    }

    @RequestMapping("/new_appointment_doctor")
    public String postNewAppointmentChooseDoctorPage(Model model,
                                                     @ModelAttribute("appointment") Appointment appointment,
                                                     @RequestParam String chosenSpecialty){
        List<Doctor> listOfDoctors = doctorService.findBySpecialtyId(Integer.parseInt(chosenSpecialty));
        Map<Integer, List<String>> doctorsAndHours = new Hashtable<>();
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
                    appointmentService.findAllByDoctorIdAndDate(d.getId(), appointment.getAppointmentDate());
            for(Appointment a : bookedAppointments){
                availableHours.removeIf(t -> t.equals(a.getAppointmentTime()));
            }
            doctorsAndHours.put(d.getId(), availableHours.stream().map(LocalTime::toString).toList());
        }
        model.addAttribute(appointment);
        model.addAttribute("listOfDoctors",listOfDoctors);
        model.addAttribute("doctorsAndHours",doctorsAndHours);

        return "new_appointment_doctor";
    }

    @PostMapping("/save_appointment")
    public String saveAppointment(@ModelAttribute("appointment") Appointment appointment) {
        appointmentService.saveAppointment(appointment);

        return "redirect:/";
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
    public String deleteAppointment(@PathVariable(name = "id") int id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/";
    }

}
