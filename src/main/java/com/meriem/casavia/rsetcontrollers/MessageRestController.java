package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Message;
import com.meriem.casavia.entities.Person;
import com.meriem.casavia.entities.User;
import com.meriem.casavia.repositories.PersonRepository;
import com.meriem.casavia.repositories.UserRepository;
import com.meriem.casavia.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@CrossOrigin
public class MessageRestController {
    @Autowired
    private MessageService messageService;
    @Autowired
    UserRepository userRep;
    @Autowired
    PersonRepository personRep;

    @PostMapping("/send")
    public Message sendMessage(@RequestBody Message message) {
        return messageService.sendMessage(message);
    }

    @GetMapping("/conversation")
    public List<Message> getConversation(@RequestParam Long senderId, @RequestParam Long recipientId) {
        User u=userRep.findById(senderId).get();
        Person p=personRep.findById(recipientId).get();
        return messageService.getConversation(u,p);
    }
}
