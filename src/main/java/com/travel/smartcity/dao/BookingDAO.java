package com.travel.smartcity.dao;


import com.travel.smartcity.model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
  private static final String URL = "jdbc:postgresql://localhost:5432/smartcitydb";
  private static final String USER = "postgres";
  private static final String PASS = "root";

  /**
   * Create a new booking record.
   */
  public boolean create(Booking booking) {
    String sql = "INSERT INTO bookings(user_id, place_name, booking_date) VALUES(?,?,?)";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, booking.getUserId());
      ps.setString(2, booking.getPlaceName());
      ps.setDate(3, Date.valueOf(booking.getDate()));
      return ps.executeUpdate() == 1;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Find bookings by user ID.
   */
  public List<Booking> findByUserId(int userId) {
    List<Booking> list = new ArrayList<>();
    String sql = "SELECT id, user_id, place_name, booking_date FROM bookings WHERE user_id = ?";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, userId);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        list.add(new Booking(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getString("place_name"),
                rs.getDate("booking_date").toLocalDate()
        ));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }
}