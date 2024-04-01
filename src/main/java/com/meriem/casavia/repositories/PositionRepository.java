package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.Person;
import com.meriem.casavia.entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository  extends JpaRepository<Position,Long> {
}
