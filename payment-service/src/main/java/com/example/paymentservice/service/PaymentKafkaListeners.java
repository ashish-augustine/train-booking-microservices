package com.example.paymentservice.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentKafkaListeners {

    //TODO: Add producer for sending payment events accordingly if needed.
    @KafkaListener(topics = "payment-events", groupId = "payment-service-group")
    public void handlePaymentEvent(String message) {
        System.out.println("Payment Service received payment event: " + message);
        // Handle payment event...
    }
}