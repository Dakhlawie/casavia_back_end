package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.*;
import com.meriem.casavia.repositories.HebergementRepository;
import com.meriem.casavia.repositories.PersonRepository;
import com.meriem.casavia.repositories.ReservationRepository;
import com.meriem.casavia.repositories.UserRepository;
import com.meriem.casavia.services.ReservationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@RequestMapping("/reservation")
@CrossOrigin
@RestController
public class ReservationRestController {
    @Autowired
    ReservationService Reservationserv;
    @Value("${spring.mail.username}")

    private String from;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    ReservationRepository ReservationRep;
    @Autowired
    HebergementRepository hebergementRep;
    @Autowired
    UserRepository userRep;
    @Autowired
    PersonRepository personRep;
    @PostMapping("/save")
    public Reservation ajouterReservation(@RequestBody Reservation reservation, @RequestParam Long user_id, @RequestParam Long hebergement_id) {
        System.out.println(user_id);
        System.out.println(hebergement_id);
        System.out.println(reservation);
        reservation.setEtat(EtatReservation.PENDING);

        System.out.println("************************************bilel");
        System.out.println(hebergement_id);
        reservation.setHebergement(hebergementRep.findById(hebergement_id).orElseThrow(() -> new NoSuchElementException("Hebergement not found")));
        System.out.println("bilel");
        reservation.setUser(userRep.findById(user_id).get());

        System.out.println("hey!");
        String numeroReservation = generateRandomNumber(6);
        System.out.println("hi");
        reservation.setNumeroReservation(numeroReservation);
        System.out.println("RESERVATION*********************:");
        System.out.println(reservation);
        return ReservationRep.save(reservation);
    }
    private String generateRandomNumber(int n) {
        Random random = new Random();
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < n; i++) {
            number.append(random.nextInt(10));
        }
        return number.toString();
    }
    //partner delete
    @PutMapping("/annuler/{id}")
    public void annulerReservation(@PathVariable("id") Long id){
        Reservationserv.annulerReservation(id);
    }
    @PutMapping("/confirmer/{id}")
    public void confirmerReservation(@PathVariable("id") Long id){
        Reservationserv.confirmerReservation(id);
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
        return ReservationRep.findPendingOrUpdatedReservationsByPerson(id);
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
        return ReservationRep.findPendingOrConfirmedOrUpdatedReservationsByUser(id);
    }
    @GetMapping("/user/completed/{id}")
    public List<Reservation> getCompletedReservationByUser(@PathVariable("id") long id){
        return ReservationRep.findCompletedReservationsByUser(id);
    }
    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable("id") long id){
        return ReservationRep.findById(id).get();
    }
    @PutMapping("/updated/{id}")
    public Reservation updateReservation(@PathVariable long id, @RequestBody Reservation updatedReservation) {
        Reservation rv= ReservationRep.findById(id).get();

        rv.setDateCheckIn(updatedReservation.getDateCheckIn());
        rv.setDateCheckOut(updatedReservation.getDateCheckOut());
        rv.setNbRooms(updatedReservation.getNbRooms());
        rv.setPrix(updatedReservation.getPrix());
        rv.setCurrency(updatedReservation.getCurrency());
        rv.setRooms(updatedReservation.getRooms());
        rv.setEtat(EtatReservation.UPDATED);
        Reservation savedReservation = ReservationRep.save(rv);
        return savedReservation;}
    @PostMapping("/confirm")
    public void confirmReservation(@RequestBody Long reservationId) {
        Reservation reservation = ReservationRep.findById(reservationId).get();
        Reservationserv.sendConfirmationEmail(reservation);
    }


}
