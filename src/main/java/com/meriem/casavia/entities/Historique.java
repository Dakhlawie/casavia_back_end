package com.meriem.casavia.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
public class Historique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historique_id;
    private String check_in;
    private String check_out;
    private String lieu;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
