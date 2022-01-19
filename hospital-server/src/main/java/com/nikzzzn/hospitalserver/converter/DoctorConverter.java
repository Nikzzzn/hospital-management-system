package com.nikzzzn.hospitalserver.converter;

import com.nikzzzn.hospitalserver.model.Doctor;
import com.nikzzzn.hospitalserver.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DoctorConverter implements Converter<String, Doctor> {

    @Autowired
    private DoctorService doctorService;

    @Override
    public Doctor convert(String id) {
        return doctorService.findById(Integer.parseInt(id));
    }

}
