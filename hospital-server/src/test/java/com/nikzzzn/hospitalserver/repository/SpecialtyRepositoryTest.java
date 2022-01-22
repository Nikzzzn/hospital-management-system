package com.nikzzzn.hospitalserver.repository;

import com.nikzzzn.hospitalserver.model.Doctor;
import com.nikzzzn.hospitalserver.model.Specialty;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SpecialtyRepositoryTest {

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    public void should_find_all_specialties() {
        Specialty specialty1 = new Specialty("specialty1", null);
        Specialty specialty2 = new Specialty("specialty2", null);

        int sizeBefore = specialtyRepository.findAll().size();

        specialtyRepository.save(specialty1);
        specialtyRepository.save(specialty2);

        int sizeAfter = specialtyRepository.findAll().size();

        Assertions.assertThat(sizeAfter - sizeBefore).isEqualTo(2);
    }

    @Test
    public void should_find_by_id() {
        Specialty specialty = new Specialty("specialty", null);

        Specialty saved = specialtyRepository.save(specialty);
        Optional<Specialty> result = specialtyRepository.findById(saved.getId());
        Assertions.assertThat(result).isPresent().contains(saved);
    }

    @Test
    public void should_not_find_by_id() {
        Optional<Specialty> result = specialtyRepository.findById(-1);
        Assertions.assertThat(result).isNotPresent();
    }

}