package com.meriem.casavia.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Hebergement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hebergement_id;
    private String nom;
    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    private String ville;
    private String pays;
    private double prix;
    private String distance;
    private String phone;
    private String email;
    private String adresse;
    private String politiqueAnnulation;
    private String nbEtoile;
    private double superficie;
    private int nb_Salles_De_Bains;
    private int nb_Chambres;

    private String website;
    private String facebook;
    private String instagram;
    private String fax;
    private String country_code;
    private String currency;
    private String cancellationfees;


    @ManyToOne
    private Categorie categorie;
    @OneToMany (mappedBy = "hebergement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "hebergement_equipement",
            joinColumns = @JoinColumn(name = "hebergement_id"),
            inverseJoinColumns = @JoinColumn(name = "equipement_id"))
    @JsonIgnore
    private List<Equipement> equipements;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "hebergement_language",
            joinColumns = @JoinColumn(name = "hebergement_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
    private List<language> languages;
    @OneToMany(mappedBy = "hebergement", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore

    private List<Avis> avisList;
    @OneToMany(mappedBy = "hebergement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chambre> chambres;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToMany(mappedBy = "hebergement", cascade = CascadeType.ALL, orphanRemoval = true)

    private List<Position> positions;

    @OneToMany(mappedBy = "hebergement",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Reservation> reservations;
    @OneToMany(mappedBy = "hebergement",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Dates> reservationDates;
    @OneToMany(mappedBy = "hebergement",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Like> likes = new HashSet<>();
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "video_id", referencedColumnName = "video_id")
    private Video video;
    @Override
    public String toString() {
        return "Hebergement{" +
                "hebergement_id=" + hebergement_id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", ville='" + ville + '\'' +
                ", pays='" + pays + '\'' +
                ", prix=" + prix +
                ", distance='" + distance + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", adresse='" + adresse + '\'' +
                ", politiqueAnnulation='" + politiqueAnnulation + '\'' +
                ", nbEtoile='" + nbEtoile + '\'' +
                ", superficie=" + superficie +
                ", nb_Salles_De_Bains=" + nb_Salles_De_Bains +
                ", nb_Chambres=" + nb_Chambres +
                ", website='" + website + '\'' +
                ", facebook='" + facebook + '\'' +
                ", instagram='" + instagram + '\'' +
                ", fax='" + fax + '\'' +
                '}';
    }

}
