package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.AccountClosure;
import com.meriem.casavia.entities.Contact;
import com.meriem.casavia.repositories.AccountClosureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account_closure")
@CrossOrigin
public class AccountClosureRestController {
    @Autowired
    AccountClosureRepository accountClosureRep;
    @PostMapping("/save")
    public AccountClosure ajouterAccountClosure(@RequestBody AccountClosure c){
        return accountClosureRep.save(c);
    }
    @GetMapping("/all")
    public List<AccountClosure> getAllAccountClosure(){
        return accountClosureRep.findAll();

    }

}
