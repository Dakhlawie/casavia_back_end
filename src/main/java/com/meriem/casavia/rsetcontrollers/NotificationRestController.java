package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Notification;
import com.meriem.casavia.entities.Person;
import com.meriem.casavia.entities.User;
import com.meriem.casavia.repositories.NotificationRepository;
import com.meriem.casavia.repositories.PersonRepository;
import com.meriem.casavia.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@CrossOrigin
public class NotificationRestController {
    @Autowired
    NotificationRepository notifRep;
    @Autowired
    PersonRepository personRep;
    @Autowired
    UserRepository userRep;
    @PostMapping("/save")
    public Notification sendNotificationToPerson(@RequestBody Notification notif ,@RequestParam("person_id") long id){
        System.out.println(id);
        Person p=this.personRep.findById(id).get();
        notif.setPerson(p);
        return this.notifRep.save(notif);
    }
    @PostMapping("/save/user")
    public Notification sendNotificationToUser(@RequestBody Notification notif ,@RequestParam("user_id") long id){

        User p=this.userRep.findById(id).get();
        notif.setUser(p);
        return this.notifRep.save(notif);
    }
    @GetMapping("/person/{id}")
    public List<Notification> findByPerson(@PathVariable("id") long id){
        Person p=this.personRep.findById(id).get();
        return notifRep.findByPersonOrderByDateDesc(p);
    }
    @GetMapping("/user/{id}")
    public List<Notification> findByUser(@PathVariable("id") long id){
        User u=this.userRep.findById(id).get();
        return notifRep.findByUserOrderByDateDesc(u);
    }
    @PutMapping("/seen/{id}")
    public void markAsSeen(@PathVariable("id") long id){
        Notification n=notifRep.findById(id).get();
        n.setSeen(true);
        notifRep.save(n);

    }
    @GetMapping("/false/{id}")
    public long count (@PathVariable("id") long id){
        Person p=this.personRep.findById(id).get();
       return  this.notifRep.countByPersonAndSeenFalse(p);
    }
    @GetMapping("/seenfalse/{id}")
    public long countByUser (@PathVariable("id") long id){
        User u=this.userRep.findById(id).get();
        return  this.notifRep.countByUserAndSeenFalse(u);
    }

}
