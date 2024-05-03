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
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long video_id;

    @Lob
    @Column(length = 16777215)
    private byte[] videoContent;
    @JsonIgnore
    @OneToOne(mappedBy = "video")
    private Hebergement hebergement;

}
