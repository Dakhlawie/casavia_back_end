package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.Chambre;
import com.meriem.casavia.entities.Hebergement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChambreRepository extends JpaRepository<Chambre,Long> {
    List<Chambre> findByHebergement(Hebergement h);


}
