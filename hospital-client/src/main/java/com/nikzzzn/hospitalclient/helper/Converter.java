package com.nikzzzn.hospitalclient.helper;

import com.nikzzzn.hospitalclient.holder.AppointmentHolder;
import com.nikzzzn.hospitalclient.model.Appointment;

import java.util.List;

public class Converter {

    public static List<AppointmentHolder> convertToHolder(List<Appointment> input){
        return input.stream().map(Converter::convertToHolder).toList();
    }

    public static AppointmentHolder convertToHolder(Appointment input){
        return new AppointmentHolder(
                input.id.toString(),
                input.doctor.name,
                input.patient.name,
                input.date.toString(),
                input.time.toString()
        );
    }

}
