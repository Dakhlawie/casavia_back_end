package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
