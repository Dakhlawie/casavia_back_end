package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Avis;
import com.meriem.casavia.services.AvisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avis")
@CrossOrigin
public class AvisRestController {
    @Autowired
    AvisService avisServ;
    @PostMapping("/save")
    public Avis ajouterAvis(@RequestBody Avis avis){
        return avisServ.ajouterAvis(avis);
    }
    @PutMapping("/update")
    public Avis modifierAvis(@RequestBody Avis avis){
        return avisServ.modifierAvis(avis);
    }
    @DeleteMapping("/delete/{id}")
    public void supprimerAvis(@PathVariable("id")Long id){
        avisServ.supprimerAvis(id);
    }
    @GetMapping("/all")
    public List<Avis> getAllAvis(){
        return avisServ.getAllAvis();
    }

}
