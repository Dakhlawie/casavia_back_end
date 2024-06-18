package com.meriem.casavia.services;

import com.meriem.casavia.entities.RefundRequest;
import com.meriem.casavia.repositories.OrderRepository;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.meriem.casavia.entities.Order;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

@Service
public class PaypalService {
    @Autowired
    private APIContext apiContext;

    @Autowired
    private OrderRepository orderRepository;
    public Payment createPayment(
            Double total,
            String currency,
            String method,
            String intent,
            String description,
            String cancelUrl,
            String successUrl) throws PayPalRESTException {

        Amount amount = new Amount();
        amount.setCurrency(currency);

        amount.setTotal(String.format("%.2f", total).replace(',', '.'));
        System.out.println(amount.getTotal());

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());

        Payment payment = new Payment();
        payment.setIntent(intent.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);
        Payment createdPayment = payment.create(apiContext);
        Order order = new Order();
        order.setPrice(total);
        order.setCurrency(currency);
        order.setMethod(method);
        order.setIntent(intent);
        order.setDescription(description);
        order.setPaymentId(createdPayment.getId());
        for (Transaction trans : createdPayment.getTransactions()) {

            for (RelatedResources resource : trans.getRelatedResources()) {
                System.out.println("hello");
                if (resource.getSale() != null) {
                    String saleId = resource.getSale().getId();
                    System.out.println("Sale ID: " + saleId);

                }
            }
        }

        orderRepository.save(order);
        return createdPayment;
    }
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        Payment executedPayment = payment.execute(apiContext, paymentExecute);

        Order order = orderRepository.findByPaymentId(paymentId);
        order.setPayerId(payerId);
        orderRepository.save(order);

        return executedPayment;
    }
    public String getSaleIdByTransactionId(String transactionId) throws PayPalRESTException {
        Payment payment = Payment.get(apiContext, transactionId);
        List<Transaction> transactions = payment.getTransactions();
        if (transactions != null && !transactions.isEmpty()) {
            List<RelatedResources> relatedResources = transactions.get(0).getRelatedResources();
            if (relatedResources != null && !relatedResources.isEmpty()) {
                Sale sale = relatedResources.get(0).getSale();
                if (sale != null) {
                    return sale.getId();
                }
            }
        }
        throw new PayPalRESTException("Sale ID not found for transaction ID: " + transactionId);
    }

    public Refund refundPayment(String transactionId, double amount, String currency) throws PayPalRESTException {

        Sale sale = new Sale();
        sale.setId(transactionId);


        Amount refundAmount = new Amount();
        refundAmount.setCurrency(currency);
        refundAmount.setTotal(String.format("%.2f", amount));
        Refund refund = new Refund();
        refund.setAmount(refundAmount);

        Refund executedRefund = sale.refund(apiContext, refund);

        return executedRefund;
    }
}
