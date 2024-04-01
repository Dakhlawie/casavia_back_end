package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Hebergement;

import com.meriem.casavia.repositories.CategorieRepository;

import com.meriem.casavia.repositories.PersonRepository;
import com.meriem.casavia.services.HebergementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/hebergement")
@RestController
@CrossOrigin
public class HebergementRestController {
    @Autowired
    private HebergementService hebergementService;
    @Autowired
    CategorieRepository categorieRep;
    @Autowired
    PersonRepository  personRep;
    @GetMapping("/all")
    public List<Hebergement> getAllHebergements() {
        return hebergementService.getAllHebergements();
    }

    @PostMapping("/save")
    public Hebergement createHebergement(@RequestParam Long categorie,@RequestParam Long person,@RequestBody Hebergement hebergement) {
        System.out.println(categorie);
        System.out.println(person);

        hebergement.setCategorie(categorieRep.findById(categorie).get());
        hebergement.setPerson(personRep.findById(person).get());

        return hebergementService.createHebergement(hebergement);

    }

    @PutMapping("/update/{id}")
    public Hebergement updateHebergement(@PathVariable Long id ,@RequestBody Hebergement hebergement) {
        return hebergementService.updateHebergement(id,hebergement);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteHebergement(@PathVariable("id") Long id) {
        hebergementService.deleteHebergement(id);
    }
    @GetMapping ("/hebergements/{idCat}")
    public List<Hebergement> getHebergementsByCatId(@PathVariable("idCat") Long idCat) {
        return hebergementService.findByCategorieIdCat(idCat);
    }
    @GetMapping ("/get/{nom}")
    public Hebergement getHebergementsByNom(@PathVariable("nom") String nom) {
        return hebergementService.getHebergementByNom(nom);
    }
    @GetMapping ("/getId/{nom}")
    public Long getHebergement_idByNom(@PathVariable String nom) {
        return hebergementService.gethebergement_idByNom(nom);
    }
    @GetMapping ("/getByPerson/{id}")
    public List<Hebergement> findHebergementsByPerson(@PathVariable("id") Long id) {
        return hebergementService.findHebergementsByPerson(id);
    }
    @GetMapping ("/{id}")
    public Hebergement getHebergementById(@PathVariable("id") Long id) {
        return hebergementService.getHebergementById(id);
    }


}
