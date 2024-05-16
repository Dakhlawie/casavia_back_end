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
    public Categorie updateCategorie(@RequestBody  Categorie c,@RequestParam long id){
        Categorie c1=categorieRep.findById(id).get();
        c1.setType(c.getType());
        c1.setDescription(c.getDescription());

        return categorieRep.save(c1);

    }
    @DeleteMapping("/delete/{id}")
    public void DeleteCategorie(@PathVariable("id") long id){
        this.categorieRep.deleteById(id);
    }
    @GetMapping("/{id}")
    public Categorie getCategoryById(@PathVariable("id") long id){
        return this.categorieRep.findById(id).get();
    }
    @GetMapping("/by-hebergement/{hebergementId}")
    public Categorie getCategorieByHebergement(@PathVariable Long hebergementId) {
        System.out.println(hebergementId);
        return categorieRep.findCategorieByHebergementId(hebergementId);
    }

}
