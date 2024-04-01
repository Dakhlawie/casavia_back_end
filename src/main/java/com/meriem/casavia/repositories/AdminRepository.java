package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    Admin findAdminByEmail(String email);
    boolean existsByEmail(String email);
}
