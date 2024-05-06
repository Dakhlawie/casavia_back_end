package com.meriem.casavia.services;

import com.meriem.casavia.entities.Categorie;
import com.meriem.casavia.entities.Hebergement;
import com.meriem.casavia.repositories.HebergementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HebergementServiceImpl implements HebergementService{

    @Autowired
    private HebergementRepository hebergementRepository;

    @Override
    public Hebergement createHebergement(Hebergement hebergement) {
        return hebergementRepository.save(hebergement);
    }
    @Override
    public Hebergement updateHebergement(long id, Hebergement hebergement) {

        Hebergement h = hebergementRepository.findById(id).get();
        h.setNom(hebergement.getNom());
        h.setDescription(hebergement.getDescription());
       h.setVille(hebergement.getVille());
       h.setPays(hebergement.getPays());
        h.setPrix(hebergement.getPrix());
        h.setDistance(hebergement.getDistance());
        h.setPhone(hebergement.getPhone());
       h.setEmail(hebergement.getEmail());
        h.setAdresse(hebergement.getAdresse());
        h.setPolitiqueAnnulation(hebergement.getPolitiqueAnnulation());
        h.setNbEtoile(hebergement.getNbEtoile());
        h.setNb_Salles_De_Bains(hebergement.getNb_Salles_De_Bains());
       h.setNb_Chambres(hebergement.getNb_Chambres());
        h.setWebsite(hebergement.getWebsite());
        h.setFacebook(hebergement.getFacebook());
        h.setInstagram(hebergement.getInstagram());
        h.setFax(hebergement.getFax());
        h.setEquipements(hebergement.getEquipements());
        h.setLanguages(hebergement.getLanguages());
        h.setCountry_code(hebergement.getCountry_code());
        h.setCurrency(hebergement.getCurrency());
        h.setCancellationfees(hebergement.getCancellationfees());



            return hebergementRepository.save(h);

    }



    @Override
    public void deleteHebergement(Long id) {
        hebergementRepository.deleteById(id);
    }



    @Override
    public List<Hebergement> getAllHebergements() {
        return hebergementRepository.findAll();
    }
    @Override
    public List<Hebergement> findByCategorie(Categorie categorie) {
        return hebergementRepository.findByCategorie(categorie);
    }
    @Override
    public List<Hebergement> findByCategorieIdCat(Long id) {
        return hebergementRepository.findByCategorieIdCat(id);
    }

    @Override
    public List<Hebergement> findHebergementsByPerson(Long id) {
        return hebergementRepository.findHebergementsByPersonId(id);
    }

    @Override
    public Hebergement getHebergementByNom(String nom) {
        return hebergementRepository.getHebergementsByNom(nom);
    }

    @Override
    public Long gethebergement_idByNom(String nom) {
        return hebergementRepository.getIdByNom(nom);
    }

    @Override
    public Hebergement getHebergementById(Long id) {
        return hebergementRepository.findById(id).get();
    }
}
