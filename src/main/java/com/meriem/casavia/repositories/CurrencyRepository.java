package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency,Long> {
}
