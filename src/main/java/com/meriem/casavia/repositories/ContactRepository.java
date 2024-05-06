package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact,Long> {
}
