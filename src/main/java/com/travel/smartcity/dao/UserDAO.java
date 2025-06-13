package com.travel.smartcity.dao;


import com.travel.smartcity.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
  private static final String URL = "jdbc:postgresql://localhost:5432/smartcitydb";
  private static final String USER = "postgres";
  private static final String PASS = "root";

  public User findByUsername(String username) {
    String sql = "SELECT * FROM users WHERE username = ?";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, username);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        int id = rs.getInt("id");
        String user = rs.getString("username");
        String pass = rs.getString("password");
        String email = rs.getString("email");
        boolean isAdmin = rs.getBoolean("is_admin");
        return new User(id, user, pass, email, isAdmin);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public boolean create(User user) {
    String sql = "INSERT INTO users (username, password, email, is_admin) VALUES (?, ?, ?, ?)";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      ps.setString(1, user.getUsername());
      ps.setString(2, user.getPassword());
      ps.setString(3, user.getEmail());
      ps.setBoolean(4, user.isAdmin());  // usually false at signup
      int rows = ps.executeUpdate();
      if (rows == 1) {
        ResultSet keys = ps.getGeneratedKeys();
        if (keys.next()) {
          user.setId(keys.getInt(1));
        }
        return true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  //  public boolean update(User user) {
//    String sql = "UPDATE users SET email = ?, password = ?, is_admin = ? WHERE id = ?";
//    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
//         PreparedStatement ps = conn.prepareStatement(sql)) {
//
//      ps.setString(1, user.getEmail());
//      ps.setString(2, user.getPassword());
//      ps.setBoolean(3, user.isAdmin());
//      ps.setInt(4, user.getId());
//      return ps.executeUpdate() == 1;
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//    return false;
//  }
  public boolean update(User user) {
    // Update username, password, and is_admin for the row whose email matches
    String sql = "UPDATE users " +
            "SET username = ?, password = ?, is_admin = ? " +
            "WHERE email = ?";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql)) {

      // 1) username
      ps.setString(1, user.getUsername());
      // 2) password
      ps.setString(2, user.getPassword());
      // 3) is_admin
      ps.setBoolean(3, user.isAdmin());
      // 4) email (used in WHERE)
      ps.setString(4, user.getEmail());

      return ps.executeUpdate() == 1;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public int countUsers() {
    String sql = "SELECT COUNT(*) FROM users";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         Statement st = conn.createStatement();
         ResultSet rs = st.executeQuery(sql)) {
      if (rs.next()) {
        return rs.getInt(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  // Return all users (for the table)
  public List<User> findAll() {
    List<User> list = new ArrayList<>();
    String sql = "SELECT id, username, password, email, is_admin FROM users ORDER BY username";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        User u = new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getBoolean("is_admin")
        );
        list.add(u);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  // Delete a user by id
  public boolean delete(int id) {
    String sql = "DELETE FROM users WHERE id = ?";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, id);
      return ps.executeUpdate() == 1;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

}