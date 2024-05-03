package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Chambre;
import com.meriem.casavia.entities.Dates;
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
    @PostMapping("/sava")
    public Dates ajouterDates(@RequestBody Dates d){
        return this.Datesserv.ajouterDates(d);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteDates(@PathVariable("id") long id ){
        this.Datesserv.deleteDates(id);
    }
    @GetMapping("/list")
    public List<Dates> getAllDates(){
        return this.Datesserv.getAllDates();
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
