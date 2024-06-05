package com.meriem.casavia.repositories;


import com.meriem.casavia.entities.Hebergement;
import com.meriem.casavia.entities.Question;
import com.meriem.casavia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    @Query("SELECT q FROM Question q LEFT JOIN q.reponse r WHERE q.hebergement.person.person_id = :personId AND (r IS NULL OR r.content = '') ORDER BY q.dateAsked DESC")
    List<Question> findQuestionsByPersonIdAndEmptyReponseOrderByDateAskedDesc(@Param("personId") Long personId);
    List<Question> findByHebergementAndUser(Hebergement hebergement, User user);
    @Query("SELECT q.user FROM Question q WHERE q.id = :questionId")
    User findUserByQuestionId(@Param("questionId") Long questionId);
}
