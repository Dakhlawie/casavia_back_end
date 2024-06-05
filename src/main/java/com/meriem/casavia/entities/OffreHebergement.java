package com.meriem.casavia.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
public class OffreHebergement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offre_id;
    private String discount;
    private String start_date;
    private String end_date;
    private boolean allRooms;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "hebergement_id")
    private Hebergement hebergement;

    @ElementCollection
    @CollectionTable(name = "offrehebergement_rooms", joinColumns = @JoinColumn(name = "offre_id"))
    @Column(name = "room_id")
    private List<Long> rooms;
}
