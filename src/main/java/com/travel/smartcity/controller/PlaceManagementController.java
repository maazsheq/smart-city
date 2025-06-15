package com.travel.smartcity.controller;


import com.travel.smartcity.model.Package;
import com.travel.smartcity.model.Place;
import com.travel.smartcity.model.User;
import com.travel.smartcity.service.PackageService;
import com.travel.smartcity.service.PlaceService;
import com.travel.smartcity.util.SceneRouter;
import com.travel.smartcity.util.session.Session;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class PlaceManagementController {

  // TableView and its columns
  @FXML private TableView<Place> placeTable;
  @FXML private TableColumn<Place, Integer> colId;
  @FXML private TableColumn<Place, String>  colName;
  @FXML private TableColumn<Place, String>  colType;
  @FXML private TableColumn<Place, String>  colAddress;

  // Form fields
  @FXML private TextField nameField;
  @FXML private TextField typeField;
  @FXML private TextField addressField;
  @FXML private GridPane addPlacePane;
  @FXML private Button addBtn, backBtn, updateBtn, deleteBtn, managePackagesBtn;

  @FXML private TextField searchField;

  @FXML
  private TextField packageDetailsField;   // fx:id in your FXML
  @FXML
  private Button addPackageBtn;

  private String defaultType;
  public void setDefaultType(String defaultType) {
    this.defaultType = defaultType;
    typeField.setText(defaultType);
    typeField.setEditable(false);
    // Load all places into the table
    refreshTable();
  }

  public void setDefaultTypee(String defaultType) {
    // Pre‐fill the type and disable editing
    typeField.setText(defaultType);
    typeField.setEditable(false);
  }


  private PlaceService placeService = new PlaceService();
  private PackageService packageService = new PackageService();

  @FXML
  private void initialize() {

    User current = Session.getCurrentUser();
    boolean isAdmin = (current != null && current.isAdmin());

    // Show/hide all but Back
    addBtn           .setVisible(isAdmin);
    updateBtn        .setVisible(isAdmin);
    deleteBtn        .setVisible(isAdmin);
//    managePackagesBtn.setVisible(isAdmin);
    addPlacePane     .setVisible(isAdmin);
    // Configure table columns to map to Place properties
    colId.setCellValueFactory(new PropertyValueFactory<>("id"));
    colName.setCellValueFactory(new PropertyValueFactory<>("name"));
    colType.setCellValueFactory(new PropertyValueFactory<>("type"));
    colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));


    // When a row is clicked, populate the form fields
    placeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
      if (newSel != null) {
        nameField.setText(newSel.getName());
        typeField.setText(newSel.getType());
        addressField.setText(newSel.getAddress());
      }
    });
  }


