package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.Categorie;
import com.meriem.casavia.entities.CategorieEquipement;
import com.meriem.casavia.entities.Equipement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EquipementRepository extends JpaRepository<Equipement,Long> {
 List<Equipement> getEquipementByCategorie(CategorieEquipement c);

 @Query("SELECT e FROM Equipement e " +
         "WHERE e.categorie.id = :categorieId " +
         "AND :hebergementId IN (SELECT h.id FROM e.hebergements h)")
 List<Equipement> findByCategorieIdAndHebergementId(@Param("categorieId") Long categorieId, @Param("hebergementId") Long hebergementId);

}
