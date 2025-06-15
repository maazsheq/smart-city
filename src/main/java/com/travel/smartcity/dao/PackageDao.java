package com.travel.smartcity.dao;

import com.travel.smartcity.model.Package;    // your model

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PackageDao {
  // your JDBC connection info
  private static final String URL  = "jdbc:postgresql://localhost:5432/smartcitydb";
  private static final String USER = "postgres";
  private static final String PASS = "root";

  public BigDecimal getPriceById(int packageId) {
    String sql = "SELECT price FROM packages WHERE id = ?";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setInt(1, packageId);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          String priceStr = rs.getString("price");
          if (priceStr != null && !priceStr.isBlank()) {
            // Remove any non-numeric characters except dot/decimal point
            String normalized = priceStr
                    .replaceAll("[$,]", "")    // strip $ and commas
                    .trim();
            return new BigDecimal(normalized);
          }
        }
      }
    } catch (SQLException | NumberFormatException ex) {
      ex.printStackTrace();
    }
    return BigDecimal.ZERO;
  }


//  /**
//   * Inserts a new Package into the DB.
//   * On success, sets the generated ID back onto the model and returns true.
//   */
  public boolean create(Package pkg) {
    String sql = "INSERT INTO packages (place_id, details, price) VALUES (?, ?, ?)";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      ps.setInt(   1, pkg.getPlaceId());
      ps.setString(2, pkg.getDetails());
      ps.setString(3, pkg.getPrice());

      int rows = ps.executeUpdate();
      if (rows == 1) {
        try (ResultSet keys = ps.getGeneratedKeys()) {
          if (keys.next()) {
            pkg.setId(keys.getInt(1));
          }
        }
        return true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Returns all Package rows for the given placeId.
   */
  public List<Package> listByPlaceId(int placeId) {
    List<Package> list = new ArrayList<>();
    String sql =
            "SELECT * " +
                    "FROM packages " +
                    "WHERE place_id = ? " +
                    "ORDER BY id";

    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setInt(1, placeId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          list.add(new Package(
                  rs.getInt("id"),
                  rs.getInt("place_id"),
                  rs.getString("details"),
                  rs.getString("price")
          ));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  /**
   * Updates an existing Package. Returns true if exactly one row was updated.
   */
  public boolean update(Package pkg) {
    String sql =
            "UPDATE packages " +
                    "SET place_id = ?, details = ?, price = ? " +
                    "WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setInt(   1, pkg.getPlaceId());
      ps.setString(2, pkg.getDetails());
      ps.setString(3, pkg.getPrice());
      ps.setInt(   4, pkg.getId());

      return ps.executeUpdate() == 1;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Deletes the Package with the given id. Returns true if exactly one row was deleted.
   */
  public boolean delete(int packageId) {
    String sql = "DELETE FROM packages WHERE id = ?";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setInt(1, packageId);
      return ps.executeUpdate() == 1;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }
}
