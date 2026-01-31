package org.example.libreraymanagementsystem_byprudwiceo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor


public class Schedule {

     public enum Confirmation{
         Confirmed,Waiting,Cancelled
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


     private int empId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Confirmation confirmationStatus;



}
