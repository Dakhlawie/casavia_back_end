package com.meriem.casavia.services;

import com.meriem.casavia.entities.Hebergement;
import com.meriem.casavia.entities.Like;
import com.meriem.casavia.entities.User;
import com.meriem.casavia.repositories.HebergementRepository;
import com.meriem.casavia.repositories.LikeRepository;
import com.meriem.casavia.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LikeServiceImpl implements LikeService{
    @Autowired
    LikeRepository LikeRep;
    @Autowired
    HebergementRepository HebergementRep;
    @Autowired
    UserRepository userRep;

    @Override
    public Like addLike(long user, long hebergement) {
        Like like = new Like();
        like.setUser(userRep.findById(user).get());
        like.setHebergement(HebergementRep.findById(hebergement).get());
        like.setLikedAt(LocalDateTime.now());
      return   LikeRep.save(like);
    }

    @Override
    public void removeLike(long id) {
        LikeRep.deleteById(id);
    }
}
