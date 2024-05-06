package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Visitor;
import com.meriem.casavia.repositories.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;


@RestController
@RequestMapping("/visitor")
@CrossOrigin
public class VisitorRestController {
    @Autowired
    VisitorRepository visitorRep;

    @PostMapping("/save")
    public Visitor ajouterVisitor() {
        Visitor v = new Visitor();
        Random rand = new Random();
        String code=String.format("%06d", rand.nextInt(1000000));
        v.setVisitor_id(code);
        return visitorRep.save(v);
    }
}
