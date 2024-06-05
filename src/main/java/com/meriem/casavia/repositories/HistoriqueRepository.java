package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.Historique;
import com.meriem.casavia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HistoriqueRepository extends JpaRepository<Historique,Long> {

    @Query("SELECT h FROM Historique h WHERE h.user = :user AND h.etat = 'active'")
    List<Historique> findByUserAndEtatActive(@Param("user") User user);
    void deleteAllByUser(User user);
}
