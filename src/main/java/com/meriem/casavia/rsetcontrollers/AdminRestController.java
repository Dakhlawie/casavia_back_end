package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Admin;

import com.meriem.casavia.repositories.AdminRepository;
import com.meriem.casavia.services.AdminService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminRestController {
    @Autowired
    AdminService adminServ;
    @Autowired
    AdminRepository adminRep;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @PostMapping("/save")
    public Admin ajouterAdmin(@RequestBody Admin admin){
        admin.setMot_de_passe(this.bCryptPasswordEncoder.encode(admin.getMot_de_passe()));
        return adminServ.ajouterAdmin(admin);
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginAdmin(@RequestBody Admin a) {
        System.out.println("in login-admin" +a );
        HashMap<String, Object> response = new HashMap<>();

        Admin adminFromDB = adminRep.findAdminByEmail(a.getEmail());
        System.out.println("adminFromDB+p" + adminFromDB);
        if (adminFromDB == null) {
            response.put("message", "admin not found !");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            boolean compare = this.bCryptPasswordEncoder.matches(a.getMot_de_passe(), adminFromDB.getMot_de_passe());
            System.out.println("compare" + compare);
            if (!compare) {
                response.put("message", "admin not found !");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            } else {
                String token = Jwts.builder()
                        .claim("data", adminFromDB)
                        .signWith(SignatureAlgorithm.HS256, "SECRET")
                        .compact();
                response.put("token", token);
                System.out.println("hhh");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }

        }
    }
}
