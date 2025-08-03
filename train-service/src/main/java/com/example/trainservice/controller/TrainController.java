package com.example.trainservice.controller;

import com.example.trainservice.model.Train;
import com.example.trainservice.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/trains")
public class TrainController {

  @Autowired
  private TrainService trainService;

  @GetMapping
  public ResponseEntity<List<Train>> getAllTrains() {
    return ResponseEntity.ok(trainService.getAllTrains());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getTrainById(@PathVariable Long id) {
    return trainService.getTrainById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }
}