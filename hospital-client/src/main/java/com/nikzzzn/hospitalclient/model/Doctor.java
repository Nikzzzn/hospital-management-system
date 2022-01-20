package com.nikzzzn.hospitalclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Doctor {

    @JsonProperty
    public Integer id;

    @JsonProperty("doctorName")
    public String name;

    @JsonProperty
    public Specialty specialty;

    public Doctor(){}

    public Doctor(Integer id, String name, Specialty specialty) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
    }
}
