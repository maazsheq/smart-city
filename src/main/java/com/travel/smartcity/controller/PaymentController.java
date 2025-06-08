package com.travel.smartcity.controller;


import com.travel.smartcity.service.PaymentService;
import com.travel.smartcity.util.SceneRouter;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class PaymentController {
  @FXML private TextField cardField;
  @FXML private TextField expiryField;

  private PaymentService paymentService = new PaymentService();

  @FXML
  private void handlePayment() {
    boolean ok = paymentService.process(
            cardField.getText(), expiryField.getText()
    );
    new Alert(ok ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
            ok ? "Payment Successful" : "Payment Failed")
            .showAndWait();
  }
  @FXML
  private void goToDashboard() throws Exception {
    Stage stage = (Stage) cardField.getScene().getWindow();
//    SceneRouter.switchTo(stage, "/com/travel/smartcity/dashboard-view.fxml", "Smart City Traveler");
    SceneRouter.switchTo(stage, "/com/travel/smartcity/dashboard-view.fxml", "Smart City Traveler");

  }
}
