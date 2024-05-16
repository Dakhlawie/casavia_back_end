package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.Historique;
import com.meriem.casavia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoriqueRepository extends JpaRepository<Historique,Long> {

    List<Historique> getByUserAndEtat(User user,String etat);
    void deleteAllByUser(User user);
}
