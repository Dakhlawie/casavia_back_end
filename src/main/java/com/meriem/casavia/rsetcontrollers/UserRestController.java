package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Code;
import com.meriem.casavia.entities.EmailBody;
import com.meriem.casavia.entities.User;
import com.meriem.casavia.entities.verification;
import com.meriem.casavia.repositories.UserRepository;
import com.meriem.casavia.repositories.verificationRepository;
import com.meriem.casavia.services.CodeService;
import com.meriem.casavia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
    @Autowired
    CodeService codeService;
    @Autowired
    verificationRepository verifRep;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private static String temporaryCode;
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

    @PutMapping("/updatenom/{id}")
    public User modifierUserNom(@PathVariable Long id,@RequestBody String nom){

        return userService.modifierUserNom(id,nom);
    }
    @PutMapping("/updateprenom/{id}")
    public User modifierUserPrenom(@PathVariable Long id,@RequestBody String prenom){

        return userService.modifierUserPrenom(id,prenom);
    }
    @PutMapping("/updateemail/{id}")
    public User modifierUserEmail(@PathVariable Long id,@RequestBody String email){

        return userService.modifierUserEmail(id,email);
    }
    @PutMapping("/updatepays/{id}")
    public User modifierUserPays(@PathVariable Long id,@RequestBody String pays){

        return userService.modifierUserPays(id,pays);
    }
    @PutMapping("/updatetlf/{id}")
    public User modifierUserTlf(@PathVariable Long id,@RequestBody String phone){

        return userService.modifierUsertlf(id,phone);
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
    }
    @PostMapping("/register")
    public String sendCodeByEmail(@RequestBody EmailBody body) {
        verification existingVerification = verifRep.findByEmail(body.getEmail());
        if (existingVerification != null) {

            verifRep.delete(existingVerification);
        }
        Random rand = new Random();
        String code=String.format("%06d", rand.nextInt(1000000));

        verifRep.save(new verification(null,body.getEmail(),code));
        userService.sendCodeByEmail(body.getEmail(), code);



        return body.getEmail();
    }
    @PostMapping("/recover")
    public String sendRecoverEmail(@RequestParam String email,@RequestParam  String firstname,@RequestParam String lastname){
        return this.userService.sendResetPasswordEmail(email,firstname,lastname);
    }
    @PostMapping("/verifcode")
    public ResponseEntity<Map<String, Boolean>>verifyCode(@RequestBody verification v ) {
        System.out.println("VERIFICATION"+v);

        verification verif=verifRep.findByEmail(v.getEmail());
        System.out.println("EQUALS"+verif.getCode().equals(v.getCode()));
        if(verif.getCode().equals(v.getCode())){
            verifRep.deleteById(verif.getId());
            return ResponseEntity.ok(Collections.singletonMap("result", true));
        }
        return ResponseEntity.ok(Collections.singletonMap("result", false));
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id){
       return  userRepository.findById(id).get();
    }
    @PutMapping("/change-password")
    public ResponseEntity<Boolean> changePassword(
            @RequestParam Long id,
            @RequestParam String currentPassword,
            @RequestParam String newPassword) {

        User u = userRepository.findById(id).get();
       boolean compare= this.bCryptPasswordEncoder.matches(currentPassword, u.getMot_de_passe());
        if (compare){
            u.setMot_de_passe(this.bCryptPasswordEncoder.encode(newPassword));
            userRepository.save(u);
            return ResponseEntity.ok( true);
        }
        return ResponseEntity.badRequest().body(false);

    }
    @PutMapping("/recover-password")
    public User RecoverPassword(
            @RequestParam Long id,

            @RequestParam String newPassword) {

        User u = userRepository.findById(id).get();

            u.setMot_de_passe(this.bCryptPasswordEncoder.encode(newPassword));
            userRepository.save(u);
            return u;



    }
    @GetMapping("/findByEmail")
    public boolean findByEmail(@RequestParam String email){
      User u=  this.userRepository.findUserByEmail(email);
      if(u!=null){
          return true;
      }
     return false;
    }
    @GetMapping("/findUserByEmail")
    public User findByUserEmail(@RequestParam String email){
        User u=  this.userRepository.findUserByEmail(email);
        if(u!=null){
            return u;
        }
        return null;
    }
}
