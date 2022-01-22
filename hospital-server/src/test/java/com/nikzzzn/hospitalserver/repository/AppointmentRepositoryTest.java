package com.nikzzzn.hospitalserver.repository;

import com.nikzzzn.hospitalserver.model.Appointment;
import com.nikzzzn.hospitalserver.model.Doctor;
import com.nikzzzn.hospitalserver.model.Patient;
import com.nikzzzn.hospitalserver.model.Specialty;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AppointmentRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Test
    public void should_save_appointment() {
        Appointment appointment = prepareAppointment(1,1,1);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        Assertions.assertThat(savedAppointment).usingRecursiveComparison().ignoringFields("id").isEqualTo(appointment);
    }

    @Test
    public void should_find_all_appointments() {
        Appointment appointment1 = prepareAppointment(1, 1, 1);
        Appointment appointment2 = prepareAppointment(2, 2, 2);
        int sizeBefore = appointmentRepository.findAll().size();

        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);

        int sizeAfter = appointmentRepository.findAll().size();

        Assertions.assertThat(sizeAfter - sizeBefore).isEqualTo(2);
    }

    @Test
    public void should_delete_appointment(){
        Appointment appointment = prepareAppointment(1, 1, 1);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        List<Appointment> before = appointmentRepository.findAll();
        appointmentRepository.deleteById(savedAppointment.getId());
        List<Appointment> after = appointmentRepository.findAll();
        Assertions.assertThat(after.size()).isLessThan(before.size());
    }

    @Test
    public void should_find_by_id() {
        Appointment appointment = prepareAppointment(1, 1, 1);
        Appointment saved = appointmentRepository.save(appointment);
        Optional<Appointment> result = appointmentRepository.findById(saved.getId());
        Assertions.assertThat(result).isPresent().contains(saved);
    }

    @Test
    public void should_not_find_by_id() {
        Optional<Appointment> result = appointmentRepository.findById(-1);
        Assertions.assertThat(result).isNotPresent();
    }

    @Test
    public void should_find_by_doctor_id_and_date(){
        Specialty specialty = new Specialty("specialty", null);
        specialty = specialtyRepository.save(specialty);
        Doctor doctor1 = new Doctor("doctor1", specialty, null);
        Doctor doctor2 = new Doctor("doctor2", specialty, null);
        doctor1 = doctorRepository.save(doctor1);
        doctor2 = doctorRepository.save(doctor2);
        Patient patient1 = new Patient(1, "patient1", LocalDate.of(1990,1,1), "male", "phone" , "address", null);
        patient1 = patientRepository.save(patient1);
        Appointment appointment1 = new Appointment(doctor1, patient1, LocalDate.now(), LocalTime.of(10,0));
        Appointment appointment2 = new Appointment(doctor2, patient1, LocalDate.now(), LocalTime.of(11,0));
        Appointment appointment3 = new Appointment(doctor1, patient1, LocalDate.now(), LocalTime.of(12,0));
        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);
        appointmentRepository.save(appointment3);

        List<Appointment> result = appointmentRepository.findAllByDoctor_IdAndAppointmentDate(doctor1.getId(), LocalDate.now());
        Assertions.assertThat(result).contains(appointment1, appointment3);
        Assertions.assertThat(result).doesNotContain(appointment2);
    }

    @Test
    public void should_find_for_current_week(){
        int before = appointmentRepository.findForCurrentWeek().size();

        Specialty specialty = new Specialty("specialty", null);
        specialty = specialtyRepository.save(specialty);
        Doctor doctor1 = new Doctor("doctor1", specialty, null);
        Doctor doctor2 = new Doctor("doctor2", specialty, null);
        doctor1 = doctorRepository.save(doctor1);
        doctor2 = doctorRepository.save(doctor2);
        Patient patient1 = new Patient(1, "patient1", LocalDate.of(1990,1,1), "male", "phone" , "address", null);
        patient1 = patientRepository.save(patient1);
        Appointment appointment1 = new Appointment(doctor1, patient1, LocalDate.now(), LocalTime.of(10,0));
        Appointment appointment2 = new Appointment(doctor2, patient1, LocalDate.of(2020,1,1), LocalTime.of(11,0));
        Appointment appointment3 = new Appointment(doctor1, patient1, LocalDate.now(), LocalTime.of(12,0));
        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);
        appointmentRepository.save(appointment3);

        List<Appointment> result = appointmentRepository.findForCurrentWeek();
        Assertions.assertThat(result).contains(appointment1, appointment3);
        Assertions.assertThat(result).doesNotContain(appointment2);
        Assertions.assertThat(result.size() - before).isEqualTo(2);
    }

    @Test
    public void should_find_for_current_week_by_patient_id(){
        Specialty specialty = new Specialty("specialty", null);
        specialty = specialtyRepository.save(specialty);
        Doctor doctor1 = new Doctor("doctor1", specialty, null);
        Doctor doctor2 = new Doctor("doctor2", specialty, null);
        doctor1 = doctorRepository.save(doctor1);
        doctor2 = doctorRepository.save(doctor2);
        Patient patient1 = new Patient(1, "patient1", LocalDate.of(1990,1,1), "male", "phone" , "address", null);
        Patient patient2 = new Patient(2, "patient2", LocalDate.of(1990,1,1), "male", "phone" , "address", null);
        patient1 = patientRepository.save(patient1);
        patient2 = patientRepository.save(patient2);
        Appointment appointment1 = new Appointment(doctor1, patient1, LocalDate.now(), LocalTime.of(10,0));
        Appointment appointment2 = new Appointment(doctor2, patient1, LocalDate.of(2020,1,1), LocalTime.of(11,0));
        Appointment appointment3 = new Appointment(doctor1, patient2, LocalDate.now(), LocalTime.of(12,0));
        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);
        appointmentRepository.save(appointment3);

        List<Appointment> result = appointmentRepository.findForCurrentWeekByPatientId(patient1.getId());
        Assertions.assertThat(result).contains(appointment1);
        Assertions.assertThat(result).doesNotContain(appointment2, appointment3);
    }

    @Test
    public void should_find_for_current_week_by_doctor_id(){
        Specialty specialty = new Specialty("specialty", null);
        specialty = specialtyRepository.save(specialty);
        Doctor doctor1 = new Doctor("doctor1", specialty, null);
        Doctor doctor2 = new Doctor("doctor2", specialty, null);
        doctor1 = doctorRepository.save(doctor1);
        doctor2 = doctorRepository.save(doctor2);
        Patient patient1 = new Patient(1, "patient1", LocalDate.of(1990,1,1), "male", "phone" , "address", null);
        Patient patient2 = new Patient(2, "patient2", LocalDate.of(1990,1,1), "male", "phone" , "address", null);
        patient1 = patientRepository.save(patient1);
        patient2 = patientRepository.save(patient2);
        Appointment appointment1 = new Appointment(doctor1, patient1, LocalDate.now(), LocalTime.of(10,0));
        Appointment appointment2 = new Appointment(doctor2, patient1, LocalDate.of(2020,1,1), LocalTime.of(11,0));
        Appointment appointment3 = new Appointment(doctor1, patient2, LocalDate.of(2020,1,1), LocalTime.of(12,0));
        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);
        appointmentRepository.save(appointment3);

        List<Appointment> result = appointmentRepository.findForCurrentWeekByDoctorId(doctor1.getId());
        Assertions.assertThat(result).contains(appointment1);
        Assertions.assertThat(result).doesNotContain(appointment2, appointment3);
    }

    @Test
    public void should_find_all_by_doctor_id(){
        Specialty specialty = new Specialty("specialty", null);
        specialty = specialtyRepository.save(specialty);
        Doctor doctor1 = new Doctor("doctor1", specialty, null);
        Doctor doctor2 = new Doctor("doctor2", specialty, null);
        doctor1 = doctorRepository.save(doctor1);
        doctor2 = doctorRepository.save(doctor2);
        Patient patient1 = new Patient(1, "patient1", LocalDate.of(1990,1,1), "male", "phone" , "address", null);
        Patient patient2 = new Patient(2, "patient2", LocalDate.of(1990,1,1), "male", "phone" , "address", null);
        patient1 = patientRepository.save(patient1);
        patient2 = patientRepository.save(patient2);
        Appointment appointment1 = new Appointment(doctor1, patient1, LocalDate.now(), LocalTime.of(10,0));
        Appointment appointment2 = new Appointment(doctor2, patient1, LocalDate.of(2020,1,1), LocalTime.of(11,0));
        Appointment appointment3 = new Appointment(doctor1, patient2, LocalDate.of(2020,1,1), LocalTime.of(12,0));
        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);
        appointmentRepository.save(appointment3);

        List<Appointment> result = appointmentRepository.findAllByDoctorId(doctor1.getId());
        Assertions.assertThat(result).contains(appointment1, appointment3);
        Assertions.assertThat(result).doesNotContain(appointment2);
    }

    @Test
    public void should_find_all_by_patient_id(){
        Specialty specialty = new Specialty("specialty", null);
        specialty = specialtyRepository.save(specialty);
        Doctor doctor1 = new Doctor("doctor1", specialty, null);
        Doctor doctor2 = new Doctor("doctor2", specialty, null);
        doctor1 = doctorRepository.save(doctor1);
        doctor2 = doctorRepository.save(doctor2);
        Patient patient1 = new Patient(1, "patient1", LocalDate.of(1990,1,1), "male", "phone" , "address", null);
        Patient patient2 = new Patient(2, "patient2", LocalDate.of(1990,1,1), "male", "phone" , "address", null);
        patient1 = patientRepository.save(patient1);
        patient2 = patientRepository.save(patient2);
        Appointment appointment1 = new Appointment(doctor1, patient1, LocalDate.now(), LocalTime.of(10,0));
        Appointment appointment2 = new Appointment(doctor2, patient1, LocalDate.of(2020,1,1), LocalTime.of(11,0));
        Appointment appointment3 = new Appointment(doctor1, patient2, LocalDate.now(), LocalTime.of(12,0));
        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);
        appointmentRepository.save(appointment3);

        List<Appointment> result = appointmentRepository.findAllByPatientId(patient1.getId());
        Assertions.assertThat(result).contains(appointment1, appointment2);
        Assertions.assertThat(result).doesNotContain(appointment3);
    }

    private Appointment prepareAppointment(int doctorId, int specialtyId, int patientId){
        Specialty specialty = new Specialty(specialtyId,"specialty" + specialtyId, null);
        specialty = specialtyRepository.save(specialty);

        Doctor doctor = new Doctor(doctorId, "doctor" + doctorId, specialty, null);
        doctor = doctorRepository.save(doctor);

        Patient patient = new Patient(patientId, "patient" + patientId, LocalDate.of(1990,1,1), "male", "phone" , "address", null);
        patient = patientRepository.save(patient);

        return new Appointment(doctor, patient, LocalDate.now(), LocalTime.of(10,0));
    }

}