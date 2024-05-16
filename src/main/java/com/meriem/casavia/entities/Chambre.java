package com.meriem.casavia.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Chambre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chambre_id;

    private String type;

    private String floor;
    private String view;
    private String bed;
    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    private double prix;
    private String image_path;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "chambre_equipement",
            joinColumns = @JoinColumn(name = "chambre_id"),
            inverseJoinColumns = @JoinColumn(name = "equipement_id")
    )
    private List<Equipement> equipements;
    private long nbRoom;
    @JsonIgnore
    @ManyToOne
    private Hebergement hebergement;
    @OneToMany(mappedBy = "chambre",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Dates> reservationDates;
    @ManyToMany(mappedBy = "chambres")
    private List<Reservation> reservations;

    @Override
    public String toString() {
        return "Chambre{" +
                "chambre_id=" + chambre_id +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", image_path='" + image_path + '\'' +
                ", equipements=" + equipements +
                ", nbRoom=" + nbRoom +
                '}';
    }



}
