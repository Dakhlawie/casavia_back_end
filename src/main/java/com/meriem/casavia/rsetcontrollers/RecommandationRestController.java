package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Hebergement;
import com.meriem.casavia.entities.Recommandation;
import com.meriem.casavia.entities.User;
import com.meriem.casavia.repositories.HebergementRepository;
import com.meriem.casavia.repositories.RecommandationRepository;
import com.meriem.casavia.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommandation")
@CrossOrigin
public class RecommandationRestController {
    @Autowired
    UserRepository userRep;
    @Autowired
    HebergementRepository hebergementRep;
    @Autowired
    RecommandationRepository recommandationRep;
    @PostMapping("/save")
    public Recommandation ajouterRecommandation(@RequestBody Recommandation rec,@RequestParam long user,@RequestParam long hebergement){
        User u=userRep.findById(user).get();
        rec.setUser(u);
        Hebergement h=hebergementRep.findById(hebergement).get();
        rec.setHebergement(h);

        return recommandationRep.save(rec);
    }
    @GetMapping("/getByUser")
    public List<Recommandation> getByUser(@RequestParam long user){
        User u=userRep.findById(user).get();
        return  recommandationRep.findByUser(u);
    }
}
