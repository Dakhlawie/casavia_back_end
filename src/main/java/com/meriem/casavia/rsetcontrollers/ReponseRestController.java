package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Question;
import com.meriem.casavia.entities.Reponse;
import com.meriem.casavia.entities.User;
import com.meriem.casavia.repositories.QuestionRepository;
import com.meriem.casavia.repositories.ReponseRepository;
import com.meriem.casavia.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reponse")
@CrossOrigin
public class ReponseRestController {
    @Autowired
    ReponseRepository reponseRep;
    @Autowired
    QuestionRepository questionRep;
    @PostMapping("/save")
    public Reponse ajouterReponse(@RequestBody Reponse reponse,@RequestParam long id){
        Question q=questionRep.findById(id).get();
        reponse.setQuestion(q);
      return  reponseRep.save(reponse);

    }
}
