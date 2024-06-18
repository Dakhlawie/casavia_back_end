package com.meriem.casavia.rsetcontrollers;

import com.meriem.casavia.entities.RefundRequest;
import com.meriem.casavia.services.PaypalService;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Refund;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paypal")
@CrossOrigin
public class PaypalRestController {
    @Autowired
    private PaypalService paypalService;

    @PostMapping("/pay")
    public Payment pay(@RequestParam double total,
                       @RequestParam String currency,
                       @RequestParam String method,
                       @RequestParam String intent,
                       @RequestParam String description,
                       @RequestParam String cancelUrl,
                       @RequestParam String successUrl) throws PayPalRESTException {
        return paypalService.createPayment(total, currency, method, intent, description, cancelUrl, successUrl);
    }

    @PostMapping("/execute")
    public Payment execute(@RequestParam String paymentId, @RequestParam String payerId) throws PayPalRESTException {
        return paypalService.executePayment(paymentId, payerId);
    }

//    @PostMapping("/refund")
//    public Refund refund(@RequestParam String saleId,
//                         @RequestParam double amount,
//                         @RequestParam String currency) throws PayPalRESTException {
//        return paypalService.refundPayment(saleId, amount, currency);
//    }
@PostMapping("/refund")
public ResponseEntity<?> refundPayment(
        @RequestBody RefundRequest refundRequestDto) {
    try {
        Refund refund = paypalService.refundPayment(
                refundRequestDto.getTransactionId(),
                refundRequestDto.getAmount(),
                refundRequestDto.getCurrency()
        );
        return ResponseEntity.ok(refund);
    } catch (PayPalRESTException e) {
        return ResponseEntity.status(400).body(e.getDetails());
    }
}
}
