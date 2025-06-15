package com.travel.smartcity.service;


import com.travel.smartcity.model.User;
import com.travel.smartcity.dao.UserDAO;

public class AuthService {
  private UserDAO userDAO = new UserDAO();

//  public boolean authenticate(String username, String password) {
//    User user = userDAO.findByUsername(username);
//    return user != null && user.getPassword().equals(password);
//  }

//  public boolean signup(User user) {
//    return userDAO.create(user);
//  }

  public boolean signup(User user) {
    int count = userDAO.countUsers();
    // First user ever → make them an admin
    user.setAdmin(count == 0);
    return userDAO.create(user);
  }

  public boolean updateProfile(User user) {
    return userDAO.update(user);
  }

  public boolean loginAsAdmin(String username, String password) {
    User user = login(username, password);
    return user != null && user.isAdmin();
  }

  public User login(String username, String password) {
    User user = userDAO.findByUsername(username);
    if (user != null && user.getPassword().equals(password)) {
      return user;  // contains isAdmin
    }
    return null;
  }

  public boolean isUsernameTaken(String username) {
    return userDAO.findByUsername(username) != null;
  }

  public boolean isEmailTaken(String email) {
    return userDAO.findByEmail(email) != null;
  }

  // changePassword(User user, String newPass)...
}