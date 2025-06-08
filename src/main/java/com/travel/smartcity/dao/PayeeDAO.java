package com.travel.smartcity.dao;

import com.travel.smartcity.model.Payee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PayeeDAO {
  private static final String URL = "jdbc:postgresql://localhost:5432/smartcitydb";
  private static final String USER = "postgres";
  private static final String PASS = "root";

  /**
   * Create a new payee record.
   */
  public boolean create(Payee payee) {
    String sql = "INSERT INTO payees(user_id, name, account_number) VALUES(?,?,?)";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, payee.getUserId());
      ps.setString(2, payee.getName());
      ps.setString(3, payee.getAccountNumber());
      return ps.executeUpdate() == 1;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Find a payee by its ID.
   */
  public Payee findById(int id) {
    String sql = "SELECT id, user_id, name, account_number FROM payees WHERE id = ?";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, id);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        return new Payee(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getString("name"),
                rs.getString("account_number")
        );
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * List all payees for a user.
   */
  public List<Payee> findAllByUserId(int userId) {
    List<Payee> list = new ArrayList<>();
    String sql = "SELECT id, user_id, name, account_number FROM payees WHERE user_id = ?";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, userId);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        list.add(new Payee(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getString("name"),
                rs.getString("account_number")
        ));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  public List<Payee> findAll() {
    List<Payee> list = new ArrayList<>();
    String sql = "SELECT id, user_id, name, account_number FROM payees";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        list.add(new Payee(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getString("name"),
                rs.getString("account_number")
        ));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }
}