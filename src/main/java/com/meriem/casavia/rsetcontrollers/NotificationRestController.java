package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Notification;
import com.meriem.casavia.entities.Person;
import com.meriem.casavia.repositories.NotificationRepository;
import com.meriem.casavia.repositories.PersonRepository;
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
    @PostMapping("/save")
    public Notification sendNotificationToPerson(@RequestBody Notification notif ,@RequestParam("person_id") long id){
        System.out.println(id);
        Person p=this.personRep.findById(id).get();
        notif.setPerson(p);
        return this.notifRep.save(notif);
    }
    @GetMapping("/person/{id}")
    public List<Notification> findByPerson(@PathVariable("id") long id){
        Person p=this.personRep.findById(id).get();
        return notifRep.findByPersonOrderByDateDesc(p);
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

}
