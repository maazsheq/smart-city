package com.travel.smartcity.controller;

import com.travel.smartcity.model.User;
import com.travel.smartcity.service.UserService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class UserManagementController {

  @FXML private TableView<User> userTable;
  @FXML private TableColumn<User, Integer> colUserId;
  @FXML private TableColumn<User, String>  colUsername;
  @FXML private TableColumn<User, String>  colEmail;
  @FXML private TableColumn<User, Boolean> colIsAdmin;

  @FXML private TextField usernameField;
  @FXML private TextField emailField;
  @FXML private PasswordField passwordField;
  @FXML private CheckBox isAdminCheckbox;

  private final UserService userService = new UserService();

  @FXML
  private void initialize() {
    // Configure table columns
    colUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
    colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
    colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    colIsAdmin.setCellValueFactory(new PropertyValueFactory<>("admin"));

    loadUsersIntoTable();

    // When a user is selected in the table, populate the form
    userTable.getSelectionModel().selectedItemProperty().addListener((obs, oldUser, newUser) -> {
      if (newUser != null) {
        usernameField.setText(newUser.getUsername());
        emailField.setText(newUser.getEmail());
        // We don't show the password for security; they’ll need to reset if needed
        passwordField.clear();
        isAdminCheckbox.setSelected(newUser.isAdmin());
      }
    });
  }

  private void loadUsersIntoTable() {
    List<User> all = userService.listAllUsers();
    userTable.setItems(FXCollections.observableArrayList(all));
    clearForm();
  }

  private void clearForm() {
    usernameField.clear();
    emailField.clear();
    passwordField.clear();
    isAdminCheckbox.setSelected(false);
  }

  @FXML
  private void handleAddUser() {
    String uname = usernameField.getText().trim();
    String email = emailField.getText().trim();
    String pass  = passwordField.getText();
    boolean admin = isAdminCheckbox.isSelected();

    if (uname.isEmpty() || email.isEmpty() || pass.isEmpty()) {
      new Alert(Alert.AlertType.WARNING, "All fields are required.").showAndWait();
      return;
    }

    User u = new User(uname, pass, email, admin);
    boolean ok = userService.createUser(u);
    if (ok) {
      new Alert(Alert.AlertType.INFORMATION, "User created.").showAndWait();
      loadUsersIntoTable();
    } else {
      new Alert(Alert.AlertType.ERROR, "Failed to create user.").showAndWait();
    }
  }

  @FXML
  private void handleUpdateUser() {
    User selected = userTable.getSelectionModel().getSelectedItem();
    if (selected == null) {
      new Alert(Alert.AlertType.WARNING, "Select a user from the table.").showAndWait();
      return;
    }

    selected.setUsername(usernameField.getText().trim());
    selected.setEmail(emailField.getText().trim());
    String pass = passwordField.getText();
    if (!pass.isEmpty()) {
      selected.setPassword(pass);
    }
    selected.setAdmin(isAdminCheckbox.isSelected());

    boolean ok = userService.updateUser(selected);
    if (ok) {
      new Alert(Alert.AlertType.INFORMATION, "User updated.").showAndWait();
      loadUsersIntoTable();
    } else {
      new Alert(Alert.AlertType.ERROR, "Failed to update user.").showAndWait();
    }
  }

  @FXML
  private void handleDeleteUser() {
    User selected = userTable.getSelectionModel().getSelectedItem();
    if (selected == null) {
      new Alert(Alert.AlertType.WARNING, "Select a user to delete.").showAndWait();
      return;
    }

    // Confirm deletion
    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
            "Are you sure you want to delete user “" + selected.getUsername() + "”?",
            ButtonType.YES, ButtonType.NO
    );
    confirm.showAndWait().ifPresent(choice -> {
      if (choice == ButtonType.YES) {
        boolean ok = userService.deleteUser(selected.getId());
        if (ok) {
          new Alert(Alert.AlertType.INFORMATION, "User deleted.").showAndWait();
          loadUsersIntoTable();
        } else {
          new Alert(Alert.AlertType.ERROR, "Failed to delete user.").showAndWait();
        }
      }
    });
  }
}
