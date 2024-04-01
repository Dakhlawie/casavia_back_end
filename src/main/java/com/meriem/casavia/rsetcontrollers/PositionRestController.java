package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Hebergement;
import com.meriem.casavia.entities.Position;
import com.meriem.casavia.repositories.HebergementRepository;
import com.meriem.casavia.services.HebergementService;
import com.meriem.casavia.services.PersonService;
import com.meriem.casavia.services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/position")
@CrossOrigin
public class PositionRestController {
    @Autowired
    PositionService positionserv;
    @Autowired
    HebergementRepository hebergementRep;
    @PostMapping("/save")
    public Position createPosition(@RequestParam Long hebergement, @RequestBody Position position) {
        System.out.println(hebergement);


        position.setHebergement(hebergementRep.findById(hebergement).get());


        return positionserv.ajouterPosition(position);

    }
}
