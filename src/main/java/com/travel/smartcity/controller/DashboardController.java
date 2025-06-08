package com.travel.smartcity.controller;

import com.travel.smartcity.model.User;
import com.travel.smartcity.util.SceneRouter;
import com.travel.smartcity.util.session.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DashboardController {

  @FXML
  private BorderPane rootPane;
  // Admin menu and its “Manage Places” item
  @FXML private Menu adminMenu;
  @FXML private MenuItem managePlacesBtn;


  @FXML
  private void initialize() {
    User current = Session.getCurrentUser();
    boolean isAdmin = (current != null && current.isAdmin());

    // Show or hide the entire “Admin” menu based on role
    adminMenu.setVisible(isAdmin);
  }

  @FXML
  private void handleLogout() throws Exception {
    // Optionally clear session/context here
//    Stage stage = (Stage) /* any node */.getScene().getWindow();
    Stage stage = (Stage) rootPane.getScene().getWindow();
//    SceneRouter.switchTo(stage, "/fxml/login-view.fxml", "Smart City Traveler - Login");
    Session.clear();
    SceneRouter.switchTo(stage, "/com/travel/smartcity/login-view.fxml", "Smart City Traveler - Login");
  }

  @FXML
  private void handleAbout() {
    new Alert(Alert.AlertType.INFORMATION,
            "Smart City Traveler v1.0\nDeveloped by SmartCity Inc.").showAndWait();
  }

  @FXML
  private void goToProfile() throws Exception {
    Stage stage = (Stage) rootPane.getScene().getWindow();
//    SceneRouter.switchTo(stage, "/com/travel/smartcity/profile-view.fxml", "Profile");
    SceneRouter.switchTo(stage, "/com/travel/smartcity/profile-view.fxml", "Profile");

  }

  @FXML
  private void goToSearch() throws Exception {
    Stage stage = (Stage) rootPane.getScene().getWindow();
//    SceneRouter.switchTo(stage, "/com/travel/smartcity/search-view.fxml", "Search Places");
    SceneRouter.switchTo(stage, "/com/travel/smartcity/search-view.fxml", "Search Places");

  }

  @FXML
  private void goToBookings() throws Exception {
    Stage stage = (Stage) rootPane.getScene().getWindow();
//    SceneRouter.switchTo(stage, "/com/travel/smartcity/booking-view.fxml", "My Bookings");
    SceneRouter.switchTo(stage, "/com/travel/smartcity/booking-view.fxml", "My Bookings");

  }

  @FXML
  private void goToPayments() throws Exception {
    Stage stage = (Stage) rootPane.getScene().getWindow();
//    SceneRouter.switchTo(stage, "/com/travel/smartcity/payment-view.fxml", "Payments");
    SceneRouter.switchTo(stage, "/com/travel/smartcity/payment-view.fxml", "Payments");

  }

  @FXML
  private void goToManagePlaces() throws Exception {
    // Only shown if user is admin
    Stage stage = (Stage) rootPane.getScene().getWindow();
    SceneRouter.switchTo(stage, "/com/travel/smartcity/place-management-view.fxml", "Manage Places");
  }
}
