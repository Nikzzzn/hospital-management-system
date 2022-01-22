package com.nikzzzn.hospitalserver.service;

import com.nikzzzn.hospitalserver.model.Appointment;
import com.nikzzzn.hospitalserver.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment saveAppointment(Appointment appointment){
        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Integer id){
        appointmentRepository.deleteById(id);
    }

    public Appointment findById(Integer id){
        return appointmentRepository.findById(id).orElse(new Appointment());
    }

    public List<Appointment> findAll(){
        return appointmentRepository.findAll();
    }

    public List<Appointment> findAllByDoctorIdAndDate(Integer id, LocalDate date){
        return appointmentRepository.findAllByDoctor_IdAndAppointmentDate(id, date);
    }

    public List<Appointment> findForCurrentWeek() {
        return appointmentRepository.findForCurrentWeek();
    }

    public List<Appointment> findForCurrentWeekByPatientId(Integer id){
        return appointmentRepository.findForCurrentWeekByPatientId(id);
    }

    public List<Appointment> findAllByPatientId(Integer id){
        return appointmentRepository.findAllByPatientId(id);
    }

    public List<Appointment> findForCurrentWeekByDoctorId(Integer id){
        return appointmentRepository.findForCurrentWeekByDoctorId(id);
    }

    public List<Appointment> findAllByDoctorId(Integer id){
        return appointmentRepository.findAllByDoctorId(id);
    }

}
