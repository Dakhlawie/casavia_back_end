package com.meriem.casavia.services;

import com.meriem.casavia.entities.Hebergement;
import com.meriem.casavia.entities.Reservation;
import com.meriem.casavia.entities.User;

import java.util.List;

public interface ReservationService {
    Reservation ajouterReservation(Reservation r);
    void annulerReservation(Long id);
    void sendEmail(Reservation reservation);
    void sendConfirmation(Reservation reservation);
    void sendAnnulation(Reservation reservation);
    void confirmerReservation(Long id);
    String sendConfirmationEmail(Reservation reservation);

}
