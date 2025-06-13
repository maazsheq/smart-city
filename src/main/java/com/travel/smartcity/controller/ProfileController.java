package com.travel.smartcity.controller;

import com.travel.smartcity.util.SceneRouter;
import com.travel.smartcity.util.session.Session;
import com.travel.smartcity.model.User;
import com.travel.smartcity.service.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ProfileController {
  @FXML private TextField userNameField;
  @FXML private PasswordField currentPasswordField;
  @FXML private PasswordField newPasswordField;
  @FXML private Button addBtn, backBtn;

  @FXML private CheckBox showCurrentPassword, showNewPassword;
  @FXML private TextField currentPasswordFieldVisible, newPasswordFieldVisible;

  private AuthService authService = new AuthService();
  private User currentUser; // initialize from session/context

  @FXML
  private void initialize() {
    // load currentUser data into fields
    currentUser = Session.getCurrentUser();
    if (currentUser != null) {
      userNameField.setText(currentUser.getUsername());
      currentPasswordField.setText(currentUser.getPassword());
    }
//    emailField.setText(currentUser.getEmail());
    bindPasswordToggle(currentPasswordField, currentPasswordFieldVisible, showCurrentPassword);
    bindPasswordToggle(newPasswordField,     newPasswordFieldVisible,     showNewPassword);
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

    if (ok) {
      // close on success
      Stage stage = (Stage)addBtn.getScene().getWindow();
      stage.close();
    }
  }

  @FXML
  private void handleBack() {
    // just close the dialog
    Stage stage = (Stage)backBtn.getScene().getWindow();
    stage.close();
  }

  private void bindPasswordToggle(PasswordField pwdField,
                                  TextField plainField,
                                  CheckBox toggle) {
    // Keep their text in sync
    plainField.textProperty().bindBidirectional(pwdField.textProperty());

    // When toggle is selected, show plainField; else show pwdField
    plainField.visibleProperty().bind(toggle.selectedProperty());
    plainField.managedProperty().bind(toggle.selectedProperty());

    pwdField.visibleProperty().bind(toggle.selectedProperty().not());
    pwdField.managedProperty().bind(toggle.selectedProperty().not());
  }

  @FXML
  private void goToDashboard() throws Exception {
    Stage stage = (Stage) userNameField.getScene().getWindow();
    SceneRouter.switchTo( stage, "/com/travel/smartcity/dashboard-view.fxml", "Smart City Traveler");
  }



}