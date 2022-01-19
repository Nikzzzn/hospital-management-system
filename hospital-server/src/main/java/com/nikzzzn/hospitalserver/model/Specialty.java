package com.nikzzzn.hospitalserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "specialties")
public class Specialty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String specialty;

    @JsonIgnore
    @OneToMany(
            mappedBy = "specialty",
            cascade = CascadeType.PERSIST
    )
    private Set<Doctor> doctors;

    public Specialty(){}

    public Specialty(Integer id, String specialty, Set<Doctor> doctors) {
        this.id = id;
        this.specialty = specialty;
        this.doctors = doctors;
    }

    public Specialty(String specialty, Set<Doctor> doctors) {
        this.specialty = specialty;
        this.doctors = doctors;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
    }

}
