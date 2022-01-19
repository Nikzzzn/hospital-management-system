package com.nikzzzn.hospitalserver.repository;

import com.nikzzzn.hospitalserver.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    List<Doctor> findAllBySpecialty_Id(Integer id);

    List<Doctor> findAllByAppointmentsPatient_Id(Integer id);

    List<Doctor> findByDoctorNameContaining(String name);

}
