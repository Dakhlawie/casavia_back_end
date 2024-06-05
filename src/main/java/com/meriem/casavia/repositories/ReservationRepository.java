package com.meriem.casavia.repositories;


import com.meriem.casavia.entities.Hebergement;
import com.meriem.casavia.entities.Reservation;
import com.meriem.casavia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository  extends JpaRepository<Reservation,Long> {
    List<Reservation> findByUser(User user);
   /* List<Reservation> findByEtatAndHebergementId(String etat, Long hebergementId);*/
    List<Reservation> findByHebergement(Hebergement hebergement);
    List<Reservation> findByEtat(String etat);
    @Query(value = "SELECT EXTRACT(MONTH FROM STR_TO_DATE(r.date_check_in, '%d/%m/%Y')) AS month, COUNT(*) AS count " +
            "FROM Reservation r " +

            "WHERE EXTRACT(YEAR FROM STR_TO_DATE(r.date_check_in, '%d/%m/%Y')) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "AND r.etat = 'COMPLETED' " +
            "GROUP BY EXTRACT(MONTH FROM STR_TO_DATE(r.date_check_in, '%d/%m/%Y')) " +
            "ORDER BY month", nativeQuery = true)
    List<Object[]> countReservationsByMonth();
    long countBy();
    @Query("SELECT FUNCTION('MONTH', FUNCTION('STR_TO_DATE', r.dateCheckIn, '%d/%m/%Y')) AS month, COUNT(r) AS count " +
            "FROM Reservation r " +
            "WHERE FUNCTION('YEAR', FUNCTION('STR_TO_DATE', r.dateCheckIn, '%d/%m/%Y')) = FUNCTION('YEAR', CURRENT_DATE) " +
            "AND r.hebergement = :hebergement " +
            "AND r.etat = 'COMPLETED' " +
            "GROUP BY FUNCTION('MONTH', FUNCTION('STR_TO_DATE', r.dateCheckIn, '%d/%m/%Y')) " +
            "ORDER BY FUNCTION('MONTH', FUNCTION('STR_TO_DATE', r.dateCheckIn, '%d/%m/%Y'))")
    List<Object[]> countReservationsByMonthAndHebergement(@Param("hebergement") Hebergement hebergement);

 @Query("SELECT FUNCTION('MONTH', FUNCTION('STR_TO_DATE', r.dateCheckIn, '%d/%m/%Y')) as month, COUNT(r) as totalReservations " +
         "FROM Reservation r JOIN r.hebergement h JOIN h.person p " +
         "WHERE p.person_id = :personId AND FUNCTION('YEAR',  FUNCTION('STR_TO_DATE', r.dateCheckIn, '%d/%m/%Y')) = FUNCTION('YEAR', CURRENT_DATE()) " +
         "AND r.etat = 'COMPLETED' " +
         "GROUP BY FUNCTION('MONTH', FUNCTION('STR_TO_DATE', r.dateCheckIn, '%d/%m/%Y')) " +
         "ORDER BY FUNCTION('MONTH', FUNCTION('STR_TO_DATE', r.dateCheckIn, '%d/%m/%Y'))")
 List<Object[]> countMonthlyReservationsByPerson(@Param("personId") Long personId);
 @Query("SELECT FUNCTION('YEAR', FUNCTION('STR_TO_DATE', r.dateCheckIn, '%d/%m/%Y')) as year, COUNT(r) as totalReservations " +
         "FROM Reservation r JOIN r.hebergement h JOIN h.person p " +
         "WHERE p.person_id = :personId AND " +
         "r.etat = 'COMPLETED' AND " +
         "FUNCTION('YEAR', FUNCTION('STR_TO_DATE', r.dateCheckIn, '%d/%m/%Y')) BETWEEN FUNCTION('YEAR', CURRENT_DATE()) - 4 AND FUNCTION('YEAR', CURRENT_DATE()) " +
         "GROUP BY FUNCTION('YEAR', FUNCTION('STR_TO_DATE', r.dateCheckIn, '%d/%m/%Y')) " +
         "ORDER BY FUNCTION('YEAR', FUNCTION('STR_TO_DATE', r.dateCheckIn, '%d/%m/%Y'))")
 List<Object[]> countYearlyReservationsByPerson(@Param("personId") Long personId);
 @Query(value = "SELECT EXTRACT(YEAR FROM STR_TO_DATE(r.date_check_in, '%d/%m/%Y')) AS year, COUNT(*) AS count " +
         "FROM Reservation r " +
         "WHERE EXTRACT(YEAR FROM STR_TO_DATE(r.date_check_in, '%d/%m/%Y')) >= EXTRACT(YEAR FROM CURRENT_DATE) - 4 " +
         "AND r.etat = 'COMPLETED' " +
         "GROUP BY EXTRACT(YEAR FROM STR_TO_DATE(r.date_check_in, '%d/%m/%Y')) " +
         "ORDER BY year", nativeQuery = true)
 List<Object[]> countReservationsByYearForLastFiveYears();
 @Query("SELECT r FROM Reservation r JOIN r.hebergement h JOIN h.person p WHERE p.person_id = :personId AND r.etat = 'PENDING' ORDER BY FUNCTION('STR_TO_DATE', r.dateCheckIn, '%d/%m/%Y') DESC")
 List<Reservation> findPendingReservationsByPerson(@Param("personId") Long personId);

 @Query("SELECT r FROM Reservation r JOIN r.hebergement h JOIN h.person p WHERE p.person_id = :personId AND r.etat = 'CONFIRMED' ORDER BY FUNCTION('STR_TO_DATE', r.dateCheckIn, '%d/%m/%Y') DESC")
 List<Reservation> findConfirmedReservationsByPerson(@Param("personId") Long personId);

 @Query("SELECT r FROM Reservation r JOIN r.hebergement h JOIN h.person p WHERE p.person_id = :personId AND r.etat = 'CANCELLED' ORDER BY FUNCTION('STR_TO_DATE', r.dateCheckIn, '%d/%m/%Y') DESC")
 List<Reservation> findCancelledReservationsByPerson(@Param("personId") Long personId);

 @Query("SELECT r FROM Reservation r JOIN r.hebergement h JOIN h.person p WHERE p.person_id = :personId AND r.etat = 'COMPLETED' ORDER BY FUNCTION('STR_TO_DATE', r.dateCheckIn, '%d/%m/%Y') DESC")
 List<Reservation> findCompletedReservationsByPerson(@Param("personId") Long personId);
 @Query("SELECT r FROM Reservation r JOIN r.user u WHERE u.user_id = :userId AND r.etat = 'COMPLETED' ORDER BY FUNCTION('STR_TO_DATE', r.dateCheckIn, '%d/%m/%Y') DESC")
 List<Reservation> findCompletedReservationsByUser(@Param("userId") Long userId);

 @Query("SELECT r FROM Reservation r JOIN r.user u WHERE u.user_id = :userId AND (r.etat = 'PENDING' OR r.etat = 'CONFIRMED') ORDER BY FUNCTION('STR_TO_DATE', r.dateCheckIn, '%d/%m/%Y') DESC")
 List<Reservation> findPendingOrConfirmedReservationsByUser(@Param("userId") Long userId);


}
