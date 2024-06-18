package com.meriem.casavia.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.param.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



@Service
public class StripeService {

    @Value("${STRIPE_SECRET_KEY}")
    private String apiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = apiKey;
    }

    public PaymentMethod createPaymentMethod(String number, int expMonth, int expYear, String cvc) throws StripeException {
        PaymentMethodCreateParams.CardDetails card = PaymentMethodCreateParams.CardDetails.builder()
                .setNumber(number)
                .setExpMonth((long) expMonth)
                .setExpYear((long) expYear)
                .setCvc(cvc)
                .build();

        PaymentMethodCreateParams params = PaymentMethodCreateParams.builder()
                .setType(PaymentMethodCreateParams.Type.CARD)
                .setCard(card)
                .build();

        return PaymentMethod.create(params);
    }

    public PaymentIntent createPaymentIntent(int amount, String currency, String paymentMethodId) throws StripeException {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount((long) amount)
                .setCurrency(currency)
                .setPaymentMethod("pm_card_visa")
                .setConfirm(true) // Confirmez le paiement immédiatement
                .build();

        return PaymentIntent.create(params);
    }

    public Refund refundCharge(String chargeId, int amount) throws StripeException {
        RefundCreateParams params = RefundCreateParams.builder()
                .setCharge(chargeId)
                .setAmount((long) amount)
                .build();

        return Refund.create(params);
    }
}
