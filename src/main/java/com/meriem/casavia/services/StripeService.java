package com.meriem.casavia.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Refund;
import com.stripe.model.Token;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.RefundCreateParams;
import com.stripe.param.TokenCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



@Service
public class StripeService {

    @Value("${stripe.api.secretKey}")
    private String apiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = apiKey;
    }

    public Token createCardToken(String number, int expMonth, int expYear, String cvc) throws StripeException {
        TokenCreateParams.Card card = TokenCreateParams.Card.builder()
                .setNumber(number)
                .setExpMonth(String.valueOf(expMonth))
                .setExpYear(String.valueOf(expYear))
                .setCvc(cvc)
                .build();

        TokenCreateParams params = TokenCreateParams.builder()
                .setCard(card)
                .build();

        return Token.create(params);
    }

    public Charge createCharge(String token, int amount, String currency) throws StripeException {
        ChargeCreateParams chargeParams = ChargeCreateParams.builder()
                .setAmount((long) amount)
                .setCurrency(currency)
                .setSource(token)
                .build();

        return Charge.create(chargeParams);
    }

    public Refund refundCharge(String chargeId, int amount) throws StripeException {
        RefundCreateParams params = RefundCreateParams.builder()
                .setCharge(chargeId)
                .setAmount((long) amount)
                .build();

        return Refund.create(params);
    }
}
