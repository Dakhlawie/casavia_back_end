package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.*;
import com.meriem.casavia.repositories.CategoryEquipementRepository;
import com.meriem.casavia.repositories.ChambreRepository;
import com.meriem.casavia.repositories.EquipementRepository;
import com.meriem.casavia.repositories.HebergementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipement")
@CrossOrigin(origins = "*")
public class EquipementRestController {
    @Autowired
    EquipementRepository equipementRep;
    @Autowired
    HebergementRepository hebergementRep;
    @Autowired
    ChambreRepository chambreRepository;
    @Autowired
    CategoryEquipementRepository categorieRep;
    @GetMapping("/all")
    public List<Equipement> getAllEquipement(){
        return equipementRep.findAll();
    }
    @GetMapping("/getByCategory")
    public List<Equipement> getEquipementByCategorie(@RequestParam long id){
        CategorieEquipement c=categorieRep.findById(id).get();
        return equipementRep.getEquipementByCategorie(c);


    }
    @PostMapping("/save")
    public Equipement addEquipement(@RequestParam long categorie,@RequestBody Equipement e){
        CategorieEquipement c=categorieRep.findById(categorie).get();
        e.setCategorie(c);
        return  this.equipementRep.save(e);
    }
    @PutMapping("/update")
    public Equipement updateEquipement(@RequestBody  Equipement e,@RequestParam long id){
        Equipement c=equipementRep.findById(id).get();
        c.setCategorie(e.getCategorie());
        c.setNom(e.getNom());
        return  this.equipementRep.save(c);
    }
    @DeleteMapping("/delete/{id}")
    public void DeleteEquipement(@PathVariable("id") long id){
        this.equipementRep.deleteById(id);
    }
    @PostMapping("/save/hebergement")
    public Equipement addEquipementToHebergement(@RequestParam long hebergementId, @RequestParam long equipementId) {
        Hebergement hebergement = hebergementRep.findById(hebergementId).get();
        Equipement equipement = equipementRep.findById(equipementId).get();

        List<Equipement> equipements = hebergement.getEquipements();
        equipements.add(equipement);
        hebergement.setEquipements(equipements);


        hebergementRep.save(hebergement);

        return equipement;
    }
    @PostMapping("/save/room/{id}")
    public Equipement addEquipementToRoom(@PathVariable("id") long id, @RequestParam long equipementId) {
        Chambre c = chambreRepository.findById(id).get();
        Equipement equipement = equipementRep.findById(equipementId).get();

        List<Equipement> equipements = c.getEquipements();
        equipements.add(equipement);
        c.setEquipements(equipements);


        chambreRepository.save(c);

        return equipement;
    }
    @PostMapping("/remove/hebergement")
    public void removeEquipementFromHebergement(@RequestParam long hebergementId, @RequestParam long equipementId) {
        Hebergement hebergement = hebergementRep.findById(hebergementId).get();
        Equipement equipement = equipementRep.findById(equipementId).get();

        List<Equipement> equipements = hebergement.getEquipements();
        equipements.remove(equipement);
        hebergement.setEquipements(equipements);

        hebergementRep.save(hebergement);
    }
    @PostMapping("/remove/room/{id}")
    public void removeEquipementFromRoom(@PathVariable("id") long id, @RequestParam long equipementId) {
        Chambre c = chambreRepository.findById(id).get();
        Equipement equipement = equipementRep.findById(equipementId).get();

        List<Equipement> equipements = c.getEquipements();
        equipements.remove(equipement);
        c.setEquipements(equipements);

        chambreRepository.save(c);
    }
    @GetMapping("/findByCategorieHebregement")
    public List<Equipement> getByCategorieHbergement(@RequestParam long categorie,@RequestParam long hebergement){
        return equipementRep.findByCategorieIdAndHebergementId(categorie,hebergement);
    }
    @GetMapping("/{id}")
    public Equipement getEquipementById(@PathVariable("id") long id){
        return this.equipementRep.findById(id).get();
    }


}
