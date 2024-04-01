package com.meriem.casavia.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    private String nom;
    private String prenom;
    private String tlf;
    private String pays;
    private String email;
    private String mot_de_passe;
     private String image_path;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Avis> avisList;



}
