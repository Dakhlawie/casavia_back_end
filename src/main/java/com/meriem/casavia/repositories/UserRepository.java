package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends  JpaRepository<User,Long>{
    User findUserByEmail(String email);
    boolean existsByEmail(String email);
}
