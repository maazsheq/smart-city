package com.travel.smartcity.controller;

import com.travel.smartcity.model.User;
import com.travel.smartcity.util.SceneRouter;
import com.travel.smartcity.util.session.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;


public class RootLayoutController {

  @FXML private StackPane centerPane;

  @FXML
  private void initialize() {
    // When the app first shows RootLayout, immediately load the Dashboard into centerPane:
    User current = Session.getCurrentUser();
    if (current != null && current.isAdmin()) {
      loadIntoCenter("/com/travel/smartcity/admin-dashboard-view.fxml");
    } else {
      loadIntoCenter("/com/travel/smartcity/dashboard-view.fxml");
    }
  }

  @FXML
  private void showDashboard() {
    if (Session.getCurrentUser().isAdmin()) {
      loadIntoCenter("/com/travel/smartcity/admin-dashboard-view.fxml");
    } else {
      loadIntoCenter("/com/travel/smartcity/dashboard-view.fxml");
    }
  }

  @FXML
  private void showProfile() {
    loadIntoCenter("/com/travel/smartcity/profile-view.fxml");
  }

  @FXML
  private void handleLogout() {
    try {
      Stage stage = (Stage) centerPane.getScene().getWindow();
      // Go back to login (this replaces the entire scene, because we want to leave RootLayout entirely)
      SceneRouter.switchTo(
              stage,
              "/com/travel/smartcity/login-view.fxml",
              "Smart City Traveler - Login"
      );
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Helper method: load the given FXML resource into the centerPane.
   * This DOES NOT replace the top toolbar or the entire scene—only the center content.
   */
  private void loadIntoCenter(String fxmlPath) {
    try {
      Node node = FXMLLoader.load(getClass().getResource(fxmlPath));
      centerPane.getChildren().setAll(node);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void handleEditProfile() {
    loadIntoCenter("/com/travel/smartcity/profile-view.fxml");
  }
}

