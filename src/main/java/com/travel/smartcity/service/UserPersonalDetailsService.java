package com.travel.smartcity.service;


import com.travel.smartcity.model.UserPersonalDetails;
import com.travel.smartcity.dao.UserPersonalDetailsDAO;

import java.util.Optional;

public class UserPersonalDetailsService {
  private final UserPersonalDetailsDAO dao = new UserPersonalDetailsDAO();

  public boolean save(UserPersonalDetails details) {
    return dao.insert(details);
  }

  public Optional<UserPersonalDetails> findByUserId(int userId) {
    return dao.findByUserId(userId);
  }

  public boolean update(UserPersonalDetails details) {
    return dao.update(details);
  }

  public boolean saveOrUpdate(UserPersonalDetails details) {
    Optional<UserPersonalDetails> existing = dao.findByUserId(details.getUserId());
    if (existing.isPresent()) {
      return dao.update(details);
    } else {
      return dao.insert(details);
    }
  }
}
