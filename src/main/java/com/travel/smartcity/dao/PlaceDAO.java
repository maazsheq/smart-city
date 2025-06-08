package com.travel.smartcity.dao;

import com.travel.smartcity.model.Place;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaceDAO {
  private static final String URL = "jdbc:postgresql://localhost:5432/smartcitydb";
  private static final String USER = "postgres";
  private static final String PASS = "root";


  /**
   * Insert a new place into the database.
   */
  public boolean create(Place place) {
    String sql = "INSERT INTO places(name, type, address) VALUES (?, ?, ?)";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, place.getName());
      ps.setString(2, place.getType());
      ps.setString(3, place.getAddress());
      return ps.executeUpdate() == 1;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Update an existing place (by id).
   */
  public boolean update(Place place) {
    String sql = "UPDATE places SET name = ?, type = ?, address = ? WHERE id = ?";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, place.getName());
      ps.setString(2, place.getType());
      ps.setString(3, place.getAddress());
      ps.setInt(4, place.getId());
      return ps.executeUpdate() == 1;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Delete a place by id.
   */
  public boolean delete(int id) {
    String sql = "DELETE FROM places WHERE id = ?";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, id);
      return ps.executeUpdate() == 1;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Search places by name or type (case-insensitive partial match).
   */
  public List<Place> searchByNameOrType(String query) {
    List<Place> results = new ArrayList<>();
    String sql = "SELECT id, name, type, address FROM places " +
            "WHERE LOWER(name) LIKE ? OR LOWER(type) LIKE ?";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql)) {
      String like = "%" + query.toLowerCase() + "%";
      ps.setString(1, like);
      ps.setString(2, like);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        results.add(new Place(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("type"),
                rs.getString("address")
        ));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return results;
  }

  /**
   * Find a place by its ID.
   */
  public Place findById(int id) {
    String sql = "SELECT id, name, type, address FROM places WHERE id = ?";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, id);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        return new Place(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("type"),
                rs.getString("address")
        );
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Fetch all places (for display in the admin table).
   */
  public List<Place> findAll() {
    List<Place> list = new ArrayList<>();
    String sql = "SELECT * FROM places ORDER BY name";
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        list.add(new Place(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("type"),
                rs.getString("address")
        ));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }
}