package org.example.libreraymanagementsystem_byprudwiceo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Assistant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Assistant_Id;

    private String Assistant_Name;

    @ElementCollection
    private List<LocalDateTime> availability;
}
