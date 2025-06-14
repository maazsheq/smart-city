package com.travel.smartcity.controller;


import com.travel.smartcity.model.Place;
import com.travel.smartcity.service.PlaceService;
import com.travel.smartcity.util.SceneRouter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

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
  @FXML private Button addBtn, backBtn;

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

  @FXML
  private void initialize() {
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
