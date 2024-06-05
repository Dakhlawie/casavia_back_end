package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Conversation;
import com.meriem.casavia.entities.Message;
import com.meriem.casavia.entities.Person;
import com.meriem.casavia.entities.User;
import com.meriem.casavia.repositories.ConversationRepository;
import com.meriem.casavia.repositories.MessageRepository;
import com.meriem.casavia.repositories.PersonRepository;
import com.meriem.casavia.repositories.UserRepository;
import com.pusher.rest.Pusher;
import com.pusher.rest.data.Result;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/conversation")
@CrossOrigin
public class ConversationRestController {
    @Autowired
    ConversationRepository convRep;
    @Autowired
    UserRepository userRep;
    @Autowired
    PersonRepository personRep;
    @Autowired
    MessageRepository msgRep;
    @Value("${PUSHER_PUBLIC_KEY}")
    private String pusherPublicKey;

    @Value("${PUSHER_SECRET_KEY}")
    private String pusherSecretKey;

    @Value("${PUSHER_CLUSTER}")
    private String pusherCluster;

    @Value("${PUSHER_APP_ID}")
    private String pusherAppId;



    @PostMapping("/add")
    public Conversation addConversation(@RequestBody Conversation c,@RequestParam Long userId, @RequestParam Long partnerId) {
        User user = userRep.findById(userId).get();
        Person partner = personRep.findById(partnerId).get();
        c.setUser(user);
        c.setPartner(partner);

        return convRep.save(c);
    }

    @GetMapping("/find")
    public Conversation findByUserAndPartner(@RequestParam Long userId, @RequestParam Long partnerId) {
        User user = userRep.findById(userId).get();
        Person partner = personRep.findById(partnerId).get();


        return convRep.findByUserAndPartner(user, partner);
    }
    @GetMapping("/partner/{partnerId}")
    public List<Conversation> getConversationsByPartner(@PathVariable Long partnerId) {
        Person partner = personRep.findById(partnerId).get();

        return convRep.findByPartner(partner);
    }
    @PostMapping("/{conversationId}/addMessage")
    public Message addMessage(@PathVariable Long conversationId, @RequestBody Message message) {

        System.out.println("PUSHER APP ID: " + this.pusherAppId);
        System.out.println("PUSHER PUBLIC KEY: " + this.pusherPublicKey);
        System.out.println("PUSHER SECRET KEY: " + this.pusherSecretKey);
        System.out.println("PUSHER CLUSTER: " + this.pusherCluster);
        Pusher pusher = new Pusher("1812828", "8fc7ac37b7848dcbc3a9","f3c87c156bef6b2cc6a5");
        pusher.setCluster("mt1");


                Conversation conversation = convRep.findById(conversationId).get();

        message.setConversation(conversation);
        Message msg = msgRep.save(message);


        Map<String, Object> eventData = new HashMap<>();
        eventData.put("message", msg.getContent());
        eventData.put("sender", msg.getSenderId());
        eventData.put("role", msg.getRole());

        Result result = pusher.trigger("partner-user-messages", "upcoming-message", eventData
        );

        System.out.println("Pusher trigger response: " + result.getStatus());
        System.out.println("Pusher trigger Message: " + result.getMessage());
        return msg;
    }
    @GetMapping("/user/{userId}")
    public List<Conversation> getConversationsByUser(@PathVariable Long userId) {
        User user = userRep.findById(userId).get();

        return convRep.findByUser(user);
    }
    @PutMapping("/markPartnerAsSeen")
    @Transactional
    public void markMessagesAsSeen(@RequestBody Conversation conversation) {
        convRep.markMessagesAsSeen(conversation);
    }
    @GetMapping("/countUnseenMessages/{userId}")
    public Long countUnseenMessagesByUser(@PathVariable Long userId) {
        return convRep.countUnseenMessagesByUser(userId);

    }
    @PutMapping("/markUserAsSeen")
    @Transactional
    public void markMessagesUserAsSeen(@RequestBody Conversation conversation) {
        convRep.markMessagesUserAsSeen(conversation);
    }
    @GetMapping("/countUnseenMessagesPartner/{userId}")
    public Long countUnseenMessagesByPartner(@PathVariable Long userId) {
        return convRep.countUnseenMessagesByPartner(userId);

    }
    @GetMapping("/unseen-count")
    public Long getUnseenMessagesCount(
            @RequestParam Long partnerId,
            @RequestParam Long conversationId) {
     return   convRep.countUnseenMessagesByPartnerAndConversation(partnerId, conversationId);

    }
    @GetMapping("/exists")
    public boolean checkConversationExists(@RequestParam Long userId, @RequestParam Long partnerId) {
        User user = userRep.findById(userId).get();
        Person partner = personRep.findById(partnerId).get();

        return convRep.existsByUserAndPartner(user, partner);
    }
}
