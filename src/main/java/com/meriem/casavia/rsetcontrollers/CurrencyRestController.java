package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Categorie;
import com.meriem.casavia.entities.Currency;
import com.meriem.casavia.entities.Equipement;
import com.meriem.casavia.repositories.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/currency")
@CrossOrigin
public class CurrencyRestController {
    @Autowired
    CurrencyRepository currencyRep;
    @GetMapping("/all")
    public List<Currency> getAllCurrency(){
        return currencyRep.findAll();
    }
    @PostMapping("/save")
    public Currency addCurrency(@RequestBody Currency c){
        return  this.currencyRep.save(c);
    }
    @PutMapping("/update")
    public Currency updateCurrency(@RequestBody  Currency c,@RequestParam long id){
        Currency cr=currencyRep.findById(id).get();
        cr.setCurrency(c.getCurrency());
        cr.setSymbol(c.getSymbol());


        return  this.currencyRep.save(cr);
    }
    @DeleteMapping("/delete/{id}")
    public void DeleteCurrency(@PathVariable("id") long id){
        this.currencyRep.deleteById(id);
    }
    @GetMapping("/{id}")
    public Currency getCurrencyById(@PathVariable("id") long id){
        return this.currencyRep.findById(id).get();
    }
}
