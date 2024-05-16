package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.Recommandation;
import com.meriem.casavia.entities.User;
import org.hibernate.query.sqm.spi.JdbcParameterBySqmParameterAccess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommandationRepository extends JpaRepository<Recommandation,Long> {
    List<Recommandation> findByUser(User user);
}
