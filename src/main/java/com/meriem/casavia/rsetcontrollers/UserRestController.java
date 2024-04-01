package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.User;
import com.meriem.casavia.repositories.UserRepository;
import com.meriem.casavia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserRestController {
    @Autowired
    UserService userService;
    @Autowired
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @PostMapping("/saveUser")

public User ajouterUser(@RequestBody User user){
        user.setMot_de_passe(this.bCryptPasswordEncoder.encode(user.getMot_de_passe()));
        return userService.ajouterUser(user);
    }
    @PutMapping("/update")
    public User modifierUser(@RequestBody User user){
        user.setMot_de_passe(this.bCryptPasswordEncoder.encode(user.getMot_de_passe()));
        return userService.ajouterUser(user);
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody User user) {
        System.out.println("in login-user" + user);
        HashMap<String, Object> response = new HashMap<>();

        User userFromDB = userRepository.findUserByEmail(user.getEmail());
        System.out.println("userFromDB+user" + userFromDB);
        if (userFromDB == null) {
            response.put("message", "user not found !");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            boolean compare = this.bCryptPasswordEncoder.matches(user.getMot_de_passe(), userFromDB.getMot_de_passe());
            System.out.println("compare" + compare);
            if (!compare) {
                response.put("message", "user not found !");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            } else {
                String token = Jwts.builder()
                        .claim("data", userFromDB)
                        .signWith(SignatureAlgorithm.HS256, "SECRET")
                        .compact();
                response.put("token", token);
                System.out.println("hhh");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }

        }
    }}
