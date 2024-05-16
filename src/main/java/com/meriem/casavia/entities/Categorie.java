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
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCat;

    private String type;
    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    private String icon;
    @JsonIgnore
    @OneToMany(mappedBy = "categorie")

    private List<Hebergement> hebergements;
    @Override
    public String toString() {
        return "Categorie{" +
                "idCat=" + idCat +
                ", type='" + type + '\'' +
                '}';
    }

}
