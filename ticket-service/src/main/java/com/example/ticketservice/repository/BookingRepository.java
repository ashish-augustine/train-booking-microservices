package com.example.ticketservice.repository;

import com.example.ticketservice.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
  List<Booking> findByUserId(Long userId);
}