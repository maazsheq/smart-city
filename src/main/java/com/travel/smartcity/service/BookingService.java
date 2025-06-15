package com.travel.smartcity.service;


import com.travel.smartcity.dao.BookingDAO;
import com.travel.smartcity.model.Booking;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;

public class BookingService {
  private BookingDAO bookingDAO = new BookingDAO();

//  public boolean book(Booking booking) {
//    return bookingDAO.create(booking);
//  }

//  public List<Booking> getBookingsForUser(int userId) {
//    return bookingDAO.findByUserId(userId);
//  }

  public int createBooking(Booking booking) {
    return bookingDAO.createBooking(booking);
  }

  public boolean bookingExists(int userId, int packageId, LocalDate date) {
    return bookingDAO.existsBooking(userId, packageId, date);
  }
}