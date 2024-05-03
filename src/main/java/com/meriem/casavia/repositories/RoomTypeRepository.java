package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.CategorieEquipement;
import com.meriem.casavia.entities.roomType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomTypeRepository extends JpaRepository<roomType,Long> {
}
