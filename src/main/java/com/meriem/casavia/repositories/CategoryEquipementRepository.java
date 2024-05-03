package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.CategorieEquipement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryEquipementRepository extends JpaRepository<CategorieEquipement,Long> {
CategorieEquipement getCategorieEquipementByNom(String nom);
}
