package com.nikzzzn.hospitalclient.holder;

public record AppointmentHolder(
        String id,
        String doctor,
        String patient,
        String date,
        String time
) {}