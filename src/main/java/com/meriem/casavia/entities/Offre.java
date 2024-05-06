package com.meriem.casavia.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
public class Offre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offre_id;
    private String discount;
    private String start_date;
    private String end_date;
    private boolean allRooms=true;
    private long hebergement;
    @ManyToMany
    @JoinTable(
            name = "offre_chambre",
            joinColumns = @JoinColumn(name = "offre_id"),
            inverseJoinColumns = @JoinColumn(name = "chambre_id")
    )
    private List<Chambre> rooms;
}
