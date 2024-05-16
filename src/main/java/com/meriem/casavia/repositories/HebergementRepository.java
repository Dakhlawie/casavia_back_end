package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.Categorie;
import com.meriem.casavia.entities.CategorieEquipement;
import com.meriem.casavia.entities.Hebergement;
import com.meriem.casavia.entities.language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

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
    @Query("SELECT h FROM Hebergement h WHERE (h.ville LIKE %:query% OR h.pays LIKE %:query%) AND h.categorie = :categorie")
    List<Hebergement> findByVilleOrPaysAndCategorie(@Param("query") String query, @Param("categorie") Categorie categorie);

    long countBy();
    @Query("SELECT DISTINCT c FROM Hebergement h JOIN h.equipements e JOIN e.categorie c WHERE h.hebergement_id = :hebergementId")
    List<CategorieEquipement> findCategoriesOfEquipementsByHebergementId(@Param("hebergementId") Long hebergementId);
    @Query("SELECT h.languages FROM Hebergement h WHERE h.hebergement_id = :hebergementId")
    List<language> findLanguagesByHebergementId(@Param("hebergementId") Long hebergementId);

    @Query("SELECT COUNT(i) > 0 FROM Image i WHERE i.hebergement.hebergement_id = :hebergementId")
    boolean hasImages(Long hebergementId);


    @Query("SELECT COUNT(e) > 0 FROM Equipement e JOIN e.hebergements h WHERE h.hebergement_id = :hebergementId")
    boolean hasEquipments(Long hebergementId);


    @Query("SELECT COUNT(p) > 0 FROM Position p WHERE p.hebergement.hebergement_id = :hebergementId")
    boolean hasLocation(Long hebergementId);


    @Query("SELECT COUNT(c) > 0 FROM Chambre c WHERE c.hebergement.hebergement_id = :hebergementId")
    boolean hasRooms(Long hebergementId);
}





