package com.nikzzzn.hospitalserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "doctor_name")
    private String doctorName;

    @ManyToOne
    @JoinColumn(name = "specialty_id", referencedColumnName = "id")
    private Specialty specialty;

    @JsonIgnore
    @OneToMany(mappedBy = "doctor",
            cascade = CascadeType.PERSIST
    )
    private Set<Appointment> appointments;

    public Doctor(){}

    public Doctor(Integer id, String doctorName, Specialty specialty, Set<Appointment> appointments) {
        this.id = id;
        this.doctorName = doctorName;
        this.specialty = specialty;
        this.appointments = appointments;
    }

    public Doctor(String doctorName, Specialty specialty, Set<Appointment> appointments) {
        this.doctorName = doctorName;
        this.specialty = specialty;
        this.appointments = appointments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

}
