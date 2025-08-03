package com.example.trainservice.repository;

import com.example.trainservice.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
  int countByTrainId(Long trainId);
  boolean existsByTrainIdAndSeatNumber(Long trainId, int seatNumber);
  List<Booking> findByUserId(Long userId);
}