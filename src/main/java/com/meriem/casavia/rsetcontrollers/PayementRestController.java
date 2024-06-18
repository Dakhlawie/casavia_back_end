package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Payment;
import com.meriem.casavia.entities.Reservation;
import com.meriem.casavia.repositories.PayementRepository;
import com.meriem.casavia.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payement")
@CrossOrigin
public class PayementRestController {
    @Autowired
    PayementRepository payementRepository;
    @Autowired
    ReservationRepository reservationRep;
    @PostMapping("/add")
    public Payment addPayment(@RequestBody Payment payement,@RequestParam long id) {
      payement.setReservation(reservationRep.findById(id).get());

        return payementRepository.save(payement);
    }
}
