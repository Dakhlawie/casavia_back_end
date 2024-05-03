package com.meriem.casavia.repositories;


import com.meriem.casavia.entities.Hebergement;
import com.meriem.casavia.entities.Reservation;
import com.meriem.casavia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository  extends JpaRepository<Reservation,Long> {
    List<Reservation> findByUser(User user);
   /* List<Reservation> findByEtatAndHebergementId(String etat, Long hebergementId);*/
    List<Reservation> findByHebergement(Hebergement hebergement);
    List<Reservation> findByEtat(String etat);

}
