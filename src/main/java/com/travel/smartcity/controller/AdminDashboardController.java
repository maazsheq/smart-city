package com.travel.smartcity.controller;

import com.travel.smartcity.util.SceneRouter;

import com.travel.smartcity.model.User;
import com.travel.smartcity.util.session.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AdminDashboardController {

  @FXML private StackPane contentPane;
  @FXML private BorderPane adminDashboardPane;
  @FXML private MenuItem usernameItem;
  @FXML private MenuItem emailItem;
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
  }

  @FXML
  private void handleLogout() throws Exception {
    Session.clear();
    Stage stage = (Stage) adminDashboardPane.getScene().getWindow();
    SceneRouter.switchTo(stage, "/com/travel/smartcity/login-view.fxml", "Smart City Traveler - Login");
  }



  @FXML
  public void goToAddPersonal(ActionEvent event) throws IOException {
    loadDialog(event, "/com/travel/smartcity/personal-details-dialog.fxml", "Add Personal Details");
  }

  @FXML
  private void updateProfile(ActionEvent event) throws IOException {
    loadDialog(event, "/com/travel/smartcity/profile-dialog.fxml", "Profile Details");
  }

  @FXML
  private void handleHotels(ActionEvent event) throws IOException {
//    loadDialog(event, "/com/travel/smartcity/hotel-dialog.fxml", "Hotel Management");
    openPlaceDialog(event, "Manage Hotels", "Hotel");
  }


  @FXML
  private void handleParks(ActionEvent event) throws IOException {
    openPlaceDialog(event, "Manage Parks", "Park");
  }

  @FXML
  private void handleLibraries(ActionEvent event) throws IOException {
    openPlaceDialog(event, "Manage Libraries", "Library");
  }

  @FXML
  private void handleJobs() {

  }
  @FXML
  private void handleDestinations(ActionEvent event) throws IOException {
    openPlaceDialog(event, "Manage Destinations", "Destination");

  }
  @FXML
  private void handleRestaurants(ActionEvent event) throws IOException {
    openPlaceDialog(event, "Manage Restaurants", "Restaurant");
  }





  @FXML
  private void viewBookings() {

  }

  private void loadDialog(ActionEvent event, String fxml, String title) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass()
            .getResource(fxml));
    Parent root = loader.load();

    Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.initOwner(((Node) event.getSource()).getScene().getWindow());
    dialog.setTitle(title);
    dialog.setScene(new Scene(root));
    dialog.setResizable(false);
    dialog.showAndWait();
  }

  private void openPlaceDialog(ActionEvent event, String title, String defaultType) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass()
            .getResource("/com/travel/smartcity/hotel-dialog.fxml"));
    Parent root = loader.load();

    // 2) Get the controller and set the default type
    PlaceManagementController ctrl = loader.getController();
    ctrl.setDefaultType(defaultType);

    // 3) Show as modal
    Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.initOwner(((Node) event.getSource()).getScene().getWindow());
    dialog.setTitle(title);
    dialog.setScene(new Scene(root));
    dialog.setResizable(false);
    dialog.showAndWait();
  }

  @FXML
  private void handleUsers(ActionEvent event) throws IOException {
    loadDialog(event, "/com/travel/smartcity/manage-users-dialog.fxml", "Users Details");
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

}
