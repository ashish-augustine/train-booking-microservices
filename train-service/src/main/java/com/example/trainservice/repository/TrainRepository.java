package com.example.trainservice.repository;

import com.example.trainservice.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainRepository extends JpaRepository<Train, Long> {}