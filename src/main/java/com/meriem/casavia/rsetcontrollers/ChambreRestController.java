package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Chambre;
import com.meriem.casavia.entities.Equipement;
import com.meriem.casavia.entities.Hebergement;
import com.meriem.casavia.repositories.ChambreRepository;
import com.meriem.casavia.repositories.HebergementRepository;
import com.meriem.casavia.services.ChambreService;
import com.meriem.casavia.services.HebergementService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chambre")
@CrossOrigin
public class ChambreRestController {
    @Autowired
    ChambreService chambreserv;
    @Autowired
    HebergementRepository hebergementRep;
    @Autowired
    ChambreRepository chambreRep;
    @PostMapping("/save")
    public Chambre ajouterChambre(@RequestParam Long hebergement ,@RequestBody Chambre chambre){
        chambre.setHebergement(hebergementRep.findById(hebergement).get());
        return chambreserv.ajouterChambre(chambre);
    }
    @PutMapping("/update/{id}")
    public Chambre modifierChambre(@PathVariable("id") Long id ,@RequestBody Chambre chambre){
        return chambreserv.modifierChambre(id,chambre);
    }
    @GetMapping("/all")
    public List<Chambre> getAllChambre(){return chambreserv.getAllChambre();}
@GetMapping("/getByHebergement")
public List<Chambre> getByHebergement(@RequestParam long hebergement){
    Hebergement h=hebergementRep.findById(hebergement).get();
    return chambreRep.findByHebergement(h);
   }
    @GetMapping("/equipements/{id}")
    public List<Equipement> getEquipements(@PathVariable("id") long id){
        Chambre h=chambreRep.findById(id).get();
        List<Equipement> equipements=h.getEquipements();
        return equipements;
    }
    @GetMapping("/hasEquipements/{id}")
    public boolean hasEquipements(@PathVariable("id")long id){
        Chambre h=chambreRep.findById(id).get();

        if(h.getEquipements().isEmpty()){
            return false;
        }else{return true;}
    }
    @GetMapping("/{id}")
    public Chambre getById(@PathVariable("id")long id){
        return this.chambreRep.findById(id).get();
    }

}
