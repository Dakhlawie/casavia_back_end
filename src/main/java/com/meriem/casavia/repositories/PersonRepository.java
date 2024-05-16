package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.Person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person,Long> {
    Person findPersonByEmail(String email);
    boolean existsByEmail(String email);
    List<Person> findByRole(String role);
    long countBy();
    @Query("SELECT p FROM Person p JOIN p.hebergements h WHERE h.hebergement_id = :hebergementId")
    Person findByHebergementId(Long hebergementId);
    @Query("SELECT p.person_id as personId, p.nom as personName, COUNT(h) as totalAccommodations, " +
            "(SELECT COUNT(l) FROM Like l JOIN l.hebergement he WHERE he.person = p) as totalLikes, " +
            "(SELECT COUNT(r) FROM Avis r JOIN r.hebergement he WHERE he.person = p) as totalReviews, " +
            "(SELECT COUNT(b) FROM Reservation b JOIN b.hebergement he WHERE he.person = p) as totalBookings " +
            "FROM Person p LEFT JOIN p.hebergements h " +
            "WHERE p.person_id = :personId " +
            "GROUP BY p.person_id")
    Object getPersonStatistics(Long personId);
}
