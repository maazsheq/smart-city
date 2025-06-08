package com.travel.smartcity.service;


import com.travel.smartcity.dao.BookingDAO;
import com.travel.smartcity.model.Booking;

import java.util.List;

public class BookingService {
  private BookingDAO bookingDAO = new BookingDAO();

  public boolean book(Booking booking) {
    return bookingDAO.create(booking);
  }

  public List<Booking> getBookingsForUser(int userId) {
    return bookingDAO.findByUserId(userId);
  }
}