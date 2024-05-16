package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.*;

import com.meriem.casavia.repositories.*;

import com.meriem.casavia.services.HebergementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    @Autowired
    UserRepository userRep;


    @GetMapping("/all")
    public List<Hebergement> getAllHebergements() {
        return hebergementService.getAllHebergements();
    }

    @PostMapping("/save")
    public Hebergement createHebergement(@RequestParam Long categorie,@RequestParam Long person,@RequestBody Hebergement hebergement) {


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
        Hebergement h =hebergementRep.findById(id).get();

        List<Equipement> equipements = h.getEquipements();
        equipements.clear();
        h.getChambres().forEach(room -> room.getEquipements().clear());


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
   ;

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
    @GetMapping("/liked-hebergements")
    public List<Hebergement> getLikedHebergements(@RequestParam Long userId) {
        User u=userRep.findById(userId).get();
        return hebergementService.findHebergementsLikedByUser(u);
    }

    @GetMapping("/reserved-hebergements")
    public List<Hebergement> getReservedHebergements(@RequestParam Long userId) {
        User u=userRep.findById(userId).get();
        return hebergementService.findHebergementsReservedByUser(u);
    }
    @GetMapping("/number")
    public long getTotalhebergements() {
        return hebergementRep.countBy();
    }
    @GetMapping("/{hebergementId}/categories")
    public List<CategorieEquipement> getCategoriesOfEquipementsByHebergementId(@PathVariable Long hebergementId) {
        return hebergementRep.findCategoriesOfEquipementsByHebergementId(hebergementId);
    }
    @GetMapping("/{hebergementId}/languages")
    public List<language> getLanguagesByHebergementId(@PathVariable Long hebergementId) {
        return hebergementRep.findLanguagesByHebergementId(hebergementId);
    }
    @GetMapping("/from-recommandation/{recommandationId}")
    public Hebergement getHebergementFromRecommandation(@PathVariable Long recommandationId) {
        return hebergementService.getHebergementFromRecommandation(recommandationId);
    }
    @GetMapping("/hasImages/{id}")
    public boolean hasImages(@PathVariable("id")long id){
        return this.hebergementRep.hasImages(id);
    }
    @GetMapping("/hasEquipements/{id}")
    public boolean hasEquipements(@PathVariable("id")long id){
        return this.hebergementRep.hasEquipments(id);
    }
    @GetMapping("/hasLocation/{id}")
    public boolean hasLocation(@PathVariable("id")long id){
        return this.hebergementRep.hasLocation(id);

    }
    @GetMapping("/hasRooms/{id}")
    public boolean hasRooms(@PathVariable("id")long id){
        return this.hebergementRep.hasRooms(id);

    }


}
