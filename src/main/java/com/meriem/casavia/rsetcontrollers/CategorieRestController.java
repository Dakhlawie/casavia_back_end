package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Avis;
import com.meriem.casavia.entities.Categorie;
import com.meriem.casavia.entities.Hebergement;
import com.meriem.casavia.repositories.CategorieRepository;
import com.meriem.casavia.repositories.HebergementRepository;
import com.meriem.casavia.services.AvisService;
import com.meriem.casavia.services.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorie")
@CrossOrigin
public class CategorieRestController {
    @Autowired
    CategorieService categorieServ;
    @Autowired
    CategorieRepository categorieRep;
    @Autowired
    HebergementRepository hebergementRep;
    @GetMapping("/all")
    public List<Categorie> getAllCategorie(){
        return categorieServ.getAllCategorie();
    }
    @PostMapping("/save")
    public Categorie addCategorie(@RequestBody Categorie c){
        return  this.categorieRep.save(c);
    }
    @PutMapping("/update")
    public Categorie updateCategorie(@RequestBody  Categorie c){
        return  this.categorieRep.save(c);
    }
    @DeleteMapping("/delete/{id}")
    public void DeleteCategorie(@PathVariable("id") long id){
        this.categorieRep.deleteById(id);
    }

}
