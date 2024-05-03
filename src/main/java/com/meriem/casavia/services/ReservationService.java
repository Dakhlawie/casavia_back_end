package com.meriem.casavia.services;

import com.meriem.casavia.entities.Hebergement;
import com.meriem.casavia.entities.Reservation;
import com.meriem.casavia.entities.User;

public interface ReservationService {
    Reservation ajouterReservation(Reservation r);
    void annulerReservation(Long id);
    void sendEmail(Reservation reservation);
    void sendConfirmation(Reservation reservation);
    void sendAnnulation(Reservation reservation);
}
