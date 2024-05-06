package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Chambre;
import com.meriem.casavia.entities.Dates;
import com.meriem.casavia.entities.Hebergement;
import com.meriem.casavia.repositories.ChambreRepository;
import com.meriem.casavia.repositories.DatesRepository;
import com.meriem.casavia.repositories.HebergementRepository;
import com.meriem.casavia.services.DatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dates")
@CrossOrigin
public class DatesRestController {
    @Autowired
    DatesService Datesserv;
    @Autowired
    DatesRepository datesRep;
    @Autowired
    ChambreRepository chambreRep;
    @Autowired
    HebergementRepository hebergementRep;
    @PostMapping("/saveToRoom")
    public Dates ajouterDatesToRoom(@RequestBody Dates d,@RequestParam long chambre){
        Chambre c=chambreRep.findById(chambre).get();
        d.setChambre(c);

        return this.Datesserv.ajouterDates(d);
    }
    @PostMapping("/saveToHebergement")
    public Dates ajouterDatesToHebrgement(@RequestBody Dates d,@RequestParam long hebergement){
        Hebergement h=hebergementRep.findById(hebergement).get();
        d.setHebergement(h);
        return this.Datesserv.ajouterDates(d);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDates(@PathVariable("id") long id ){
        this.Datesserv.deleteDates(id);
    }
    @GetMapping("/getByHebergement")
    public List<Dates> getAllDatesByHebergement(@RequestParam long hebergement){
        Hebergement h=hebergementRep.findById(hebergement).get();
        return this.datesRep.findByHebergement(h);
    }
    @GetMapping("/getByChambre")
    public List<Dates> getAllDatesByChambre(@RequestParam long chambre){
        Chambre h=chambreRep.findById(chambre).get();
        return this.datesRep.findByChambre(h);
    }

    @PostMapping("/dispo/{id}")
    public boolean CheckAvailibility(@PathVariable("id") long chambreId,
                                     @RequestParam("checkIn") String checkIn,
                                     @RequestParam("checkOut") String checkOut){
        return this.Datesserv.isRoomAvailable(chambreId,checkIn,checkOut);
    }
    @PostMapping("/hebergement/dispo/{id}")
    public boolean CheckAvailibilityHebergement(@PathVariable("id") long hebergementId,
                                     @RequestParam("checkIn") String checkIn,
                                     @RequestParam("checkOut") String checkOut){
        return this.Datesserv.isHebergementAvailable(hebergementId,checkIn,checkOut);
    }


}
