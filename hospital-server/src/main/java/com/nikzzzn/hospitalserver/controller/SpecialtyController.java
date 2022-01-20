package com.nikzzzn.hospitalserver.controller;

import com.nikzzzn.hospitalserver.model.Patient;
import com.nikzzzn.hospitalserver.model.Specialty;
import com.nikzzzn.hospitalserver.service.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SpecialtyController {

    @Autowired
    private SpecialtyService specialtyService;

    @GetMapping("/specialties")
    public List<Specialty> getSpecialties(){
        return specialtyService.findAll();
    }

}
