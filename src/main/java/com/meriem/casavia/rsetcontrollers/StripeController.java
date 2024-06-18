package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.ChargeRequest;
import com.meriem.casavia.entities.PaymentResponse;
import com.meriem.casavia.services.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/stripe")
@CrossOrigin
public class StripeController {
    @Autowired
    private StripeService stripeService;
    private static final Logger logger = Logger.getLogger(StripeController.class.getName());

    @PostMapping("/charge")
    public ResponseEntity<Object> charge(@RequestBody ChargeRequest request) {
        try {
            logger.info("Received charge request: " + request);

            // Création de la méthode de paiement
            logger.info("Creating payment method...");
            PaymentMethod paymentMethod = stripeService.createPaymentMethod(
                    request.getNumber(),
                    request.getExpMonth(),
                    request.getExpYear(),
                    request.getCvc()
            );

            logger.info("Payment method created successfully: " + paymentMethod.getId());

            // Création de l'intention de paiement
            logger.info("Creating payment intent...");
            PaymentIntent paymentIntent = stripeService.createPaymentIntent(
                    request.getAmount() * 100, // Montant en cents
                    request.getCurrency(),
                    paymentMethod.getId()
            );

            logger.info("Payment intent created successfully: " + paymentIntent.getId());

            // Construction de la réponse
            PaymentResponse response = new PaymentResponse();
            response.setId(paymentIntent.getId());
            response.setAmount(paymentIntent.getAmount().intValue());
            response.setCurrency(paymentIntent.getCurrency());
            response.setStatus(paymentIntent.getStatus());

            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            logger.log(Level.SEVERE, "StripeException: " + e.getMessage(), e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception: " + e.getMessage(), e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "An unexpected error occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
