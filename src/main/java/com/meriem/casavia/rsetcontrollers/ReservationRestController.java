package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.*;
import com.meriem.casavia.repositories.HebergementRepository;
import com.meriem.casavia.repositories.PersonRepository;
import com.meriem.casavia.repositories.ReservationRepository;
import com.meriem.casavia.repositories.UserRepository;
import com.meriem.casavia.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RequestMapping("/reservation")
@CrossOrigin
@RestController
public class ReservationRestController {
    @Autowired
    ReservationService Reservationserv;
    @Autowired
    ReservationRepository ReservationRep;
    @Autowired
    HebergementRepository hebergementRep;
    @Autowired
    UserRepository userRep;
    @Autowired
    PersonRepository personRep;
    @PostMapping("/save")
    public Reservation ajouterReservation(@RequestBody Reservation reservation,@RequestParam long user,@RequestParam long hebergement){
        reservation.setEtat(EtatReservation.PENDING);
        reservation.setHebergement(hebergementRep.findById(hebergement).get());
        reservation.setUser(userRep.findById(user).get());
        return Reservationserv.ajouterReservation(reservation);
    }
    //partner delete
    @DeleteMapping("/delete/{id}")
    public void annulerReservation(@PathVariable("id") Long id){
        Reservationserv.annulerReservation(id);
    }
    @GetMapping("/getByUser")
    public List<Reservation> getByUser(@RequestBody User user){
        return this.ReservationRep.findByUser(user);
    }
    @GetMapping("/getByHebergement")
    public List<Reservation> getByHebergement(@RequestBody Hebergement hebergement){
        return this.ReservationRep.findByHebergement(hebergement);
    }
    @GetMapping("/get/{etat}")
    public List<Reservation> getByEtat(@PathVariable String etat){
        return this.ReservationRep.findByEtat(etat);
    }
    @PutMapping("/modifEtat/{id}")
    public Reservation modifierEtatReservation(@PathVariable("id") long id,EtatReservation etat ){
        Reservation r=this.ReservationRep.findById(id).get();
        r.setEtat(etat);
        return this.ReservationRep.save(r);
    }
    @PostMapping("/sendEmail")
    public void sendEmail(@RequestBody Reservation reservation) {
        Reservationserv.sendEmail(reservation);
    }
    @PostMapping("/confirmation")
    public void sendConfirmation(@RequestBody Reservation reservation) {
        Reservationserv.sendConfirmation(reservation);
    }
    @PostMapping("/annulation")
    public void sendAnnulation(@RequestBody Reservation reservation) {
        Reservationserv.sendAnnulation(reservation);
    }
    @GetMapping("/getById/{id}")
    public Reservation getById(@PathVariable("id") long id){
        return ReservationRep.findById(id).get();
    }
    @GetMapping("/monthly-count")
    public List<Object[]> getMonthlyReservationCounts() {
        return ReservationRep.countReservationsByMonth();
    }
    @GetMapping("/hebergement/{id}")
    public List<Object[]> getMonthlyReservationCountsByHebergement(@PathVariable("id") long id) {

        Hebergement h=hebergementRep.findById(id).get();
        return ReservationRep.countReservationsByMonthAndHebergement(h);
    }
    @GetMapping("/person/reservations/{id}")
    public List<Object[]> getMonthlyReservationCountsByPerson(@PathVariable("id") long id) {

        Person p=personRep.findById(id).get();
        return ReservationRep.countMonthlyReservationsByPerson(p.getPerson_id());
    }

    @GetMapping("/number")
    public long getTotalReservation() {
        return ReservationRep.countBy();
    }
    @GetMapping("/person/years/{id}")
    public List<Object[]> getYearlyReservationByPerson(@PathVariable("id") long id){
        return this.ReservationRep.countYearlyReservationsByPerson(id);
    }
    @GetMapping("/years")
    public List<Object[]> getYearlyReservation(){
        return this.ReservationRep.countReservationsByYearForLastFiveYears();
    }
    @GetMapping("/person/pending/{id}")
    public List<Reservation> getPendingReservationByPerson(@PathVariable("id") long id){
        return ReservationRep.findPendingReservationsByPerson(id);
    }
    @GetMapping("/person/confirmed/{id}")
    public List<Reservation> getConfirmedReservationByPerson(@PathVariable("id") long id){
        return ReservationRep.findConfirmedReservationsByPerson(id);
    }
    @GetMapping("/person/completed/{id}")
    public List<Reservation> getCompletedReservationByPerson(@PathVariable("id") long id){
        return ReservationRep.findCompletedReservationsByPerson(id);
    }
    @GetMapping("/person/cancelled/{id}")
    public List<Reservation> getCancelledReservationByPerson(@PathVariable("id") long id){
        return ReservationRep.findCancelledReservationsByPerson(id);
    }
    @GetMapping("/user/pendingOrConfirmed/{id}")
    public List<Reservation> getPendingOrConfirmedReservationByUser(@PathVariable("id") long id ){
        return ReservationRep.findPendingOrConfirmedReservationsByUser(id);
    }
    @GetMapping("/user/completed/{id}")
    public List<Reservation> getCompletedReservationByUser(@PathVariable("id") long id){
        return ReservationRep.findCompletedReservationsByUser(id);
    }
}
