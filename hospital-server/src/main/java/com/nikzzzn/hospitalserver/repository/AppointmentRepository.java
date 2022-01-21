package com.nikzzzn.hospitalserver.repository;

import com.nikzzzn.hospitalserver.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    List<Appointment> findAllByDoctor_IdAndAppointmentDate(Integer id, LocalDate date);

    @Query(value = "SELECT * FROM appointments a " +
                   "WHERE YEARWEEK(a.appointment_date, 1) = YEARWEEK(CURDATE(), 1) " +
                   "ORDER BY a.appointment_date DESC, a.appointment_time ASC", nativeQuery = true)
    List<Appointment> findForCurrentWeek();

    @Query(value = "SELECT * FROM appointments a " +
            "ORDER BY a.appointment_date DESC, a.appointment_time ASC", nativeQuery = true)
    List<Appointment> findAll();
}
