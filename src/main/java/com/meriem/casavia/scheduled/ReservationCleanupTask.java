package com.meriem.casavia.scheduled;

import com.meriem.casavia.entities.EtatReservation;
import com.meriem.casavia.entities.Reservation;
import com.meriem.casavia.repositories.ReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ReservationCleanupTask {

    private final ReservationRepository reservationRepository;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public ReservationCleanupTask(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void updateReservationStates() {
        List<Reservation> reservations = reservationRepository.findAll();
        Date currentDate = new Date();
        for (Reservation reservation : reservations) {
            try {
                Date checkOutDate = dateFormat.parse(reservation.getDateCheckOut());
                if (checkOutDate.before(currentDate) && !reservation.getEtat().equals(EtatReservation.COMPLETED)) {
                    reservation.setEtat(EtatReservation.COMPLETED);
                    reservationRepository.save(reservation);
                }
            } catch (Exception e) {
                System.out.println("Error parsing date: " + e.getMessage());
            }
        }
    }
}
