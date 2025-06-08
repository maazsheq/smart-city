package com.travel.smartcity.model;

import java.time.LocalDate;

public class Booking {
  private int id;
  private int userId;
  private String placeName;
  private LocalDate date;

  public Booking(int id, int userId, String placeName, LocalDate date) {
    this.id = id;
    this.userId = userId;
    this.placeName = placeName;
    this.date = date;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getPlaceName() {
    return placeName;
  }

  public void setPlaceName(String placeName) {
    this.placeName = placeName;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }
}