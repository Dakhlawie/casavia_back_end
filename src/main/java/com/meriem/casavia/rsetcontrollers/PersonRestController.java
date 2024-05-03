package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Person;

import com.meriem.casavia.entities.User;
import com.meriem.casavia.repositories.PersonRepository;
import com.meriem.casavia.services.PersonService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/person")
@CrossOrigin
public class PersonRestController {
    @Autowired
    PersonService personserv;
    @Autowired
    PersonRepository personRep;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/save")
    public Person ajouterPerson(@RequestBody Person p){
        p.setRole("partenaire");
        p.setMot_de_passe(this.bCryptPasswordEncoder.encode(p.getMot_de_passe()));
        return personserv.ajouterPerson(p);
    }
    @PutMapping("/update/{id}")
    public Person modifierPerson(@PathVariable Long id,@RequestBody Person p){

        return personserv.modifierPerson(id, p);
    }
    @DeleteMapping("/delete/{id}")
    public void supprimerPerson(@PathVariable("id") Long id){
        personserv.supprimerPerson(id);
    }
    @GetMapping("/all")
    public List<Person> listPerson(){
        return personserv.getAllPerson();
    }
    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable("id") Long id){
        return personserv.getPersonById(id);
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginPerson(@RequestBody Person p) {
        System.out.println("in login-person" +p );
        HashMap<String, Object> response = new HashMap<>();

        Person partFromDB = personRep.findPersonByEmail(p.getEmail());
        System.out.println("personFromDB+p" + partFromDB);
        if (partFromDB == null) {
            response.put("message", "person not found !");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            boolean compare = this.bCryptPasswordEncoder.matches(p.getMot_de_passe(), partFromDB.getMot_de_passe());
            System.out.println("compare" + compare);
            if (!compare) {
                response.put("message", "person not found !");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            } else {
                String token = Jwts.builder()
                        .claim("data", partFromDB)
                        .signWith(SignatureAlgorithm.HS256, "SECRET")
                        .compact();
                response.put("token", token);
                System.out.println("hhh");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }

        }
    }

}
