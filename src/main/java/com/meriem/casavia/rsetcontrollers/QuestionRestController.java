package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Hebergement;
import com.meriem.casavia.entities.Question;
import com.meriem.casavia.entities.User;
import com.meriem.casavia.repositories.HebergementRepository;
import com.meriem.casavia.repositories.QuestionRepository;
import com.meriem.casavia.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
@CrossOrigin
public class QuestionRestController {
    @Autowired
    QuestionRepository questionRep;
    @Autowired
    UserRepository userRep;
    @Autowired
    HebergementRepository hebergementRep;
    @PostMapping("/save")
    public Question ajouterQuestion(@RequestBody Question q,@RequestParam long user,@RequestParam long hebergement){
        Hebergement h=hebergementRep.findById(hebergement).get();
        User u=userRep.findById(user).get();
        q.setHebergement(h);
        q.setUser(u);
        return questionRep.save(q);
    }
    @GetMapping("/person/{id}")
    public List<Question> getQuestions(@PathVariable("id") long id){
        return questionRep.findQuestionsByPersonIdOrderByDateAskedDesc(id);
    }
    @GetMapping("/user/hebergement")
    public List<Question> findByUserAndHebergement(@RequestParam long hebergement,@RequestParam long user){
        Hebergement h=hebergementRep.findById(hebergement).get();
        User u=userRep.findById(user).get();
        return questionRep.findByHebergementAndUser(h,u);
    }
}
