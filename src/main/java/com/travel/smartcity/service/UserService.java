package com.travel.smartcity.service;

import com.travel.smartcity.dao.UserDAO;
import com.travel.smartcity.model.User;

import java.util.List;

public class UserService {
  private final UserDAO userDAO = new UserDAO();

  public boolean createUser(User u) {
    return userDAO.create(u);
  }

  public boolean updateUser(User u) {
    return userDAO.update(u);
  }

  public boolean deleteUser(int userId) {
    return userDAO.delete(userId);
  }

  public List<User> listAllUsers() {
    return userDAO.findAll();
  }
}
