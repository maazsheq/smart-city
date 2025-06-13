package com.travel.smartcity.dao;

import com.travel.smartcity.model.UserPersonalDetails;

import java.sql.*;
import java.util.Optional;

public class UserPersonalDetailsDAO {
  private static final String URL = "jdbc:postgresql://localhost:5432/smartcitydb";
  private static final String USER = "postgres";
  private static final String PASS = "root";

  private static final String INSERT_SQL =
          "INSERT INTO user_personal_details (user_id, gender, country, phone, address) "
                  + "VALUES (?, ?, ?, ?, ?)";

  private static final String SELECT_BY_USERID =
          "SELECT user_id, gender, country, phone, address " +
                  "  FROM user_personal_details " +
                  " WHERE user_id = ?";

  private static final String UPDATE_SQL =
          "UPDATE user_personal_details\n" +
                  "   SET gender  = ?,\n" +
                  "       country = ?,\n" +
                  "       phone   = ?,\n" +
                  "       address = ?\n" +
                  " WHERE user_id = ?";


  public boolean insert(UserPersonalDetails d) {
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
      ps.setLong(1, d.getUserId());
      ps.setString(2, d.getGender());
      ps.setString(3, d.getCountry());
      ps.setString(4, d.getPhone());
      ps.setString(5, d.getAddress());
      return ps.executeUpdate() == 1;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public Optional<UserPersonalDetails> findByUserId(int userId) {
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(SELECT_BY_USERID)) {

      ps.setLong(1, userId);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          UserPersonalDetails d = new UserPersonalDetails();
          d.setUserId  ( rs.getInt("user_id") );
          d.setGender  ( rs.getString("gender") );
          d.setCountry ( rs.getString("country") );
          d.setPhone   ( rs.getString("phone") );
          d.setAddress ( rs.getString("address") );
          return Optional.of(d);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

  public boolean update(UserPersonalDetails d) {
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

      ps.setString(1, d.getGender());
      ps.setString(2, d.getCountry());
      ps.setString(3, d.getPhone());
      ps.setString(4, d.getAddress());
      ps.setLong(5, d.getUserId());

      return ps.executeUpdate() == 1;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }

  }
}
