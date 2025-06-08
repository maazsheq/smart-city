package com.travel.smartcity.controller;


import com.travel.smartcity.model.Place;
import com.travel.smartcity.service.PlaceService;
import com.travel.smartcity.util.SceneRouter;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SearchController {
  @FXML private TextField searchField;
  @FXML private TableView<Place> resultTable;
  @FXML private TableColumn<Place,String> nameCol;
  @FXML private TableColumn<Place,String> typeCol;
  @FXML private TableColumn<Place,String> addressCol;
  @FXML private BorderPane rootPane;

  private PlaceService placeService = new PlaceService();

  @FXML
  private void initialize() {
    nameCol.setCellValueFactory(c -> c.getValue().nameProperty());
    typeCol.setCellValueFactory(c -> c.getValue().typeProperty());
    addressCol.setCellValueFactory(c -> c.getValue().addressProperty());
  }

  @FXML
  private void handleSearch() {
    var results = placeService.search(searchField.getText());
    resultTable.setItems(FXCollections.observableArrayList(results));
  }
  @FXML
  private void goToDashboard() throws Exception {
    Stage stage = (Stage) rootPane.getScene().getWindow();
    SceneRouter.switchTo(stage, "/com/travel/smartcity/dashboard-view.fxml", "Smart City Traveler");
//    SceneRouter.switchTo(stage, "/com/travel/smartcity/dashboard-view.fxml", "Smart City Traveler");

  }
}