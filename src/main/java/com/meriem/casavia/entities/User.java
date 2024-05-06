package com.meriem.casavia.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Reservation> reservations;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Like> likes = new HashSet<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Historique> historiques;
    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", tlf='" + tlf + '\'' +
                ", pays='" + pays + '\'' +
                ", email='" + email + '\'' +

                ", image_path='" + image_path + '\'' +
                '}';
    }




}
