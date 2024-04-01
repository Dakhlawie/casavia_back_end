package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Avis;
import com.meriem.casavia.entities.Categorie;
import com.meriem.casavia.services.AvisService;
import com.meriem.casavia.services.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categorie")
@CrossOrigin
public class CategorieRestController {
    @Autowired
    CategorieService categorieServ;
    @GetMapping("/all")
    public List<Categorie> getAllCategorie(){
        return categorieServ.getAllCategorie();
    }
}
