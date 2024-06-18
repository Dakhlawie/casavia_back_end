package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayementRepository  extends JpaRepository<Payment,Long> {
}
