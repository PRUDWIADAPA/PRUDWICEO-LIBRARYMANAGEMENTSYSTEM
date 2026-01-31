package org.example.libreraymanagementsystem_byprudwiceo.Model;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String name;

    private int quantity;

    private String location;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Author author;

    @ManyToMany(mappedBy = "booksRead")
    private Set<Member> member = new HashSet<>();


}
