package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Person;
import com.meriem.casavia.entities.language;
import com.meriem.casavia.repositories.CategorieRepository;
import com.meriem.casavia.repositories.HebergementRepository;
import com.meriem.casavia.services.LanguageService;
import com.meriem.casavia.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/language")
@CrossOrigin
public class LanguageRestController {
    @Autowired
    LanguageService langaugeserv;
    @Autowired
    HebergementRepository heberRep;

}
