package com.travel.smartcity.dao;


import com.travel.smartcity.model.Booking;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
  private static final String URL = "jdbc:postgresql://localhost:5432/smartcitydb";
  private static final String USER = "postgres";
  private static final String PASS = "root";

  /**
   * Create a new booking record.
   */
  public int createBooking(Booking booking) {
    String sql = "INSERT INTO bookings (user_id, package_id, booking_date) VALUES (?, ?, ?)";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      ps.setInt(1, booking.getUserId());
      ps.setInt(2, booking.getPackageId());
      ps.setDate(3, Date.valueOf(booking.getBookingDate()));

      int rows = ps.executeUpdate();
      if (rows == 1) {
        try (ResultSet keys = ps.getGeneratedKeys()) {
          if (keys.next()) {
            return keys.getInt(1);
          }
        }
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return -1;
  }

  public boolean existsBooking(int userId, int packageId, LocalDate bookingDate) {
    String sql = ""
            + "SELECT 1 "
            + "FROM bookings "
            + "WHERE user_id    = ? "
            + "  AND package_id = ? "
            + "  AND booking_date = ?";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setInt(1, userId);
      ps.setInt(2, packageId);
      ps.setDate(3, Date.valueOf(bookingDate));

      try (ResultSet rs = ps.executeQuery()) {
        return rs.next();  // any row → exists
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
      // On error, be conservative: say it exists to prevent duplicates
      return true;
    }
  }
    /**
     * Find bookings by user ID.
     */
//  public List<Booking> findByUserId(int userId) {
//    List<Booking> list = new ArrayList<>();
//    String sql = "SELECT id, user_id, place_name, booking_date FROM bookings WHERE user_id = ?";
//    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
//         PreparedStatement ps = conn.prepareStatement(sql)) {
//      ps.setInt(1, userId);
//      ResultSet rs = ps.executeQuery();
//      while (rs.next()) {
//        list.add(new Booking(
//                rs.getInt("id"),
//                rs.getInt("user_id"),
//                rs.getString("place_name"),
//                rs.getDate("booking_date").toLocalDate()
//        ));
//      }
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//    return list;
//  }


  }