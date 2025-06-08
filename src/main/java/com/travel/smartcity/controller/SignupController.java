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
  @FXML
  private void handleSubmitButtonAction() throws Exception {
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
}
