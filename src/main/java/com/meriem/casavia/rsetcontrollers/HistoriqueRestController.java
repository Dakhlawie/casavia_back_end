package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Historique;
import com.meriem.casavia.entities.User;
import com.meriem.casavia.repositories.HistoriqueRepository;
import com.meriem.casavia.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/historique")
@RestController
@CrossOrigin
public class HistoriqueRestController {
    @Autowired
    HistoriqueRepository historiqueRep;
    @Autowired
    UserRepository userRep;
    @PostMapping("/save")
    public Historique ajouterHistorique(@RequestParam long user,@RequestBody Historique historique){

        historique.setUser(userRep.findById(user).get());
        return historiqueRep.save(historique);
    }
    @GetMapping("/getByUser")
    public List<Historique> getHistoriqueByUser(@RequestParam long user){
        User u=userRep.findById(user).get();
        return historiqueRep.getByUser(u);

    }
    @Transactional
    @DeleteMapping("/deleteAll")
    public void deleteAll(@RequestParam long user){
        User u=userRep.findById(user).get();
        historiqueRep.deleteAllByUser(u);

    }
}
