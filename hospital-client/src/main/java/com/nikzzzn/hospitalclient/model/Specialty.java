package com.nikzzzn.hospitalclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Specialty {

    @JsonProperty
    public Integer id;

    @JsonProperty("specialty")
    public String name;

    public Specialty(){}

    public Specialty(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
