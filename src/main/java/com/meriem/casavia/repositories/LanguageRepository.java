package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageRepository extends JpaRepository<language,Long> {

}
