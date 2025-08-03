package com.example.trainservice.service;

import com.example.trainservice.model.Booking;
import com.example.trainservice.model.Train;
import com.example.trainservice.repository.BookingRepository;
import com.example.trainservice.repository.TrainRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class BookingService {

  @Autowired
  private BookingRepository bookingRepository;

  @Autowired
  private TrainRepository trainRepository;

  @Autowired
  private StringRedisTemplate redisTemplate;

  @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

  private static final String REDIS_LOCK_KEY_PREFIX = "train_booking_lock:";
    public static final String BOOKING_TOPIC = "train-booking-events";


  public Booking bookSeat(Long userId, Long trainId) throws Exception {
    String lockKey = REDIS_LOCK_KEY_PREFIX + trainId;
    Boolean success = redisTemplate.opsForValue().setIfAbsent(lockKey, String.valueOf(userId), Duration.ofSeconds(10));
    if (success == null || !success) {
      throw new IllegalStateException("Another booking is in progress. Try again later.");
    }

    try {
      Train train = trainRepository.findById(trainId).orElseThrow(() -> new Exception("Train not found"));
      int bookedSeats = bookingRepository.countByTrainId(trainId);
      if (bookedSeats >= train.getTotalSeats()) {
        throw new Exception("No seats available");
      }
      int seatNumber = bookedSeats + 1;

      if (bookingRepository.existsByTrainIdAndSeatNumber(trainId, seatNumber)) {
        throw new Exception("Seat already booked, please retry");
      }

      Booking booking = new Booking();
      booking.setUserId(userId);
      booking.setTrainId(trainId);
      booking.setSeatNumber(seatNumber);
      booking.setStatus("CONFIRMED");

      booking = bookingRepository.save(booking);

      // Publish Kafka event
      ObjectMapper mapper = new ObjectMapper();
      String bookingEventMsg = mapper.writeValueAsString(booking);
      kafkaTemplate.send(BOOKING_TOPIC, bookingEventMsg);

      return booking;
    } finally {
      redisTemplate.delete(lockKey);
    }
  }

  public List<Booking> getBookingsByUser(Long userId) {
    return bookingRepository.findByUserId(userId);
  }
}