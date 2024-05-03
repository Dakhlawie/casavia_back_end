package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.Code;
import com.meriem.casavia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeRepository extends JpaRepository<Code, Long> {
    Code findByCode(String code);
    Code findByUser(User user);

}
