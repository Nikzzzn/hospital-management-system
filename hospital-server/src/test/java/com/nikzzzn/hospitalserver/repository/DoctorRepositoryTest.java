package com.nikzzzn.hospitalserver.repository;

import com.nikzzzn.hospitalserver.model.Appointment;
import com.nikzzzn.hospitalserver.model.Doctor;
import com.nikzzzn.hospitalserver.model.Patient;
import com.nikzzzn.hospitalserver.model.Specialty;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DoctorRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Test
    public void should_save_doctor() {
        Specialty specialty = saveSpecialty(1);

        Doctor doctor = new Doctor("test", specialty, null);
        Doctor savedDoctor = doctorRepository.save(doctor);
        Assertions.assertThat(savedDoctor).usingRecursiveComparison().ignoringFields("id").isEqualTo(doctor);
    }

    @Test
    public void should_find_all_doctors() {
        Specialty specialty1 = saveSpecialty(1);
        Specialty specialty2 = saveSpecialty(2);

        Doctor doctor1 = new Doctor("test1", specialty1, null);
        Doctor doctor2 = new Doctor("test2", specialty2, null);

        int sizeBefore = doctorRepository.findAll().size();

        doctorRepository.save(doctor1);
        doctorRepository.save(doctor2);

        int sizeAfter = doctorRepository.findAll().size();

        Assertions.assertThat(sizeAfter - sizeBefore).isEqualTo(2);
    }

    @Test
    public void should_delete_doctor(){
        Specialty specialty1 = saveSpecialty(1);
        Doctor doctor = new Doctor("test1", specialty1, null);
        Doctor savedDoctor = doctorRepository.save(doctor);
        List<Doctor> before = doctorRepository.findAll();
        doctorRepository.deleteById(savedDoctor.getId());
        List<Doctor> after = doctorRepository.findAll();
        Assertions.assertThat(after.size()).isLessThan(before.size());
    }

    @Test
    public void should_find_by_id() {
        Specialty specialty1 = saveSpecialty(1);
        Doctor doctor = new Doctor("test1", specialty1, null);
        Doctor saved = doctorRepository.save(doctor);
        Optional<Doctor> result = doctorRepository.findById(saved.getId());
        Assertions.assertThat(result).isPresent().contains(saved);;
    }

    @Test
    public void should_not_find_by_id() {
        Optional<Doctor> result = doctorRepository.findById(-1);
        Assertions.assertThat(result).isNotPresent();
    }

    @Test
    public void should_find_by_name() {
        Specialty specialty1 = saveSpecialty(1);
        Specialty specialty2 = saveSpecialty(2);
        Doctor doctor1 = new Doctor("doctor 1", specialty1, null);
        Doctor doctor2 = new Doctor("doctor 2", specialty1, null);
        Doctor doctor3 = new Doctor("something", specialty2, null);

        doctorRepository.save(doctor1);
        doctorRepository.save(doctor2);
        doctorRepository.save(doctor3);

        List<Doctor> result1 = doctorRepository.findByDoctorNameContaining("doctor");
        List<Doctor> result2 = doctorRepository.findByDoctorNameContaining("some");
        Assertions.assertThat(result1).contains(doctor1, doctor2);
        Assertions.assertThat(result1.size()).isEqualTo(2);
        Assertions.assertThat(result2).contains(doctor3);
        Assertions.assertThat(result2.size()).isEqualTo(1);
    }

    @Test
    public void should_find_by_specialty_id(){
        Specialty specialty1 = saveSpecialty(1);
        Specialty specialty2 = saveSpecialty(2);
        Doctor doctor1 = new Doctor("doctor 1", specialty1, null);
        Doctor doctor2 = new Doctor("doctor 2", specialty1, null);
        Doctor doctor3 = new Doctor("doctor 3", specialty2, null);

        int before = doctorRepository.findAllBySpecialty_Id(specialty1.getId()).size();

        doctorRepository.save(doctor1);
        doctorRepository.save(doctor2);
        doctorRepository.save(doctor3);

        int after = doctorRepository.findAllBySpecialty_Id(specialty1.getId()).size();

        List<Doctor> result = doctorRepository.findAllBySpecialty_Id(specialty1.getId());
        Assertions.assertThat(after - before).isEqualTo(2);
    }

    @Test
    public void should_find_by_patient_id(){
        Specialty specialty1 = saveSpecialty(1);
        Specialty specialty2 = saveSpecialty(2);

        Patient patient1 = new Patient("patient 1", LocalDate.now(), "Male", "phone", "address", new HashSet<>());
        Patient patient2 = new Patient("patient 2", LocalDate.now(), "Male", "phone", "address", new HashSet<>());
        patient1 = patientRepository.save(patient1);
        patient2 = patientRepository.save(patient2);

        Doctor doctor1 = new Doctor("doctor 1", specialty1, null);
        Doctor doctor2 = new Doctor("doctor 2", specialty2, null);
        Doctor doctor3 = new Doctor("doctor 3", specialty1, null);
        doctor1 = doctorRepository.save(doctor1);
        doctor2 = doctorRepository.save(doctor2);
        doctor3 = doctorRepository.save(doctor3);

        Appointment appointment1 = new Appointment(doctor1, patient1, LocalDate.now(), LocalTime.of(10,0));
        Appointment appointment2 = new Appointment(doctor2, patient2, LocalDate.now(), LocalTime.of(11,0));
        Appointment appointment3 = new Appointment(doctor3, patient2, LocalDate.now(), LocalTime.now());
        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);
        appointmentRepository.save(appointment3);

        List<Doctor> result = doctorRepository.findAllByPatientId(patient2.getId());
        Assertions.assertThat(result).contains(doctor2, doctor3);
        Assertions.assertThat(result).doesNotContain(doctor1);
    }

    public Specialty saveSpecialty(int id){
        Specialty specialty = new Specialty(id,"specialty" + id, null);
        specialty = specialtyRepository.save(specialty);
        return specialty;
    }

}