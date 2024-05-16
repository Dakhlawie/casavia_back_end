package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Avis;
import com.meriem.casavia.entities.Hebergement;
import com.meriem.casavia.repositories.AvisRepository;
import com.meriem.casavia.repositories.HebergementRepository;
import com.meriem.casavia.services.AvisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/avis")
@CrossOrigin
public class AvisRestController {
    @Autowired
    AvisService avisServ;
    @Autowired
    HebergementRepository hebergementRepository;
    @Autowired
    AvisRepository avisRep;
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
    @GetMapping("/byhebergement/{id}")
    public List<Avis> getAvisByHebergement(@PathVariable("id") long id){
        Hebergement h=hebergementRepository.findById(id).get();


        return avisRep.findByHebergement(h, Sort.by(Sort.Direction.DESC, "date"));
    }
    @GetMapping("/hebergement/{id}")
    public Map<String, Double> getReviewsByHebergement(@PathVariable("id") long id){
        Hebergement h=hebergementRepository.findById(id).get();


        return avisRep.findAveragesByHebergement(h);
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

}
