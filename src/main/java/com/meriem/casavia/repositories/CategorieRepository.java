package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.Categorie;
import com.meriem.casavia.entities.Hebergement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategorieRepository extends JpaRepository<Categorie,Long> {
    @Query("SELECT c FROM Categorie c JOIN c.hebergements h WHERE h.hebergement_id = :hebergementId")
    Categorie findCategorieByHebergementId(@Param("hebergementId") Long hebergementId);
}
