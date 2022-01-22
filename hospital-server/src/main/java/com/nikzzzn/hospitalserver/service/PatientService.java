package com.nikzzzn.hospitalserver.service;

import com.nikzzzn.hospitalserver.model.Patient;
import com.nikzzzn.hospitalserver.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient savePatient(Patient patient){
        return patientRepository.save(patient);
    }

    public void deletePatient(Integer id){
        patientRepository.deleteById(id);
    }

    public List<Patient> findAll(){
        return patientRepository.findAll();
    }

    public Patient findById(Integer id){
        return patientRepository.findById(id).orElse(new Patient());
    }

    public List<Patient> findByName(String name) {
        return patientRepository.findByPatientNameContaining(name);
    }

    public List<Patient> findByDoctorId(Integer id){
        return patientRepository.findAllByDoctorId(id);
    }

}
