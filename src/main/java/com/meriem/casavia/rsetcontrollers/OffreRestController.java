package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Hebergement;

import com.meriem.casavia.entities.OffreHebergement;
import com.meriem.casavia.repositories.HebergementRepository;
import com.meriem.casavia.repositories.OffreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offre")
@CrossOrigin
public class OffreRestController {
    @Autowired
    OffreRepository offreRep;
    @Autowired
    HebergementRepository hebergementRep;
    @PostMapping("/save")
    public OffreHebergement ajouterOffre(@RequestBody OffreHebergement offre, @RequestParam long id){
        System.out.println("*************************");
        System.out.println(offre);
        Hebergement h=hebergementRep.findById(id).get();
        offre.setHebergement(h);
        return offreRep.save(offre);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteOffre(@PathVariable("id") long id){
        this.offreRep.deleteById(id);
    }
    @PutMapping("/update/{id}")
    public OffreHebergement updateOffre(@RequestBody OffreHebergement o,@PathVariable("id")  long id){
        OffreHebergement off=offreRep.findById(id).get();
        off.setRooms(o.getRooms());
        off.setDiscount(o.getDiscount());
        off.setEnd_date(o.getEnd_date());
        off.setStart_date(o.getStart_date());


        return offreRep.save(off);
    }
    @GetMapping("/list")
    public List<OffreHebergement> offreList(@RequestParam long id){
   return this.offreRep.findByPersonId(id);
    }
    @GetMapping("/{id}")
    public OffreHebergement getOffreById( @PathVariable("id") long id){
return this.offreRep.findById(id).get();
    }

}
