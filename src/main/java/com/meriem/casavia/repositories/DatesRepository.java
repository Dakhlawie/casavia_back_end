package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.Chambre;
import com.meriem.casavia.entities.Dates;
import com.meriem.casavia.entities.Hebergement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DatesRepository extends JpaRepository<Dates,Long> {
    List<Dates> findByChambre(Chambre chambre);
    List<Dates> findByHebergement(Hebergement h);
}
