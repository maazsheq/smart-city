package com.travel.smartcity.controller;

import com.travel.smartcity.util.SceneRouter;

import com.travel.smartcity.model.User;
import com.travel.smartcity.util.session.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AdminDashboardController {

  @FXML private StackPane contentPane;
  @FXML private BorderPane adminDashboardPane;

  @FXML
  private void initialize() {
    // Optionally, load a default child (e.g. Manage Places) on startup:
//    try {
//      goToPlaceManagement();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
  }

  @FXML
  private void handleLogout() throws Exception {
    Session.clear();
    Stage stage = (Stage) adminDashboardPane.getScene().getWindow();
    SceneRouter.switchTo(stage, "/com/travel/smartcity/login-view.fxml", "Smart City Traveler - Login");
  }

  @FXML
  private void handleAbout() {
    new Alert(Alert.AlertType.INFORMATION,
            "Smart City Traveler v1.0\nAdmin Panel"
    ).showAndWait();
  }

  @FXML
  private void goToPlaceManagement() throws IOException {
    // Load the Place Management UI into contentPane
    Node node = FXMLLoader.load(
            Objects.requireNonNull(
                    getClass().getResource("/com/travel/smartcity/place-management-view.fxml")
            )
    );
    contentPane.getChildren().setAll(node);
  }

  @FXML
  private void goToUserManagement() throws IOException {
    // Load the User Management UI into contentPane
    Node node = FXMLLoader.load(
            Objects.requireNonNull(
                    getClass().getResource("/com/travel/smartcity/user-management-view.fxml")
            )
    );
    contentPane.getChildren().setAll(node);
  }

  @FXML
  private void handleUsers() throws IOException {

  }

  @FXML
  private void handleHotels() throws IOException {

  }

  @FXML
  private void handleLibraries() throws IOException {

  }

  @FXML
  private void handleParks() throws IOException {

  }
}
