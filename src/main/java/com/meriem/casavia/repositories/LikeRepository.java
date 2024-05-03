package com.meriem.casavia.repositories;

import com.meriem.casavia.entities.Hebergement;
import com.meriem.casavia.entities.Like;
import com.meriem.casavia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like,Long> {
List<Like> findByUser(User user);
Like findByUserAndHebergement(User user, Hebergement h);
    void deleteByUserAndHebergement(User user, Hebergement hebergement);

}
