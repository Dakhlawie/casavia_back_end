package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.Hebergement;
import com.meriem.casavia.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video,Long> {
    Video getVideoByHebergement(Hebergement h);
}
