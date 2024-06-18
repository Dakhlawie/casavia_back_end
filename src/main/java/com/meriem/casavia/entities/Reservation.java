package com.meriem.casavia.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String numeroReservation;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "hebergement_id")
    private Hebergement hebergement;
    private String dateCheckIn;
    private String dateCheckOut;
    private double prix;
    private String currency;

    @Enumerated(EnumType.STRING)
    private EtatReservation etat;
    private int nbRooms;
    @ElementCollection
    @CollectionTable(name = "reservation_rooms", joinColumns = @JoinColumn(name = "reservation_id"))
    @Column(name = "room_id")
    private List<Long> rooms;
    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL)
    private Payment payment;
}
