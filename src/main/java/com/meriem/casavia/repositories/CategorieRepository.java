package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.Categorie;
import com.meriem.casavia.entities.Hebergement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie,Long> {

}
