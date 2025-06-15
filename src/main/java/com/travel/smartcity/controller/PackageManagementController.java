package com.travel.smartcity.controller;

import com.travel.smartcity.model.Package;
import com.travel.smartcity.model.User;
import com.travel.smartcity.service.PackageService;
import com.travel.smartcity.util.session.Session;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class PackageManagementController {

  @FXML private TableView<Package> packageTable;
  @FXML private TableColumn<Package,Integer> colPkgId;
  @FXML private TableColumn<Package,String>  colPkgDetails;
  @FXML private TableColumn<Package,String> colPrice;

  @FXML private TextArea detailsField;
  @FXML private TextField priceField;
  @FXML private Button addPackageBtn, updatePackageBtn, deletePackageBtn, closeBtn;
  @FXML private GridPane addPackagePane;

  @FXML private Button bookNowBtn;
  private final PackageService packageService = new PackageService();
  private int placeId;

  /** Called by the opener to tell us which place we belong to */
  public void setPlace(int placeId) {
    this.placeId = placeId;
    loadPackages();
  }

  @FXML
  private void initialize() {
    User current = Session.getCurrentUser();
    boolean isAdmin = (current != null && current.isAdmin());

    // Show/hide all but Back
    addPackageBtn           .setVisible(isAdmin);
    updatePackageBtn        .setVisible(isAdmin);
    deletePackageBtn        .setVisible(isAdmin);
    addPackagePane        .setVisible(isAdmin);
    colPkgId      .setCellValueFactory(new PropertyValueFactory<>("id"));
    colPkgDetails .setCellValueFactory(new PropertyValueFactory<>("details"));
    colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    // to show details in a Text node that wraps
    colPkgDetails.setCellFactory(col -> {
      return new TableCell<Package,String>() {
        private final Text text = new Text();
        {
          // Bind wrapping width to (column width minus padding)
          text.wrappingWidthProperty()
                  .bind(col.widthProperty().subtract(10));
          setGraphic(text);
          // Let the row's height be computed based on the Text node
          setPrefHeight(Control.USE_COMPUTED_SIZE);
        }
        @Override
        protected void updateItem(String item, boolean empty) {
          super.updateItem(item, empty);
          if (empty || item == null) {
            text.setText(null);
          } else {
            text.setText(item);
          }
        }
      };
    });

    packageTable.getSelectionModel()
            .selectedItemProperty()
            .addListener((obs,oldP,newP) -> {
              if (newP != null) {
                detailsField.setText(newP.getDetails());
                priceField.setText(newP.getPrice());
              }
            });
  }

  private void loadPackages() {
    List<Package> list = packageService.listByPlaceId(placeId);
    packageTable.setItems(FXCollections.observableArrayList(list));
    detailsField.clear();
    priceField.clear();
  }

  @FXML
  private void handleAddPackage() {
    String details  = detailsField.getText().trim();
    String priceRaw = priceField.getText().trim();

    if (details.isEmpty()) {
      warn("Details cannot be empty.");
      return;
    }
    if (!priceRaw.matches("\\d+\\$")) {
      warn("Price must be digits followed by ‘$’, e.g. “123$”.");
      return;
    }

    // strip trailing ‘$’
    String numeric = priceRaw.substring(0, priceRaw.length() - 1);

    int value;
    try {
      value = Integer.parseInt(numeric);
    } catch (NumberFormatException ex) {
      // thrown if numeric is too big for int
      warn("That number is too large!  Maximum allowed price is 10,000,000$.");
      return;
    }

    if (value < 0 || value > 10_000_000) {
      warn("Price must be between 0$ and 10,000,000$.");
      return;
    }

    Package pkg = new Package(placeId, details, priceRaw);
    boolean ok = packageService.create(pkg);
    info(ok, "Package added.", "Failed to add package.");
    if (ok) loadPackages();
  }

  @FXML
  private void handleUpdatePackage() {
    Package sel = packageTable.getSelectionModel().getSelectedItem();
    if (sel == null) {
      warn("Select a package first.");
      return;
    }

    String details  = detailsField.getText().trim();
    String priceRaw = priceField.getText().trim();

    if (details.isEmpty()) {
      warn("Details cannot be empty.");
      return;
    }
    if (!priceRaw.matches("\\d+\\$")) {
      warn("Price must be digits followed by ‘$’, e.g. “123$”.");
      return;
    }

    String numeric = priceRaw.substring(0, priceRaw.length() - 1);

    int value;
    try {
      value = Integer.parseInt(numeric);
    } catch (NumberFormatException ex) {
      warn("That number is too large!  Maximum allowed price is 10,000,000$.");
      return;
    }

    if (value < 0 || value > 10_000_000) {
      warn("Price must be between 0$ and 10,000,000$.");
      return;
    }

    sel.setDetails(details);
    sel.setPrice(priceRaw);
    boolean ok = packageService.update(sel);
    info(ok, "Package updated.", "Update failed.");
    if (ok) loadPackages();
  }

  private void warn(String msg) {
    new Alert(Alert.AlertType.WARNING, msg).showAndWait();
  }

  private void info(boolean ok, String success, String failure) {
    new Alert(ok ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
            ok ? success : failure)
            .showAndWait();
  }


//  @FXML
//  private void handleAddPackage() {
//    String details = detailsField.getText().trim();
//    String price = priceField.getText().trim();
//    if (price.isEmpty()) {
//      new Alert(Alert.AlertType.WARNING, "Price cannot be empty.").showAndWait();
//      return;
//    }
//    if (details.isEmpty()) {
//      new Alert(Alert.AlertType.WARNING, "Details cannot be empty.").showAndWait();
//      return;
//    }
//    Package pkg = new Package(placeId, details, price);
//    boolean ok = packageService.create(pkg);
//    new Alert(ok ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
//            ok ? "Package added." : "Failed to add package.")
//            .showAndWait();
//    if (ok) loadPackages();
//  }
//
//  @FXML
//  private void handleUpdatePackage() {
//    Package sel = packageTable.getSelectionModel().getSelectedItem();
//    if (sel == null) {
//      new Alert(Alert.AlertType.WARNING, "Select a package first.").showAndWait();
//      return;
//    }
//    String details = detailsField.getText().trim();
//    if (details.isEmpty()) {
//      new Alert(Alert.AlertType.WARNING, "Details cannot be empty.").showAndWait();
//      return;
//    }
//    String price = priceField.getText().trim();
//    if (price.isEmpty()) {
//      new Alert(Alert.AlertType.WARNING, "Price cannot be empty.").showAndWait();
//      return;
//    }
//    sel.setDetails(details);
//    sel.setPrice(price);
//    boolean ok = packageService.update(sel);
//    new Alert(ok ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
//            ok ? "Package updated." : "Update failed.")
//            .showAndWait();
//    if (ok) loadPackages();
//  }

  @FXML
  private void handleDeletePackage() {
    Package sel = packageTable.getSelectionModel().getSelectedItem();
    if (sel == null) {
      new Alert(Alert.AlertType.WARNING, "Select a package first.").showAndWait();
      return;
    }
    boolean ok = packageService.delete(sel.getId());
    new Alert(ok ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
            ok ? "Package deleted." : "Deletion failed.")
            .showAndWait();
    if (ok) loadPackages();
  }

  @FXML
  private void handleBookNow() throws IOException {
    Package sel = packageTable.getSelectionModel().getSelectedItem();
    if (sel == null) {
      new Alert(Alert.AlertType.WARNING, "Select a package first").showAndWait();
      return;
    }

    FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/com/travel/smartcity/booking-dialog.fxml")
    );
    Parent root = loader.load();

    // pass the selected package ID
    BookingDialogController ctrl = loader.getController();
    ctrl.setPackageId(sel.getId());

    Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.initOwner(bookNowBtn.getScene().getWindow());
    dialog.setTitle("Book & Pay");

    // 1) build a scene that’s 600×500
    Scene scene = new Scene(root, 600, 500);
    dialog.setScene(scene);

    // 2) enforce it can’t shrink below that
    dialog.setMinWidth(600);
    dialog.setMinHeight(500);


    dialog.showAndWait();
  }

  @FXML
  private void handleClose() {
    Stage stage = (Stage)closeBtn.getScene().getWindow();
    stage.close();
  }
}
