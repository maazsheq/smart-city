package com.travel.smartcity.dao;

import com.travel.smartcity.model.PayeeCardDetails;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PayeeDAO {
  private static final String URL = "jdbc:postgresql://localhost:5432/smartcitydb";
  private static final String USER = "postgres";
  private static final String PASS = "root";


  public boolean upsertDefaultPayee(PayeeCardDetails payeeCardDetails) {
    String sql =
            "INSERT INTO payees(user_id, name, account_number, expiry, cvv) " +
                    "VALUES (?, ?, ?, ?, ?) " +
                    "ON CONFLICT(user_id, name) DO UPDATE " +
                    "  SET account_number = EXCLUDED.account_number, " +
                    "      expiry         = EXCLUDED.expiry, " +
                    "      cvv            = EXCLUDED.cvv";

    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, payeeCardDetails.getUserId());
      ps.setString(2, payeeCardDetails.getName());
      ps.setString(3, payeeCardDetails.getAccountNumber());
      ps.setString(4, payeeCardDetails.getExpiry());
      ps.setString(5, payeeCardDetails.getCvv());
      return ps.executeUpdate() >= 1;
    } catch (SQLException ex) {
      ex.printStackTrace();
      return false;
    }
  }




  public boolean create(PayeeCardDetails payeeCardDetails) {
    String sql =
            "INSERT INTO payees(user_id, name, account_number, expiry, cvv) " +
                    "VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      ps.setInt(1, payeeCardDetails.getUserId());
      ps.setString(2, payeeCardDetails.getName());
      ps.setString(3, payeeCardDetails.getAccountNumber());
      ps.setString(4, payeeCardDetails.getExpiry());
      ps.setString(5, payeeCardDetails.getCvv());

      int rows = ps.executeUpdate();
      if (rows == 1) {
        try (ResultSet rs = ps.getGeneratedKeys()) {
          if (rs.next()) payeeCardDetails.setId(rs.getInt(1));
        }
        return true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Find a payee by its ID.
   */
  public PayeeCardDetails findById(int id) {
    String sql =
            "SELECT id, user_id, name, account_number, expiry, cvv " +
                    "FROM payees WHERE id = ?";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return new PayeeCardDetails(
                  rs.getInt("id"),
                  rs.getInt("user_id"),
                  rs.getString("name"),
                  rs.getString("account_number"),
                  rs.getString("expiry"),
                  rs.getString("cvv")
          );
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * List all payees for a given user.
   */
  public List<PayeeCardDetails> findAllByUserId(int userId) {
    List<PayeeCardDetails> list = new ArrayList<>();
    String sql =
            "SELECT id, user_id, name, account_number, expiry, cvv " +
                    "FROM payees WHERE user_id = ? ORDER BY name";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, userId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          list.add(new PayeeCardDetails(
                  rs.getInt("id"),
                  rs.getInt("user_id"),
                  rs.getString("name"),
                  rs.getString("account_number"),
                  rs.getString("expiry"),
                  rs.getString("cvv")
          ));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  public PayeeCardDetails findDefaultByUser(int userId) {
    String sql =
            "SELECT id, user_id, name, account_number, expiry, cvv\n" +
                    "  FROM payees\n" +
                    " WHERE user_id = ?\n" +
                    " ORDER BY id DESC      -- newest first\n" +
                    " LIMIT 1";            // just one default
    try (Connection c = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, userId);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return new PayeeCardDetails(
                  rs.getInt("id"),
                  rs.getInt("user_id"),
                  rs.getString("name"),
                  rs.getString("account_number"),
                  rs.getString("expiry"),
                  rs.getString("cvv")
          );
        }
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return null;
  }

}