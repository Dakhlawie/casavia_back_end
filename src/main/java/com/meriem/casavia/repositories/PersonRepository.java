package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.Person;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person,Long> {
    Person findPersonByEmail(String email);
    boolean existsByEmail(String email);
    List<Person> findByRole(String role);
}
