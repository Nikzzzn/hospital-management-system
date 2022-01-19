package com.nikzzzn.hospitalserver.repository;

import com.nikzzzn.hospitalserver.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

    List<Patient> findAllByAppointmentsDoctor_Id(Integer id);

    List<Patient> findByPatientNameContaining(String name);

}
