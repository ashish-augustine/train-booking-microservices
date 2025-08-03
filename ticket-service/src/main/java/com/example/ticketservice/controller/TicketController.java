package com.example.ticketservice.controller;

import com.example.ticketservice.model.Booking;
import com.example.ticketservice.repository.BookingRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

  @Value("${jwt.secret}")
  private String jwtSecret;

  private final BookingRepository bookingRepository;

  public TicketController(BookingRepository bookingRepository) {
    this.bookingRepository = bookingRepository;
  }

  @GetMapping
  public ResponseEntity<?> getTickets(@RequestHeader("Authorization") String authHeader) {
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

      Long userId = Long.valueOf(username);

      List<Booking> bookings = bookingRepository.findByUserId(userId);
      return ResponseEntity.ok(bookings);

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }
  }
}