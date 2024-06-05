package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Avis;
import com.meriem.casavia.entities.Hebergement;
import com.meriem.casavia.entities.User;
import com.meriem.casavia.repositories.AvisRepository;
import com.meriem.casavia.repositories.HebergementRepository;
import com.meriem.casavia.repositories.UserRepository;
import com.meriem.casavia.services.AvisService;
import com.meriem.casavia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/avis")
@CrossOrigin
public class AvisRestController {
    @Autowired
    AvisService avisServ;
    @Autowired
    UserRepository userRep;
    @Autowired
    HebergementRepository hebergementRepository;
    @Autowired
    AvisRepository avisRep;
    @PostMapping("/save")
    public Avis ajouterAvis(@RequestBody Avis avis,@RequestParam long id,@RequestParam long user){
        Hebergement h=hebergementRepository.findById(id).get();
        User u=userRep.findById(user).get();
        avis.setUser(u);
        avis.setHebergement(h);
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
    @GetMapping("/byhebergement/{id}")
    public List<Avis> getAvisByHebergement(@PathVariable("id") long id){
        Hebergement h=hebergementRepository.findById(id).get();


        return avisRep.findByHebergement(h, Sort.by(Sort.Direction.DESC, "date"));
    }

    //@GetMapping("/hebergement/{id}")
    //public Map<String, Double> getReviewsByHebergement(@PathVariable("id") long id) {
        ///Hebergement h = hebergementRepository.findById(id).get();


       // return avisRep.findAveragesByHebergement(h);
  //  }
    @GetMapping("/hebergement/{id}")
    public Map<String, Double> getReviewsByHebergement(@PathVariable("id") long id) {
        Hebergement hebergement = hebergementRepository.findById(id).orElse(null);
        if (hebergement == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hebergement not found");
        }

        if ("Hotel".equals(hebergement.getCategorie().getType())) {
            return avisRep.findAveragesByHebergement(hebergement);
        } else {
            return avisRep.findAveragesByOtherHebergement(hebergement);
        }
    }

    @GetMapping("/avispositive/{id}")
    public double calculatePositiveRate(@PathVariable long id){
        Hebergement h=hebergementRepository.findById(id).get();
        long totalReviews = avisRep.count();
        long positiveReviews = avisRep.countPositiveReviews(h);
        return (double) positiveReviews / totalReviews * 100;
    }
    @GetMapping("/avisnegative/{id}")
    public double calculateNegatifRate(@PathVariable long id){
        Hebergement h=hebergementRepository.findById(id).get();
        long totalReviews = avisRep.count();
        long positiveReviews = avisRep.countNegativeReviews(h);
        return (double) positiveReviews / totalReviews * 100;
    }
    @GetMapping("/positiveAndNegative/{id}")
    public List<Object[]> findMonthlyPositiveAndNegativeCounts(@PathVariable("id") long id ){
        Hebergement h=hebergementRepository.findById(id).get();
        return avisRep.findMonthlyPositiveAndNegativeCounts(h);
    }
    @GetMapping("/positive/{id}")
    public List<Object[]> findMonthlyPositiveCounts(@PathVariable("id") long id ){
        Hebergement h=hebergementRepository.findById(id).get();
        return avisRep.findMonthlyPositiveCounts(h);
    }
    @GetMapping("/negative/{id}")
    public List<Object[]> findMonthlyNegativeCounts(@PathVariable("id") long id ){
        Hebergement h=hebergementRepository.findById(id).get();
        return avisRep.findMonthlyNegativeCounts(h);
    }

    @GetMapping("/top")
    public List<Avis> findTopAvisHebergement(@RequestParam long id ){
        Hebergement h=hebergementRepository.findById(id).get();
        Pageable pageable = PageRequest.of(0, 4);
        if (h.getCategorie().getType().equals("Hotel")) {
            return avisRep.findTop4AvisForHotel(h, pageable);
        } else {
            return avisRep.findTop4AvisForOtherHebergement(h, pageable);
        }

    }
    @GetMapping("/avis/user/{id}")
    public List<Avis> getAvisByUser(@PathVariable("id") long id){

        User u =userRep.findById(id).get();
        return this.avisRep.findAvisByUser(u);
    }

}
