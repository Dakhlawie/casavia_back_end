package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Hebergement;
import com.meriem.casavia.entities.Like;
import com.meriem.casavia.entities.User;
import com.meriem.casavia.repositories.HebergementRepository;
import com.meriem.casavia.repositories.LikeRepository;
import com.meriem.casavia.repositories.UserRepository;
import com.meriem.casavia.services.LikeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/like")
@CrossOrigin
public class LikeRestController {
    @Autowired
    LikeService likeserv;
    @Autowired
    UserRepository userRep;
    @Autowired
    HebergementRepository hebergementRep;
    @Autowired
    LikeRepository likeRep;
    @PostMapping("/save")

    public Like addLike(@RequestParam Long userId, @RequestParam Long hebergementId){

        return  likeserv.addLike(userId,hebergementId);

    }
    @DeleteMapping("/delete/{id}")
    public void deleteLike(@PathVariable("id") long id ){
        likeserv.removeLike(id);
    }
    @GetMapping("/getByUser/{id}")
    public List<Like> getByUser(@PathVariable("id") long id ){
        User u=userRep.findById(id).get();
        return this.likeRep.findByUser(u);
    }
    @GetMapping("/findByUserHebergement")
    public boolean findByUserAndHebergement(@RequestParam long user,@RequestParam  long hebergement){
        User u=userRep.findById(user).get();
        Hebergement h=hebergementRep.findById(hebergement).get();
        Like like=likeRep.findByUserAndHebergement(u,h);
        if(like!=null){return true ;}
        else{return false;}

    }
    @DeleteMapping("/deleteByUserHebergement")
    @Transactional
    public void deleteByUserAndHebergement(@RequestParam long user,@RequestParam  long hebergement){
        User u=userRep.findById(user).get();
        Hebergement h=hebergementRep.findById(hebergement).get();
        likeRep.deleteByUserAndHebergement(u,h);
    }
}
