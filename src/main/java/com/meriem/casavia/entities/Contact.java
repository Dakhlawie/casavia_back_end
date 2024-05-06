package com.meriem.casavia.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long contact_id;
    private String name;
    private String email;
    @Lob
    @Column(name = "message", columnDefinition = "TEXT")
    private String message;
}
