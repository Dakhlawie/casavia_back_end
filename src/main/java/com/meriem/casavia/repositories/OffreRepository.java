package com.meriem.casavia.repositories;


import com.meriem.casavia.entities.OffreHebergement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OffreRepository extends JpaRepository<OffreHebergement,Long> {
    @Query("SELECT o FROM OffreHebergement o JOIN o.hebergement h WHERE h.person.person_id = :personId")
    List<OffreHebergement> findByPersonId(@Param("personId") Long personId);
}
