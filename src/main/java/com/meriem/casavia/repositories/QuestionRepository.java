package com.meriem.casavia.repositories;


import com.meriem.casavia.entities.Hebergement;
import com.meriem.casavia.entities.Question;
import com.meriem.casavia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    @Query("SELECT q FROM Question q WHERE q.hebergement.person.id = :personId ORDER BY q.dateAsked DESC")
    List<Question> findQuestionsByPersonIdOrderByDateAskedDesc(@Param("personId") Long personId);
    List<Question> findByHebergementAndUser(Hebergement hebergement, User user);
}
