package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Categorie;
import com.meriem.casavia.entities.CategorieEquipement;
import com.meriem.casavia.entities.Equipement;
import com.meriem.casavia.entities.roomType;
import com.meriem.casavia.repositories.CategoryEquipementRepository;
import com.meriem.casavia.repositories.EquipementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorie_equipement")
@CrossOrigin
public class CategorieEquipementRestController {
    @Autowired
    CategoryEquipementRepository categorieRep;
    @Autowired
    EquipementRepository equipementRep;
    @GetMapping("/all")
    public List<CategorieEquipement> getAllCategorie(){
        return categorieRep.findAll();
    }
    @PostMapping("/save")
    public CategorieEquipement addCategorie(@RequestBody CategorieEquipement c){
        return  this.categorieRep.save(c);
    }
    @PutMapping("/update")
    public CategorieEquipement updateCategorie(@RequestBody  CategorieEquipement c,@RequestParam long id){
        CategorieEquipement c1=categorieRep.findById(id).get();
        c1.setNom(c.getNom());
        c1.setDescription(c.getDescription());
        return  this.categorieRep.save(c1);
    }

    @DeleteMapping("/delete/{id}")
    public void DeleteCategorie(@PathVariable("id") long id){
        this.categorieRep.deleteById(id);
    }
    @GetMapping("/categorieByEquipementId")
    public CategorieEquipement getCategorieByEquipementId(@RequestParam Long equipementId) {
        Equipement equipement = equipementRep.findById(equipementId).get();
        return equipement.getCategorie();
    }
    @GetMapping("/nom")
    public CategorieEquipement getCategoryByNom(@RequestParam String nom){
       return  categorieRep.getCategorieEquipementByNom(nom);
    }
    @GetMapping("/{id}")
    public CategorieEquipement getCategorieById(@PathVariable("id") long id){
        return this.categorieRep.findById(id).get();
    }
    @GetMapping("/by-category")
    public List<Equipement> getEquipementsByCategory(@RequestParam String category) {
        List<Equipement> equipements = equipementRep.findByCategorieName(category);
        return equipements;
    }
}
