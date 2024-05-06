package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.Contact;
import com.meriem.casavia.repositories.ContactRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact")
@CrossOrigin
public class ContactRestController {
    @Autowired
    ContactRepository contactRep;
    @Value("${spring.mail.username}")

    private String from;
    @Autowired
    private JavaMailSender mailSender;
    @PostMapping("/save")
    public Contact ajouterContact(@RequestBody Contact contact){
        return contactRep.save(contact);
    }
    @GetMapping("/all")
    public List<Contact> getAllContacts(){
        return contactRep.findAll();
    }
    @PostMapping("/confirmation")
    public void sendEmail(@RequestParam String email, @RequestParam String name){
        System.out.println("hello");
        MimeMessage message = mailSender.createMimeMessage();
        String htmlBody ="<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #ffffff; text-align: center;}" +
                ".header img { width: 20%; max-width: 200px; margin: 20px auto; margin: 0 auto; display: block; }" +
                ".container {  margin: 0 auto;width: 600px; border: 1px solid #ccc; padding: 20px; background-color: #f9f9f9;box-sizing: border-box; }" +
                ".footer { text-align: left; /* Align footer text to the left */\n" +
                "            width: 600px;\n" +
                "            margin: 20px auto; /* Aligns the footer at the same level as the container */\n" +
                "            padding: 0 20px; }" +
                ".button { background-color: #0b2370; color: white; padding: 10px 20px; text-decoration: none; border: none; border-radius: 3px; cursor: pointer; }" +
                "h2 { color: #0b2370; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='header'>" +
                "<img src='https://drive.google.com/uc?export=view&id=1fok6tD7IdiNQ2C1IROCrs4Lu3ptIggKy' alt='CASAVIA Logo'>" +
                "</div>" +
                "<div class='container'>" +
                "<h2>Hello " + name + ",</h2>" +
                "<p>Thank you for contacting us.</p>" +
                "<p>Your request is currently being reviewed by our technical support team. We kindly ask for your patience as we assess your request.</p>" +

                "<p>If you have any further questions or concerns, please <a href='http://localhost:4200/contact'>contact us</a></p>" +
                "<p>We appreciate your patience and understanding.</p>" +
                "</div>" +
                "<div class='footer'>" +
                "<p>Best regards</p>" +
                "<p>The Casavia Team</p>" +
                "</div>" +
                "</body>" +
                "</html>";

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject("Confirmation of Receipt of Your Message");
            helper.setText(htmlBody, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
