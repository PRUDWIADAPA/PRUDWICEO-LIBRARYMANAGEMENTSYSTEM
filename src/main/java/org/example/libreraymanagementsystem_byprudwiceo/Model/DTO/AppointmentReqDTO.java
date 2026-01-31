package org.example.libreraymanagementsystem_byprudwiceo.Model.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentReqDTO {

    private int id;

    private LocalDateTime requestedTime;
}
