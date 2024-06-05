package com.meriem.casavia.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "hebergement_id", nullable = false)
    private Hebergement hebergement;

    @OneToOne(mappedBy = "question", cascade = CascadeType.ALL)
    private Reponse reponse;


    private String content;

    private LocalDateTime dateAsked;
    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +


                ", content='" + content + '\'' +
                ", dateAsked=" + dateAsked +
                '}';
    }
}
