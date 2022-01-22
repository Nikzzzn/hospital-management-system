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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Test
    public void should_save_patient() {
        Patient patient = new Patient("test", LocalDate.now(), "Male", "phone", "address", new HashSet<>());
        Patient savedPatient = patientRepository.save(patient);
        Assertions.assertThat(savedPatient).usingRecursiveComparison().ignoringFields("id").isEqualTo(patient);
    }

    @Test
    public void should_find_all_patients() {
        Patient patient1 = new Patient("test", LocalDate.now(), "Male", "phone", "address", new HashSet<>());
        Patient patient2 = new Patient("test", LocalDate.now(), "Male", "phone", "address", new HashSet<>());

        int sizeBefore = patientRepository.findAll().size();

        patientRepository.save(patient1);
        patientRepository.save(patient2);

        int sizeAfter = patientRepository.findAll().size();

        Assertions.assertThat(sizeAfter - sizeBefore).isEqualTo(2);
    }

    @Test
    public void should_delete_patient(){
        Patient patient = new Patient("test", LocalDate.now(), "Male", "phone", "address", new HashSet<>());
        Patient savedPatient = patientRepository.save(patient);
        List<Patient> before = patientRepository.findAll();
        patientRepository.deleteById(savedPatient.getId());
        List<Patient> after = patientRepository.findAll();
        Assertions.assertThat(after.size()).isLessThan(before.size());
    }

    @Test
    public void should_find_by_id() {
        Patient patient = new Patient("test", LocalDate.now(), "Male", "phone", "address", new HashSet<>());
        Patient saved = patientRepository.save(patient);
        Optional<Patient> result = patientRepository.findById(saved.getId());
        Assertions.assertThat(result).isPresent().contains(saved);
    }

    @Test
    public void should_not_find_by_id() {
        Optional<Patient> result = patientRepository.findById(-1);
        Assertions.assertThat(result).isNotPresent();
    }

    @Test
    public void should_find_by_name() {
        Patient patient1 = new Patient("patient 1", LocalDate.now(), "Male", "phone", "address", new HashSet<>());
        Patient patient2 = new Patient("patient 2", LocalDate.now(), "Male", "phone", "address", new HashSet<>());
        Patient patient3 = new Patient("something", LocalDate.now(), "Male", "phone", "address", new HashSet<>());

        patientRepository.save(patient1);
        patientRepository.save(patient2);
        patientRepository.save(patient3);

        List<Patient> result1 = patientRepository.findByPatientNameContaining("patient");
        List<Patient> result2 = patientRepository.findByPatientNameContaining("some");
        Assertions.assertThat(result1.size()).isEqualTo(2);
        Assertions.assertThat(result1).contains(patient1, patient2);
        Assertions.assertThat(result2.size()).isEqualTo(1);
        Assertions.assertThat(result2).contains(patient3);
    }

    @Test
    public void should_find_by_doctor_id() {
        Patient patient1 = new Patient("patient 1", LocalDate.now(), "Male", "phone", "address", new HashSet<>());
        Patient patient2 = new Patient("patient 2", LocalDate.now(), "Male", "phone", "address", new HashSet<>());
        Patient patient3 = new Patient("patient 3", LocalDate.now(), "Male", "phone", "address", new HashSet<>());
        patient1 = patientRepository.save(patient1);
        patient2 = patientRepository.save(patient2);
        patient3 = patientRepository.save(patient3);

        Specialty specialty1 = new Specialty("specialty 1", null);
        Specialty specialty2 = new Specialty("specialty 2", null);
        specialty1 = specialtyRepository.save(specialty1);
        specialty2 = specialtyRepository.save(specialty2);

        Doctor doctor1 = new Doctor("doctor 1", specialty1, null);
        Doctor doctor2 = new Doctor("doctor 2", specialty2, null);
        doctor1 = doctorRepository.save(doctor1);
        doctor2 = doctorRepository.save(doctor2);

        Appointment appointment1 = new Appointment(doctor1, patient1, LocalDate.now(), LocalTime.of(10,0));
        Appointment appointment2 = new Appointment(doctor1, patient2, LocalDate.now(), LocalTime.of(11,0));
        Appointment appointment3 = new Appointment(doctor2, patient3, LocalDate.now(), LocalTime.now());
        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);
        appointmentRepository.save(appointment3);

        List<Patient> result = patientRepository.findAllByDoctorId(doctor1.getId());
        Assertions.assertThat(result).contains(patient1, patient2);
        Assertions.assertThat(result).doesNotContain(patient3);
    }

}