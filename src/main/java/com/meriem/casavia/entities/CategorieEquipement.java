package com.meriem.casavia.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
public class CategorieEquipement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categorie_id;

    private String nom;

    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Equipement> equipements ;
}
