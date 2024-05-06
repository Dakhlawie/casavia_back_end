package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Categorie;
import com.meriem.casavia.entities.Equipement;
import com.meriem.casavia.entities.Hebergement;

import com.meriem.casavia.entities.Video;
import com.meriem.casavia.repositories.CategorieRepository;

import com.meriem.casavia.repositories.HebergementRepository;
import com.meriem.casavia.repositories.PersonRepository;
import com.meriem.casavia.repositories.VideoRepository;
import com.meriem.casavia.services.HebergementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    @Autowired
    HebergementRepository hebergementRep;
    @Autowired
    VideoRepository videoRep;
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
        System.out.println("***************");
        System.out.println(id+""+hebergement.getCountry_code());
        return hebergementService.updateHebergement(id,hebergement);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteHebergement(@PathVariable("id") Long id) {
        Hebergement h =hebergementRep.findById(id).get();
        List<Equipement> equipements = h.getEquipements();
        equipements.clear();


        hebergementRep.save(h);
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
    @GetMapping("/search")
    public List<Hebergement> searchHebergements(@RequestParam String terme) {

       return hebergementRep.findByVille(terme);
    }
    @GetMapping("/findByCategorieVille")
    public List<Hebergement> searchHebergements(@RequestParam String ville, @RequestParam long id) {
       Categorie  categorie=this.categorieRep.findById(id).get();
        return hebergementRep.findByVilleOrPaysAndCategorie(ville,categorie);
    }
    @PutMapping("/video")
    public Hebergement ajouterVideo(@RequestParam("video_id") long video_id, @RequestParam("hebergement_id") long hebergement_id){
        System.out.println(hebergement_id);
        System.out.println(video_id);

        Video v=videoRep.findById(video_id).get();
        Hebergement h=hebergementRep.findById(hebergement_id).get();
        h.setVideo(v);
        hebergementRep.save(h);
        return h;
    }
    @DeleteMapping("/deleteVideo")
    public void deleteVideo(@RequestParam long hebergement){
        Hebergement h=this.hebergementRep.findById(hebergement).get();
        h.setVideo(null);
        hebergementRep.save(h);
    }
    @GetMapping("/equipements")
    public List<Equipement> getEquipements(@RequestParam long hebergement){
        Hebergement h=hebergementRep.findById(hebergement).get();
        List<Equipement> equipements=h.getEquipements();
        return equipements;
    }
    @GetMapping("/getcategorie")
    public Categorie getCategorie(@RequestParam long id){
        return this.hebergementRep.findCategorieByHebergementId(id);
    }


}
