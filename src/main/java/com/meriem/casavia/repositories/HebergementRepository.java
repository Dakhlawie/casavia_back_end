package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.Categorie;
import com.meriem.casavia.entities.Hebergement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HebergementRepository extends JpaRepository<Hebergement,Long> {
    List<Hebergement> findByCategorie (Categorie categorie);
    List<Hebergement> findByCategorieIdCat(Long id);
    Hebergement getHebergementsByNom(String nom);
    Long getIdByNom(String nom);
    @Query("SELECT h FROM Hebergement h WHERE h.person.person_id = :personId")
    List<Hebergement> findHebergementsByPersonId(Long personId);



}
