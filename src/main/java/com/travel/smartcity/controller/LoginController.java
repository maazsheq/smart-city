package com.travel.smartcity.controller;

import com.travel.smartcity.model.User;
import com.travel.smartcity.service.AuthService;
import com.travel.smartcity.util.SceneRouter;
import com.travel.smartcity.util.session.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LoginController {
  @FXML private TextField usernameField;
  @FXML private PasswordField passwordField;

  private final AuthService authService = new AuthService();

  @FXML
  private void handleLogin() throws Exception {
    String user = usernameField.getText().trim();
    String pass = passwordField.getText();

    User loggedIn = authService.login(user, pass);
    if (loggedIn != null) {
      Session.setCurrentUser(loggedIn);

      // Get the current Stage
      Stage stage = (Stage) usernameField.getScene().getWindow();

      // Now switch the entire scene to RootLayout,
      // which has the toolbar (Dashboard/Profile/Logout).
      if (loggedIn.isAdmin()) {
        SceneRouter.switchTo(
                stage,
                "/com/travel/smartcity/admin-dashboard-view.fxml",
                "Admin Dashboard - Smart City Traveler"
        );
      } else {
        SceneRouter.switchTo(
                stage,
                "/com/travel/smartcity/dashboard-view.fxml",
                "User Dashboard - Smart City Traveler"
        );
      }

    } else {
      new Alert(Alert.AlertType.ERROR, "Invalid credentials.").showAndWait();
    }
  }

  @FXML
  private void handleSignup() throws Exception {
    // Replace the entire scene with signup view
    Stage stage = (Stage) usernameField.getScene().getWindow();
    SceneRouter.switchTo(
            stage,
            "/com/travel/smartcity/signup-view.fxml",
            "Smart City Traveler - Sign Up"
    );
  }
}
