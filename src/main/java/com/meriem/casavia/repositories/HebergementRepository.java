package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.Categorie;
import com.meriem.casavia.entities.Hebergement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HebergementRepository extends JpaRepository<Hebergement,Long> {
    List<Hebergement> findByCategorie (Categorie categorie);
    List<Hebergement> findByCategorieIdCat(Long id);
    Hebergement getHebergementsByNom(String nom);
    Long getIdByNom(String nom);
    @Query("SELECT h FROM Hebergement h WHERE h.person.person_id = :personId")
    List<Hebergement> findHebergementsByPersonId(Long personId);
    List<Hebergement> findByVille(String ville);
    List<Hebergement> findByVilleAndCategorie(String ville,Categorie c);
    @Query("SELECT h.categorie FROM Hebergement h WHERE h.hebergement_id = :id")
    Categorie findCategorieByHebergementId(@Param("id") Long id);
    @Query("SELECT h FROM Hebergement h WHERE h.ville LIKE %:query% OR h.pays LIKE %:query% AND h.categorie = :categorie")
    List<Hebergement> findByVilleOrPaysAndCategorie(@Param("query") String query, @Param("categorie") Categorie categorie);



}
