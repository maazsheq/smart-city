package com.travel.smartcity.controller;

import com.travel.smartcity.model.User;
import com.travel.smartcity.service.AuthService;
import com.travel.smartcity.util.SceneRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignupController {
  @FXML private TextField usernameField;
  @FXML private TextField emailField;
  @FXML private PasswordField passwordField;

  private AuthService authService = new AuthService();

  @FXML
  private void handleSignup() throws Exception {
    String username = usernameField.getText().trim();
    String email    = emailField.getText().trim();
    String pass     = passwordField.getText();

    if (username.isEmpty() || email.isEmpty() || pass.isEmpty()) {
      new Alert(Alert.AlertType.WARNING, "All fields are required.").showAndWait();
      return;
    }

    User newUser = new User(username, pass, email);
    boolean created = authService.signup(newUser);
    if (created) {
      new Alert(Alert.AlertType.INFORMATION, "Sign-up successful! Please log in.").showAndWait();
      // navigate back to Login view
      Stage stage = (Stage) usernameField.getScene().getWindow();
      SceneRouter.switchTo(stage, "/com/travel/smartcity/login-view.fxml", "Smart City Traveler - Login");
    } else {
      new Alert(Alert.AlertType.ERROR, "Sign-up failed. Try a different username.").showAndWait();
    }
  }

  @FXML
  private void handleBack() throws Exception {
    Stage stage = (Stage) usernameField.getScene().getWindow();
    SceneRouter.switchTo(stage, "/com/travel/smartcity/login-view.fxml", "Smart City Traveler - Login");
  }
//  @FXML
//  private void handleSubmitButtonAction() throws Exception {
//    String username = usernameField.getText().trim();
//    String email    = emailField.getText().trim();
//    String pass     = passwordField.getText();
//
//    if (username.isEmpty() || email.isEmpty() || pass.isEmpty()) {
//      new Alert(Alert.AlertType.WARNING, "All fields are required.").showAndWait();
//      return;
//    }
//
//    User newUser = new User(username, pass, email);
//    boolean created = authService.signup(newUser);
//    if (created) {
//      new Alert(Alert.AlertType.INFORMATION, "Sign-up successful! Please log in.").showAndWait();
//      // navigate back to Login view
//      Stage stage = (Stage) usernameField.getScene().getWindow();
//      SceneRouter.switchTo(stage, "/com/travel/smartcity/login-view.fxml", "Smart City Traveler - Login");
//    } else {
//      new Alert(Alert.AlertType.ERROR, "Sign-up failed. Try a different username.").showAndWait();
//    }
//  }

  @FXML
  private void handleSubmitButtonAction() throws Exception {
    String username = usernameField.getText().trim();
    String email    = emailField.getText().trim();
    String pass     = passwordField.getText();

    if (username.isEmpty() || email.isEmpty() || pass.isEmpty()) {
      new Alert(Alert.AlertType.WARNING, "All fields are required.").showAndWait();
      return;
    }
    if (!username.matches("^[A-Za-z]{1,12}$")) {
      new Alert(Alert.AlertType.WARNING,
              "Username must be alphabetic and up to 12 characters.")
              .showAndWait();
      return;
    }
    if (!email.matches("^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,}$")) {
      new Alert(Alert.AlertType.WARNING,
              "Please enter a valid email address.")
              .showAndWait();
      return;
    }
    if (!pass.matches("^[A-Za-z0-9]{3,10}$")) {
      new Alert(Alert.AlertType.WARNING,
              "Password must be 3–10 characters long and contain only letters and digits.")
              .showAndWait();
      return;
    }

    // **duplicate checks**
    if (authService.isUsernameTaken(username)) {
      new Alert(Alert.AlertType.WARNING,
              "That username is already taken, please choose another.")
              .showAndWait();
      return;
    }
    if (authService.isEmailTaken(email)) {
      new Alert(Alert.AlertType.WARNING,
              "That email is already registered, please log in or use another.")
              .showAndWait();
      return;
    }

    // finally attempt the insert
    User newUser = new User(username, pass, email);
    boolean created = authService.signup(newUser);
    if (created) {
      new Alert(Alert.AlertType.INFORMATION,
              "Sign-up successful! Please log in.")
              .showAndWait();
      Stage stage = (Stage) usernameField.getScene().getWindow();
      SceneRouter.switchTo(stage,
              "/com/travel/smartcity/login-view.fxml",
              "Smart City Traveler - Login");
    } else {
      new Alert(Alert.AlertType.ERROR,
              "Sign-up failed. Please try again later.")
              .showAndWait();
    }
  }


}
