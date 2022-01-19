package com.nikzzzn.hospitalserver.service;

import com.nikzzzn.hospitalserver.model.Doctor;
import com.nikzzzn.hospitalserver.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public void saveDoctor(Doctor doctor){
        doctorRepository.save(doctor);
    }

    public void deleteDoctor(Integer id){
        doctorRepository.deleteById(id);
    }

    public Doctor findById(Integer id){
        return doctorRepository.findById(id).orElse(new Doctor());
    }

    public List<Doctor> findAll(){
        return doctorRepository.findAll();
    }

    public List<Doctor> findByName(String name) {
        return doctorRepository.findByDoctorNameContaining(name);
    }

    public List<Doctor> findBySpecialtyId(Integer id){
        return doctorRepository.findAllBySpecialty_Id(id);
    }

    public List<Doctor> findByPatientId(Integer id){
        return doctorRepository.findAllByAppointmentsPatient_Id(id);
    }

}
