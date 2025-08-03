package com.example.trainservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TrainServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(TrainServiceApplication.class, args);
  }
}