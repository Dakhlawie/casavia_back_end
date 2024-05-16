package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.Question;
import com.meriem.casavia.entities.Reponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReponseRepository extends JpaRepository<Reponse,Long> {
}
