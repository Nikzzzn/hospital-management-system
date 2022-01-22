package com.nikzzzn.hospitalserver.repository;

import com.nikzzzn.hospitalserver.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    List<Doctor> findAllBySpecialty_Id(Integer id);

    List<Doctor> findByDoctorNameContaining(String name);

    @Query(value = "SELECT * FROM doctors d, " +
                   "(SELECT DISTINCT doctor_id FROM appointments a WHERE a.patient_id = :id) s " +
                   "WHERE d.id = s.doctor_id", nativeQuery = true)
    List<Doctor> findAllByPatientId(Integer id);

}
