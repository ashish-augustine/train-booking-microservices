package com.example.trainservice.controller;

import com.example.trainservice.model.Booking;
import com.example.trainservice.service.BookingService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/booking")
public class BookingController {

  @Autowired
  private BookingService bookingService;

  @Value("${jwt.secret}")
  private String jwtSecret;

  @PostMapping("/{trainId}")
  public ResponseEntity<?> bookTrain(@RequestHeader("Authorization") String authHeader,
                                     @PathVariable Long trainId) {
    try {
      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
      }
      String token = authHeader.substring(7);
      Claims claims = Jwts.parserBuilder()
              .setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8))
              .build()
              .parseClaimsJws(token)
              .getBody();

      String username = claims.getSubject();

      // For demo: username must be a number (userId). In production, map properly calling User Service.
      Long userId = Long.valueOf(username);

      Booking booking = bookingService.bookSeat(userId, trainId);
      return ResponseEntity.ok(booking);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}