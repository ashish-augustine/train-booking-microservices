package com.example.paymentservice.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.Stripe;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {

  @Value("${stripe.api.key}")
  private String stripeApiKey;

  @PostMapping("/charge")
  public ResponseEntity<?> charge(@RequestBody Map<String, Object> chargeRequest) {
    System.out.println("Processing payment: " + chargeRequest);
    Stripe.apiKey = stripeApiKey;

    try {
      Map<String, Object> params = new HashMap<>();
      params.put("amount", chargeRequest.get("amount"));
      params.put("currency", "usd");
      params.put("source", chargeRequest.get("source"));
      params.put("description", chargeRequest.get("description"));

      Charge charge = Charge.create(params);
      return ResponseEntity.ok(charge);
    } catch (StripeException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }
}