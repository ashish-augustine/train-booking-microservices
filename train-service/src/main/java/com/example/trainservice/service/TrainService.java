package com.example.trainservice.service;

import com.example.trainservice.model.Train;
import com.example.trainservice.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainService {

  @Autowired
  private TrainRepository trainRepository;

  @Cacheable(value = "trainsCache")
  public List<Train> getAllTrains() {
    return trainRepository.findAll();
  }

  @Cacheable(value = "trainCache", key = "#id")
  public Optional<Train> getTrainById(Long id) {
    return trainRepository.findById(id);
  }
}