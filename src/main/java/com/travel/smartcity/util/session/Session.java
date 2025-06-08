package com.travel.smartcity.util.session;

import com.travel.smartcity.model.User;

public class Session {
  private static User currentUser;

  public static void setCurrentUser(User user) {
    currentUser = user;
  }

  public static User getCurrentUser() {
    return currentUser;
  }

  public static void clear() {
    currentUser = null;
  }
}