package com.example.userservice.controller;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtUtil jwtUtil;

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody User user) {
    if (userRepository.findByUsername(user.getUsername()).isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
    }
    user.setPassword(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(user.getPassword()));
    User saved = userRepository.save(user);
    return ResponseEntity.ok(Map.of("username", saved.getUsername()));
  }

  @PostMapping("/login")
  public ResponseEntity<?> loginUser(@RequestBody Map<String, String> credentials) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(credentials.get("username"), credentials.get("password"))
      );

      User user = userRepository.findByUsername(credentials.get("username")).get();

      String token = jwtUtil.generateToken(user.getId());
      return ResponseEntity.ok(Map.of("token", token));
    } catch (BadCredentialsException ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
  }
}