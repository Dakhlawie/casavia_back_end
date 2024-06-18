//package com.meriem.casavia.rsetcontrollers;
//
//import com.meriem.casavia.entities.ChargeRequest;
//import com.meriem.casavia.services.StripeService;
//import com.stripe.exception.StripeException;
//import com.stripe.model.Charge;
//import com.stripe.param.checkout.SessionCreateParams;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/checkout")
//@CrossOrigin
//public class CheckoutController {
//
//    @Value("${STRIPE_PUBLIC_KEY}")
//    private String stripePublicKey;
//
//    @PostMapping("/create-session")
//    void createSession() {
//        String YOUR_DOMAIN = "http://localhost:3000";
//        SessionCreateParams params =
//                SessionCreateParams.builder()
//                        .setMode(SessionCreateParams.Mode.PAYMENT)
//                        .setSuccessUrl(YOUR_DOMAIN + "?success=true")
//                        .setCancelUrl(YOUR_DOMAIN + "?canceled=true")
//                        .setCurrency("usd")
//                        .addLineItem(
//                                SessionCreateParams.LineItem.builder()
//                                        .setQuantity(1L)
//                                        // Provide the exact Price ID (for example, pr_1234) of the product you want to sell
//                                        .setPrice(1000  + "")
//
//                                        .build())
//                        .build();
//    }
//}
