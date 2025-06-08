package com.travel.smartcity.controller;

import com.travel.smartcity.util.SceneRouter;
import com.travel.smartcity.util.session.Session;
import com.travel.smartcity.model.User;
import com.travel.smartcity.service.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class ProfileController {
  @FXML private TextField userNameField;
  @FXML private PasswordField newPasswordField;

  private AuthService authService = new AuthService();
  private User currentUser; // initialize from session/context

  @FXML
  private void initialize() {
    // load currentUser data into fields
    currentUser = Session.getCurrentUser();
    if (currentUser != null) {
      userNameField.setText(currentUser.getUsername());
//      newPasswordField.setText(currentUser.getPassword());
    }
//    emailField.setText(currentUser.getEmail());
  }

  @FXML
  private void handleSave() {
    currentUser.setUsername(userNameField.getText());
    String newPass = newPasswordField.getText();
    if (!newPass.isEmpty()) {
      currentUser.setPassword(newPass);
    }
    boolean ok = authService.updateProfile(currentUser);
    new Alert(ok ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
            ok ? "Profile updated." : "Update failed.")
            .showAndWait();
  }

  @FXML
  private void goToDashboard() throws Exception {
    Stage stage = (Stage) userNameField.getScene().getWindow();
    SceneRouter.switchTo( stage, "/com/travel/smartcity/dashboard-view.fxml", "Smart City Traveler");
  }
}