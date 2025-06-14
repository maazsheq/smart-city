package com.travel.smartcity.controller;

import com.travel.smartcity.util.SceneRouter;
import com.travel.smartcity.util.session.Session;
import com.travel.smartcity.model.User;
import com.travel.smartcity.service.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ProfileController {
  // Current‐password toggles
  @FXML private TextField userNameField;
  @FXML private Button addBtn, backBtn;

  @FXML private PasswordField currentPasswordField;
  @FXML private TextField     currentPasswordFieldPlain;
  @FXML private Button        toggleCurrentPasswordBtn;
  @FXML private ImageView currentEyeIcon;
  private boolean showingCurrent = false;

  // New‐password toggles
  @FXML private PasswordField newPasswordField;
  @FXML private TextField     newPasswordFieldPlain;
  @FXML private Button        toggleNewPasswordBtn;
  @FXML private ImageView     newEyeIcon;
  private boolean showingNew = false;

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
    currentPasswordFieldPlain.textProperty()
            .bindBidirectional(currentPasswordField.textProperty());
    newPasswordFieldPlain.textProperty()
            .bindBidirectional(newPasswordField.textProperty());
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


  @FXML
  private void onToggleCurrentPassword() {
    showingCurrent = !showingCurrent;
    // swap visibility/management
    currentPasswordFieldPlain.setVisible(showingCurrent);
    currentPasswordFieldPlain.setManaged(showingCurrent);
    currentPasswordField     .setVisible(!showingCurrent);
    currentPasswordField     .setManaged(!showingCurrent);

    // swap the icon
    String path = showingCurrent
            ? "/com/travel/smartcity/images/eye-open.png"
            : "/com/travel/smartcity/images/eye-closed.png";
    currentEyeIcon.setImage(
            new Image(getClass().getResourceAsStream(path))
    );
  }

  @FXML
  private void onToggleNewPassword() {
    showingNew = !showingNew;
    newPasswordFieldPlain.setVisible(showingNew);
    newPasswordFieldPlain.setManaged(showingNew);
    newPasswordField     .setVisible(!showingNew);
    newPasswordField     .setManaged(!showingNew);

    String path = showingNew
            ? "/com/travel/smartcity/images/eye-open.png"
            : "/com/travel/smartcity/images/eye-closed.png";
    newEyeIcon.setImage(
            new Image(getClass().getResourceAsStream(path))
    );
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