package com.meriem.casavia.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
public class AccountClosure {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long account_id;

    @Lob
    @Column(name = "message", columnDefinition = "TEXT")
    private String message;
}
