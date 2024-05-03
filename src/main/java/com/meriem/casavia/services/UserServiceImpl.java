package com.meriem.casavia.services;

import com.meriem.casavia.entities.Code;
import com.meriem.casavia.entities.User;
import com.meriem.casavia.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository UserRep;
    @Autowired
    CodeService codeservice;
    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender mailSender;
    private static String temporaryCode;

    @Override
    public User ajouterUser(User c) {
      return UserRep.save(c);
    }

    @Override
    public User modifierUserNom(long id, String nom) {
        User u =UserRep.findById(id).get();
        u.setNom(nom);
        UserRep.save(u);

        return u;
    }

    @Override
    public User modifierUserPrenom(long id, String prenom) {
        User u =UserRep.findById(id).get();
        u.setPrenom(prenom);
        UserRep.save(u);
        return u;
    }

    @Override
    public User modifierUserEmail(long id, String email) {
        User u =UserRep.findById(id).get();
        u.setEmail(email);
        UserRep.save(u);
        return u;
    }

    @Override
    public User modifierUserPays(long id, String pays) {
        User u =UserRep.findById(id).get();
        u.setPays(pays);
        UserRep.save(u);
        return u;
    }

    @Override
    public User modifierUsertlf(long id, String phone) {
        User u =UserRep.findById(id).get();
        u.setTlf(phone);
        UserRep.save(u);
        return u;
    }


    @Override
    public User getUser(Long id) {
        return UserRep.findById(id).get();
    }

    @Override
    public String sendCodeByEmail(String email, String code) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject("Your authentication code is:");

            // Utilisez le template HTML
            String htmlBody = "<!DOCTYPE html>" +
                    "<html lang=\"en\">" +
                    "<head>" +
                    "<meta charset=\"UTF-8\">" +
                    "<title>Email Verification</title>" +
                    "<style>" +
                    "body { font-family: Arial, sans-serif; background-color: #f2f2f2; margin: 0; padding: 0; }" +
                    ".container { width: 80%; max-width: 600px; margin: 20px auto; background-color: #fff; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); padding: 20px; }" +
                    "h1 { color: #333; }" +
                    "p { color: #555; }" +
                    ".verification-code { background-color: #f0f0f0; border: 1px solid #ccc; border-radius: 5px; padding: 10px; font-size: 18px; margin-bottom: 20px; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class=\"container\">" +
                    "<img src=\"https://drive.google.com/uc?export=view&id=1RFsQLMCQ2GJdYDiu28OjdA6O6VEfn4P8\" alt=\"Email Verification Image\" style=\"width: 100%; max-width: 100%; height: auto;\">" +
                    "<p>Hey there!</p>" +
                    "<p>Please use the verification code below to verify your email address:</p>" +
                    "<div class=\"verification-code\">" + code + "</div>" +
                    "<p>Thank you.</p>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            helper.setText(htmlBody, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return code;
    }
    @Override
    public String sendResetPasswordEmail(String email, String firstName, String lastName) {
        MimeMessage message = mailSender.createMimeMessage();


        String resetPasswordLink = "http://localhost:4200/reset_password?email=" + email;
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
                "<h2>Hello " + firstName + " " + lastName + ",</h2>" +
                "<p>Thank you for contacting us.</p>" +
                "<p>Please use the link below to reset or create a new password:</p>" +
                "<a href=\"" + resetPasswordLink + "\" class=\"button\" style=\"background-color: #004a99; color: white; padding: 10px 20px; text-decoration: none; border: none; border-radius: 3px; cursor: pointer; display: inline-block;\">Reset Password</a>\n" +

                "<p>If you believe you have received this email in error, please <a href='http://localhost:4200/contact'>contact us</a> immediately.</p>" +
                "</div>" +
                "<div class='footer'>" +
                "<p>Regards,</p>" +
                "<p>The Casavia Team</p>" +
                "</div>" +
                "</body>" +
                "</html>";

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject("Your Password Reset Request");

            helper.setText(htmlBody, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return email;
    }



    @Override
    public boolean ChangePassword(long id, String currentpassword, String newpassword) {
       User u= UserRep.findById(id).get();
       if(u.getMot_de_passe().equals(currentpassword)){
           u.setMot_de_passe(newpassword);
           UserRep.save(u);
           return true;
       }
        return false;
    }
    @Override
    public User RecoverPassword(long id,String newpassword) {
        User u= UserRep.findById(id).get();

            u.setMot_de_passe(newpassword);
            UserRep.save(u);
            return u;


    }


}
