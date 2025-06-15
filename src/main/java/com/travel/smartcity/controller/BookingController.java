package com.travel.smartcity.controller;

import com.travel.smartcity.model.Booking;
import com.travel.smartcity.service.BookingService;
import com.travel.smartcity.util.SceneRouter;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BookingController {
  @FXML private TextField placeField;
  @FXML private DatePicker datePicker;
  @FXML private VBox vBoxBooking;

  private BookingService bookingService = new BookingService();

  @FXML
  private void handleBooking() {
//    Booking b = new Booking(
//            /* id= */0,0,
//            placeField.getText(),
//            datePicker.getValue()
//    );
//    boolean ok = bookingService.book(b);
//    new Alert(ok ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
//            ok ? "Booked!" : "Booking failed.")
//            .showAndWait();
  }

  @FXML
  private void goToDashboard() throws Exception {
    Stage stage = (Stage) vBoxBooking.getScene().getWindow();
//    SceneRouter.switchTo(stage, "/com/travel/smartcity/dashboard-view.fxml", "Smart City Traveler");
    SceneRouter.switchTo(stage, "/com/travel/smartcity/dashboard-view.fxml", "Smart City Traveler");

  }
}