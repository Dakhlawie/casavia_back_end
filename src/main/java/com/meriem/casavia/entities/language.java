package com.meriem.casavia.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
public class language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long language_id;
    private String language;

}
