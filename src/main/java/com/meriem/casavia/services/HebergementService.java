package com.meriem.casavia.services;

import com.meriem.casavia.entities.Categorie;
import com.meriem.casavia.entities.Hebergement;

import java.util.List;

public interface HebergementService {
    Hebergement createHebergement(Hebergement hebergement);
    Hebergement updateHebergement( long id,Hebergement Hebergement);
    void deleteHebergement(Long id);

    List<Hebergement> getAllHebergements();
    List<Hebergement> findByCategorie (Categorie categorie);
    List<Hebergement> findByCategorieIdCat(Long id);
    List<Hebergement> findHebergementsByPerson(Long id);
    Hebergement getHebergementByNom(String nom);
    Long gethebergement_idByNom(String nom);
    Hebergement getHebergementById(Long id);

}
