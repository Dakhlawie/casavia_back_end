package com.meriem.casavia.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String transactionId;
    private String payerId;
    private String currency;
    private double total;
    private String paymentStatus;
    private String paymentMethod;
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "reservation_id", referencedColumnName = "id")
    private Reservation reservation;
}
