package com.example.trainservice.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="bookings",
  uniqueConstraints = {@UniqueConstraint(columnNames = {"train_id", "seat_number"})})
public class Booking implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long userId;

  @Column(name="train_id")
  private Long trainId;

  @Column(name="seat_number")
  private int seatNumber;

  private String status;

  public Booking() {}

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public Long getUserId() { return userId; }
  public void setUserId(Long userId) { this.userId = userId; }

  public Long getTrainId() { return trainId; }
  public void setTrainId(Long trainId) { this.trainId = trainId; }

  public int getSeatNumber() { return seatNumber; }
  public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }

  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }
}