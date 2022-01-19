package com.nikzzzn.hospitalserver.converter;

import com.nikzzzn.hospitalserver.model.Patient;
import com.nikzzzn.hospitalserver.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PatientConverter implements Converter<String, Patient> {

    @Autowired
    private PatientService patientService;

    @Override
    public Patient convert(String id) {
        return patientService.findById(Integer.parseInt(id));
    }

}
