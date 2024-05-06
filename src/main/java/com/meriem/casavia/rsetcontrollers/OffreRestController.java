package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Offre;
import com.meriem.casavia.repositories.OffreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/offre")
@CrossOrigin
public class OffreRestController {
    @Autowired
    OffreRepository offreRep;

}
