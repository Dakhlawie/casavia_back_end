package com.meriem.casavia.services;

import com.meriem.casavia.entities.Hebergement;
import com.meriem.casavia.entities.Reservation;
import com.meriem.casavia.entities.User;
import com.meriem.casavia.repositories.ReservationRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
@Service
public class ReservationServiceImpl implements ReservationService{
    @Autowired
    ReservationRepository ReservationRep;
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;
    public Reservation ajouterReservation(Reservation r){
        return this.ReservationRep.save(r);
    }
    public void annulerReservation(Long id){
        this.ReservationRep.deleteById(id);
    }

    public void sendEmail(Reservation reservation){
        System.out.println(reservation.getUser().getEmail());
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(reservation.getUser().getEmail());
            helper.setSubject("Reservation Submitted");



            String htmlBody = "<!DOCTYPE html>" +
                    "<html lang=\"en\">" +
                    "<head>" +
                    "<meta charset=\"UTF-8\">" +
                    "<title>Reservation Submitted</title>" +
                    "<style>" +
                    "body { font-family: Arial, sans-serif; background-color: #f2f2f2; margin: 0; padding: 0; }" +
                    ".container {" +
                    "  width: 80%;" +
                    "  max-width: 600px;" +
                    "  margin: 20px auto;" +
                    "  background-color: #fff;" +
                    "  border-radius: 10px;" +
                    "  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);" +
                    "  padding: 20px;" +
                    "}" +
                    "h3 { color: #333; }" +
                    "p { color: #555; }" +
                    "ul { color: #555; }" +

                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class=\"container\">" +
                    "<img src=\"https://drive.google.com/uc?export=view&id=1RFsQLMCQ2GJdYDiu28OjdA6O6VEfn4P8\" alt=\"Casavia Image\" style=\"width: 100%; max-width: 100%; height: auto;\">" +
                    "<h3>Reservation Submitted</h3>" +
                    "<p>You find below the details of your reservation:</p>" +
                    "<div >" +
                    "<ul>" +
                    "<li><b>Accommodation:</b> "  + reservation.getHebergement().getNom() +" </li>" +
                    "<li><b>Check-in Date:</b> " + reservation.getDateCheckIn() + "</li>" +
                    "<li><b>Check-out Date:</b> " + reservation.getDateCheckOut() +"</li>" +
                    "<li><b>Number of Rooms:</b> " + reservation.getNbRooms()+ "</li>" +
                    "<li><b>Price:</b> "  + reservation.getPrix()+"</li>" +
                    "</ul>" +
                    "</div>" +
                    "<p>After receiving the confirmation email, you will be able to proceed with the payment. We will send a confirmation email later.</p>" +
                    "<p>Please make sure to regularly check your inbox, including spam or junk folders, for any updates on your reservation.</p>" +
                    "<p>Best regards,<br>The Casavia Team</p>" +
                    "</div>" +
                    "</body>" +
                    "</html>";



            helper.setText(htmlBody, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendConfirmation(Reservation reservation) {
        Reservation r=this.ReservationRep.findById(reservation.getId()).get();
        r.setEtat("confirme");
        this.ReservationRep.save(r);
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(reservation.getUser().getEmail());
            helper.setSubject("Status of your reservation ");



            String htmlBody = "<!DOCTYPE html>" +
                    "<html lang=\"en\">" +
                    "<head>" +
                    "<meta charset=\"UTF-8\">" +
                    "<title>Confirmation of your booking with CasaVia</title>" +
                    "<style>" +
                    "body {" +
                    "  font-family: Arial, sans-serif;" +
                    "  background-color: #f2f2f2;" +
                    "  margin: 0;" +
                    "  padding: 0;" +
                    "}" +
                    ".container {" +
                    "  width: 80%;" +
                    "  max-width: 600px;" +
                    "  margin: 20px auto;" +
                    "  background-color: #fff;" +
                    "  border-radius: 10px;" +
                    "  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);" +
                    "  padding: 20px;" +
                    "}" +
                    "h2 {" +
                    "  color: #333;" +
                    "}" +
                    "p {" +
                    "  color: #555;" +
                    "}" +
                    ".reservation-details {" +
                    "  background-color: #f0f0f0;" +
                    "  border: 1px solid #ccc;" +
                    "  border-radius: 5px;" +
                    "  padding: 10px;" +
                    "  font-size: 16px;" +
                    "  margin-bottom: 20px;" +
                    "}" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class=\"container\">" +
                    "<img src=\"https://drive.google.com/uc?export=view&id=1RFsQLMCQ2GJdYDiu28OjdA6O6VEfn4P8\" alt=\"Casavia Image\" style=\"width: 100%; max-width: 100%; height: auto;\">" +
                    "<h2>Confirmation of your booking </h2>" +
                    "<p>We are pleased to inform you that your booking for a stay at "+reservation.getHebergement().getNom()+"has been successfully confirmed via the CasaVia application. Below are the details of your booking:</p>" +
                    "<div >" +
                    "<p><strong>Hotel:</strong> "+reservation.getHebergement().getNom()+"</p>" +
                    "<p><strong>Stay Dates:</strong>"+reservation.getDateCheckIn()+" to" +reservation.getDateCheckOut()+"</p>" +

                    "<p><strong>Number of Room:</strong> "+reservation.getNbRooms()+"</p>" +
                    "<p><strong>Total Amount:</strong> "+reservation.getPrix()+"</p>" +
                    "</div>" +
                    "<p><strong>Payment Deadline:</strong> You can now view your list of bookings in the CasaVia application and proceed with the payment to confirm your reservation. Please note that your booking will be automatically canceled if payment is not received within 24 hours.</p>" +
                    "<p><strong>Cancellation Policy:</strong> If you wish to cancel your reservation, please do so at least 48 hours before the scheduled arrival date to avoid cancellation fees. In the event of cancellation after this period, the hotel reserves the right to deduct 50% of the total booking amount. In case of a no-show without prior notice, the full amount paid will be retained by the hotel, and the booking will be considered non-canceled.</p>" +

                    "<p>We thank you for your trust and look forward to welcoming you to "+reservation.getHebergement().getNom()+" for a pleasant stay.</p>" +
                    "<p>Best regards,<br>The CasaVia Team</p>" +
                    "</div>" +
                    "</body>" +
                    "</html>";




            helper.setText(htmlBody, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendAnnulation(Reservation reservation) {
      this.ReservationRep.deleteById(reservation.getId());
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(reservation.getUser().getEmail());
            helper.setSubject("Status of your reservation ");
            String htmlBody = "<!DOCTYPE html>" +
                    "<html lang=\"en\">" +
                    "<head>" +
                    "<meta charset=\"UTF-8\">" +

                    "<style>" +
                    "body {" +
                    "  font-family: Arial, sans-serif;" +
                    "  background-color: #f2f2f2;" +
                    "  margin: 0;" +
                    "  padding: 0;" +
                    "}" +
                    ".container {" +
                    "  width: 80%;" +
                    "  max-width: 600px;" +
                    "  margin: 20px auto;" +
                    "  background-color: #fff;" +
                    "  border-radius: 10px;" +
                    "  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);" +
                    "  padding: 20px;" +
                    "}" +
                    "h3 {" +
                    "  color: #333;" +
                    "}" +
                    "p {" +
                    "  color: #555;" +
                    "}" +
                    "ul { color: #555; }" +
                    "li {" +
                    "  margin-bottom: 10px;" +
                    "}" +
                    ".theme-btn {" +
                    "  background-color: #007bff;" +
                    "  color: #fff;" +
                    "  text-decoration: none;" +
                    "  padding: 10px 20px;" +
                    "  border-radius: 5px;" +
                    "}" +
                    ".theme-btn:hover {" +
                    "  background-color: #0056b3;" +
                    "}" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class=\"container\">" +
                    "<img src=\"https://drive.google.com/uc?export=view&id=1RFsQLMCQ2GJdYDiu28OjdA6O6VEfn4P8\" alt=\"Casavia Image\" style=\"width: 100%; max-width: 100%; height: auto;\">" +
                    "<h3>Unavailability of Dates for Your Reservation at "+reservation.getHebergement().getNom()+"</h3>" +
                    "<p>Dear "+reservation.getUser().getNom()+" "+reservation.getUser().getPrenom()+",</p>" +
                    "<p>We are contacting you to inform you that unfortunately, we were unable to confirm your reservation for a stay at "+reservation.getHebergement().getNom()+" on the requested dates. </p>" +
                    "<p>Our teams have done their best to find availability that matches your dates, but unfortunately, they are not currently available. We invite you to consider the following options:</p>" +
                    "<ul>" +
                    "  <li><strong>Change your stay dates:</strong> We encourage you to consider changing the dates of your stay. If you are flexible, we would be happy to help you find an available period that fits your preferences.</li>" +
                    "  <li><strong>Explore other accommodations:</strong> If you are open to other options, we have a selection of similar accommodations in the area that may meet your needs. Feel free to browse our website to discover other possibilities.</li>" +
                    "  <li><strong>Get assistance from our team:</strong> If you have any questions or need assistance finding an alternative solution, our customer service team is here to help. Please don't hesitate to contact us for personalized assistance.</li>" +
                    "</ul>" +
                    "<p>We apologize for not being able to accommodate your request this time, but we hope to have the opportunity to welcome you soon. Your satisfaction is our top priority, and we will do everything we can to provide you with an exceptional experience at "+reservation.getHebergement().getNom()+".</p>" +
                    "<p>Best regards,<br>The casavia Team</p>" +
                    "</div>" +
                    "</body>" +
                    "</html>";





            helper.setText(htmlBody, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }
}
