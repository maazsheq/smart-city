package com.travel.smartcity.dao;

import com.travel.smartcity.model.Payment;

import java.math.BigDecimal;
import java.sql.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
  private static final String URL = "jdbc:postgresql://localhost:5432/smartcitydb";
  private static final String USER = "postgres";
  private static final String PASS = "root";


  /**
   * Records a payment row. Returns true if exactly one row was inserted.
   */
  public boolean createPayment(int userId,
                               int bookingId,
                               BigDecimal amount,
                               String method,
                               String status) {

    String sql = ""
            + "INSERT INTO payments "
            + "  (user_id, booking_id, amount, paid_at, method, status) "
            + "VALUES (?, ?, ?, ?, ?, ?)";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setInt(1, userId);
      ps.setInt(2, bookingId);
      ps.setBigDecimal(3, amount);
      ps.setObject(4, OffsetDateTime.now());
      ps.setString(5, method);
      ps.setString(6, status);

      return ps.executeUpdate() == 1;
    } catch (SQLException ex) {
      ex.printStackTrace();
      return false;
    }
  }
  /**
   * Process and record a payment.
   */
//  public boolean create(Payment payment) {
//    String sql = "INSERT INTO payments(user_id, payee_id, amount, payment_date) VALUES(?,?,?,?)";
//    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
//         PreparedStatement ps = conn.prepareStatement(sql)) {
//      ps.setInt(1, payment.getUserId());
//      ps.setInt(2, payment.getPayeeId());
//      ps.setBigDecimal(3, payment.getAmount());
//      ps.setTimestamp(4, Timestamp.valueOf(payment.getDateTime()));
//      return ps.executeUpdate() == 1;
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//    return false;
//  }

  /**
   * Retrieve payments for a user.
   */
//  public List<Payment> findByUserId(int userId) {
//    List<Payment> list = new ArrayList<>();
//    String sql = "SELECT id, user_id, payee_id, amount, payment_date FROM payments WHERE user_id = ?";
//    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
//         PreparedStatement ps = conn.prepareStatement(sql)) {
//      ps.setInt(1, userId);
//      ResultSet rs = ps.executeQuery();
//      while (rs.next()) {
//        list.add(new Payment(
//                rs.getInt("id"),
//                rs.getInt("user_id"),
//                rs.getInt("payee_id"),
//                rs.getBigDecimal("amount"),
//                rs.getTimestamp("payment_date").toLocalDateTime()
//        ));
//      }
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//    return list;
//  }
}