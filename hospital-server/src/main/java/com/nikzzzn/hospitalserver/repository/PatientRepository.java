package com.nikzzzn.hospitalserver.repository;

import com.nikzzzn.hospitalserver.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

    @Query(value = "SELECT * FROM patients p, " +
                   "(SELECT DISTINCT patient_id FROM appointments a WHERE a.doctor_id = :id) s " +
                   "WHERE p.id = s.patient_id", nativeQuery = true)
    List<Patient> findAllByDoctorId(Integer id);

    List<Patient> findByPatientNameContaining(String name);

}
