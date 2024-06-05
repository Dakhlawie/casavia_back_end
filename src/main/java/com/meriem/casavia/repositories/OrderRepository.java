package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.OffreHebergement;
import com.meriem.casavia.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Order findByPaymentId(String id);
}
