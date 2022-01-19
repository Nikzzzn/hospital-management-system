package com.nikzzzn.hospitalserver.service;

import com.nikzzzn.hospitalserver.model.Specialty;
import com.nikzzzn.hospitalserver.repository.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialtyService {

    @Autowired
    private SpecialtyRepository specialtyRepository;

    public List<Specialty> findAll(){
        return specialtyRepository.findAll();
    }

}