//  private void refreshTable() {
//    List<Place> all = placeService.listAllPlaces();
//    placeTable.setItems(FXCollections.observableArrayList(all));
//    clearForm();
//  }

  private void refreshTable() {
    List<Place> list = placeService.listPlacesByType(defaultType);
    placeTable.setItems(FXCollections.observableArrayList(list));
    clearForm();
  }

  private void clearForm() {
    nameField.clear();
//    typeField.clear();
    addressField.clear();
  }

  @FXML
  private void handleAddPlace() {
    String name    = nameField.getText().trim();
    String type    = typeField.getText().trim();
    String address = addressField.getText().trim();

    if (name.isEmpty() || type.isEmpty() || address.isEmpty()) {
      new Alert(Alert.AlertType.WARNING, "All fields must be filled in.").showAndWait();
      return;
    }

    Place p = new Place(0, name, type, address);
    boolean ok = placeService.addPlace(p);
    if (ok) {
      new Alert(Alert.AlertType.INFORMATION, "Place added successfully.").showAndWait();
      refreshTable();
    } else {
      new Alert(Alert.AlertType.ERROR, "Failed to add place.").showAndWait();
    }
  }

  @FXML
  private void handleUpdatePlace() {
    Place selected = placeTable.getSelectionModel().getSelectedItem();
    if (selected == null) {
      new Alert(Alert.AlertType.WARNING, "Select a place from the table first.").showAndWait();
      return;
    }

    String name    = nameField.getText().trim();
    String type    = typeField.getText().trim();
    String address = addressField.getText().trim();

    if (name.isEmpty() || type.isEmpty() || address.isEmpty()) {
      new Alert(Alert.AlertType.WARNING, "All fields must be filled in.").showAndWait();
      return;
    }

    selected.setName(name);
    selected.setType(type);
    selected.setAddress(address);
    boolean ok = placeService.updatePlace(selected);
    if (ok) {
      new Alert(Alert.AlertType.INFORMATION, "Place updated successfully.").showAndWait();
      refreshTable();
    } else {
      new Alert(Alert.AlertType.ERROR, "Failed to update place.").showAndWait();
    }
  }

  @FXML
  private void handleDeletePlace() {
    Place selected = placeTable.getSelectionModel().getSelectedItem();
    if (selected == null) {
      new Alert(Alert.AlertType.WARNING, "Select a place to delete.").showAndWait();
      return;
    }

    // Confirm deletion
    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
            "Are you sure you want to delete “" + selected.getName() + "”?",
            ButtonType.YES, ButtonType.NO
    );
    confirm.showAndWait().ifPresent(button -> {
      if (button == ButtonType.YES) {
        boolean ok = placeService.deletePlace(selected.getId());
        if (ok) {
          new Alert(Alert.AlertType.INFORMATION, "Place deleted.").showAndWait();
          refreshTable();
        } else {
          new Alert(Alert.AlertType.ERROR, "Failed to delete place.").showAndWait();
        }
      }
    });
  }

  @FXML
  private void handleManagePackages(ActionEvent event) throws IOException {
    Place selected = placeTable.getSelectionModel().getSelectedItem();
    if (selected == null) {
      new Alert(Alert.AlertType.WARNING,
              "Select a place before managing its packages.")
              .showAndWait();
      return;
    }

    // Load the package‐management dialog
    FXMLLoader loader = new FXMLLoader(getClass()
            .getResource("/com/travel/smartcity/place-packages-dialog.fxml"));
    Parent root = loader.load();

    // Pass the selected place’s ID into the package controller
    PackageManagementController pkgCtrl = loader.getController();
    pkgCtrl.setPlace(selected.getId());

    Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.initOwner(((Node)event.getSource()).getScene().getWindow());
    dialog.setTitle("Packages for “" + selected.getName() + "”");
    dialog.setScene(new Scene(root));
    dialog.setResizable(false);
    dialog.showAndWait();
  }


//  @FXML
//  private void handleAddPackage() {
//    Place selected = placeTable.getSelectionModel().getSelectedItem();
//    if (selected == null) {
//      new Alert(Alert.AlertType.WARNING, "Select a place before adding a package.").showAndWait();
//      return;
//    }
//
//    String details = packageDetailsField.getText().trim();
//    if (details.isEmpty()) {
//      new Alert(Alert.AlertType.WARNING, "Package details cannot be empty.").showAndWait();
//      return;
//    }
//
//    // Build the model
//    Package pkg = new Package(selected.getId(), details);
//
//    // Persist via service
//    boolean ok = packageService.create(pkg);
//    new Alert(ok ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
//            ok ? "Package added!" : "Failed to add package.")
//            .showAndWait();
//
//    if (ok) {
//      packageDetailsField.clear();
//      refreshPackageTable(selected);
//    }
//  }

  /** Handler for the “Go” or ENTER in the searchField */
  @FXML
  private void handleSearch() {
    String term = searchField.getText().trim().toLowerCase();
    if (term.isEmpty()) {
      refreshTable();
      return;
    }
    // Filter the full list for this type by name:
    List<Place> filtered = placeService.listPlacesByType(defaultType)
            .stream()
            .filter(p -> p.getName().toLowerCase().contains(term))
            .collect(Collectors.toList());

    placeTable.setItems(FXCollections.observableArrayList(filtered));
    clearForm();
  }

  /** Clears the search box and reloads the full table */
  @FXML
  private void handleClearSearch() {
    searchField.clear();
    refreshTable();
  }

  @FXML
  private void handleBack() {
    // just close the dialog
    Stage stage = (Stage)backBtn.getScene().getWindow();
    stage.close();
  }


  @FXML
  private void goToDashboard() throws Exception {
    Stage stage = (Stage) placeTable.getScene().getWindow();
    SceneRouter.switchTo(stage, "/com/travel/smartcity/dashboard-view.fxml", "Dashboard");
  }
}
