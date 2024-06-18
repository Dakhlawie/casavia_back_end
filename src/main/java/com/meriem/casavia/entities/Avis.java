package com.meriem.casavia.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
public class Avis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long avisId;
    @Lob
    @Column( columnDefinition = "TEXT")
    private String avis;
    @Lob
    @Column( columnDefinition = "TEXT")
    private String avisNegative;
    private int staff;
    private int location;
    private int comfort;
    private int facilities;
    private int cleanliness;
    private int security;
    private LocalDate date;
    private double moyenne;

    @ManyToOne
    private User user;

    @ManyToOne
    private Hebergement hebergement;
}

