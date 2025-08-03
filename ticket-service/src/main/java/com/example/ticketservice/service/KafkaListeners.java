package com.example.ticketservice.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.ticketservice.model.Booking;
import com.example.ticketservice.repository.BookingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaListeners {

    private final BookingRepository bookingRepository;

  public KafkaListeners(BookingRepository bookingRepository) {
    this.bookingRepository = bookingRepository;
  }

    @KafkaListener(topics = "train-booking-events", groupId = "ticket-service-group")
    public void handleBookingEvent(String message) {
        System.out.println("Ticket Service received booking event: " + message);
        // TODO: Parse message and create ticket or update internal state accordingly
        try {
        ObjectMapper mapper = new ObjectMapper();
        Booking booking = mapper.readValue(message, Booking.class);
        bookingRepository.save(booking);
        System.out.println("Booking " + booking + " saved");
        }catch (Exception e) {
            System.out.println("Error parsing/saving booking: ");
            System.out.println(e);
        }
    }
}