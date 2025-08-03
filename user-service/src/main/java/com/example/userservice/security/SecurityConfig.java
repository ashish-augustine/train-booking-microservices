package com.example.userservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.userservice.service.UserDetailsServiceImpl;

@Configuration
public class SecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) 
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
          .userDetailsService(userDetailsService)
          .passwordEncoder(passwordEncoder)
          .and()
          .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
          .authorizeRequests()
          .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .antMatchers("/users/register","/users/login").permitAll()
          .anyRequest()
          .authenticated();
        return http.build();
    }
}