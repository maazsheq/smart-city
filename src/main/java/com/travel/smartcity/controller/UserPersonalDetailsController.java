package com.travel.smartcity.controller;
import com.travel.smartcity.model.User;
import com.travel.smartcity.model.UserPersonalDetails;
import com.travel.smartcity.service.UserPersonalDetailsService;
import com.travel.smartcity.util.session.Session;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class UserPersonalDetailsController {
  @FXML private TextField usernameField, numberField, nameField;
  @FXML private ComboBox<String> idCombo;
  @FXML private RadioButton maleBtn, femaleBtn;
  @FXML private ToggleGroup genderGroup;
  @FXML private TextField countryField, phoneField;
  @FXML private TextArea addressArea;
  @FXML private Button addBtn, backBtn;
  private static boolean detailsPresent = false;

  // inject your service (could be via DI or you can new it)
  private final UserPersonalDetailsService service = new UserPersonalDetailsService();
  @FXML
  public void initialize() {
    // get current user from session
    User current = Session.getCurrentUser();
    if (current == null) {
      return; // no session, leave prompts
    }

    // try to load existing details
    Optional<UserPersonalDetails> opt = service.findByUserId(current.getId());
    detailsPresent = opt.isPresent();
    if (detailsPresent) {
      UserPersonalDetails d = opt.get();
      countryField.setText(d.getCountry());
      phoneField .setText(d.getPhone());
      addressArea .setText(d.getAddress());

      // select appropriate radio button
      if ("Male".equalsIgnoreCase(d.getGender())) {
        genderGroup.selectToggle(maleBtn);
      } else if ("Female".equalsIgnoreCase(d.getGender())) {
        genderGroup.selectToggle(femaleBtn);
      }
    }
    // else: leave promptText in place
  }
  @FXML
  private void handleAdd() {
    // 1) Gather form data
    User currentUser = Session.getCurrentUser();
    UserPersonalDetails details = getUserPersonalDetails(currentUser);

    boolean ok = service.saveOrUpdate(details);
    Alert alert = new Alert(ok ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
            ok ? "Saved successfully!" : "Save failed.");
    alert.showAndWait();

    if (ok) {
      // close on success
      Stage stage = (Stage)addBtn.getScene().getWindow();
      stage.close();
    }
  }

  @NotNull
  private UserPersonalDetails getUserPersonalDetails(User currentUser) {
    int userId      = currentUser.getId();
    String gender    = ((RadioButton)genderGroup.getSelectedToggle()).getText();
    String country   = countryField.getText();
    String phone     = phoneField.getText();
    String address   = addressArea.getText();

    // 2) Build model and save
    UserPersonalDetails details = new UserPersonalDetails();
    details.setUserId(userId);
    details.setGender(gender);
    details.setCountry(country);
    details.setPhone(phone);
    details.setAddress(address);
    return details;
  }

  @FXML
  private void handleBack() {
    // just close the dialog
    Stage stage = (Stage)backBtn.getScene().getWindow();
    stage.close();
  }
}

