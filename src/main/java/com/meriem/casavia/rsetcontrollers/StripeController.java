package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.ChargeRequest;
import com.meriem.casavia.entities.PaymentResponse;
import com.meriem.casavia.services.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Refund;
import com.stripe.model.Token;
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

            logger.info("Creating card token...");
            Token token = stripeService.createCardToken(
                    request.getNumber(),
                    request.getExpMonth(),
                    request.getExpYear(),
                    request.getCvc()
            );

            logger.info("Card token created successfully: " + token.getId());

            logger.info("Creating charge...");
            Charge charge = stripeService.createCharge(
                    token.getId(),
                    request.getAmount() * 100, // Montant en cents
                    request.getCurrency()
            );

            logger.info("Charge created successfully: " + charge.getId());

            PaymentResponse response = new PaymentResponse();
            response.setId(charge.getId());
            response.setAmount(charge.getAmount().intValue());
            response.setCurrency(charge.getCurrency());
            response.setStatus(charge.getStatus());

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
