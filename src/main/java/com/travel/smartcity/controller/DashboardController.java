package com.travel.smartcity.controller;

import com.travel.smartcity.model.User;
import com.travel.smartcity.util.SceneRouter;
import com.travel.smartcity.util.session.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

  @FXML
  private BorderPane rootPane;
  // Admin menu and its “Manage Places” item
  @FXML private Menu adminMenu;
  @FXML private MenuItem managePlacesBtn;
  @FXML private MenuItem usernameItem;
  @FXML private MenuItem emailItem;
  @FXML private StackPane centerPane;
  @FXML private StackPane centerStackPane;

  @FXML
  private void initialize() {


    User current = Session.getCurrentUser();
    boolean isAdmin = (current != null && current.isAdmin());
    if (current != null) {
      usernameItem.setText("Username: " + current.getUsername());
      emailItem   .setText("Email: "    + current.getEmail());
    } else {
      // hide it if somehow no one is in session
      usernameItem.setVisible(false);
      emailItem   .setVisible(false);
    }
    // Show or hide the entire “Admin” menu based on role
//    adminMenu.setVisible(isAdmin);
  }

  @FXML
  private void handleLogout() throws Exception {
    // Optionally clear session/context here
//    Stage stage = (Stage) /* any node */.getScene().getWindow();
    Stage stage = (Stage) rootPane.getScene().getWindow();
//    SceneRouter.switchTo(stage, "/fxml/login-view.fxml", "Smart City Traveler - Login");
    Session.clear();
    SceneRouter.switchToLogin(stage, "/com/travel/smartcity/login-view.fxml", "Smart City Traveler - Login");
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


  // -------------
//  @FXML
//  private void goToAddPersonal() {
//    loadView("personal-details-view.fxml");
//  }


// inside DashboardController (or wherever you trigger “Add”)

  @FXML
  public void goToAddPersonal(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass()
            .getResource("/com/travel/smartcity/personal-details-dialog.fxml"));
    Parent root = loader.load();

    Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.initOwner(((Node)event.getSource()).getScene().getWindow());
    dialog.setTitle("Add Personal Details");
    dialog.setScene(new Scene(root));
    dialog.setResizable(false);
    dialog.showAndWait();
  }


  @FXML
  private void updateProfile(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass()
            .getResource("/com/travel/smartcity/profile-view.fxml"));
    Parent root = loader.load();

    Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.initOwner(((Node)event.getSource()).getScene().getWindow());
    dialog.setTitle("Profile Details");
    dialog.setScene(new Scene(root));
    dialog.setResizable(false);
    dialog.showAndWait();
  }

  @FXML
  private void goToViewPersonal() {

  }

  @FXML
  private void goToDeletePersonal() {

  }

  @FXML
  private void goToCheckPackage() {

  }

  @FXML
  private void goToBookPackage() {

  }

  @FXML
  private void goToViewPackage() {

  }

  @FXML
  private void goToViewHotels() {

  }

  @FXML
  private void goToBookHotel() {

  }

  @FXML
  private void goToViewBookedHotel() {

  }

  @FXML
  private void goToDestinations() {

  }

  @FXML
  private void goToPayment() {

  }

  @FXML
  private void goToCalculator() {

  }

  @FXML
  private void goToNotepad() {

  }

//  @FXML
//  private void handleAbout() {
//
//  }

  // reuse for all nav items
  private void loadView(String fxmlName) {
    try {
      Parent view = FXMLLoader.load(
              getClass().getResource("/com/travel/smartcity/" + fxmlName)
      );
      centerStackPane.getChildren().setAll(view);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
