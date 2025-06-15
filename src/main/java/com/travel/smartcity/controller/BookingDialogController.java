package com.travel.smartcity.controller;

import com.travel.smartcity.model.Booking;
import com.travel.smartcity.model.PayeeCardDetails;
import com.travel.smartcity.model.User;
import com.travel.smartcity.service.BookingService;
import com.travel.smartcity.service.PackageService;
import com.travel.smartcity.service.PayeeService;
import com.travel.smartcity.service.PaymentService;
import com.travel.smartcity.util.session.Session;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class BookingDialogController {
  @FXML private TextField cardHolderField;  // new
  @FXML private TextField cardNumField;
  @FXML private TextField expiryField;
  @FXML private PasswordField cvvField;
  @FXML private DatePicker   datePicker;

  private final BookingService bookingService = new BookingService();
  private final PayeeService    payeeService   = new PayeeService();
  private final PaymentService  paymentService = new PaymentService();
  private final PackageService  packageService = new PackageService();

  private int selectedPackageId;

  /** Called by the opener */
  public void setPackageId(int pkgId) {
    this.selectedPackageId = pkgId;
  }

  @FXML
  private void initialize() {
    // once session exists, load the user's default card
    User u = Session.getCurrentUser();
    if (u != null) {
      int userId = u.getId();
      PayeeCardDetails p = payeeService.getDefaultPayee(userId);
      if (p != null) {
        cardHolderField.setText(p.getName());
        cardNumField   .setText(p.getAccountNumber());
        expiryField    .setText(p.getExpiry());
        cvvField       .setText(p.getCvv());
      }
    }
    // only allow today or future:
    datePicker.setDayCellFactory(dp -> new DateCell() {
      @Override
      public void updateItem(LocalDate d, boolean empty) {
        super.updateItem(d, empty);
        if (d.isBefore(LocalDate.now())) setDisable(true);
      }
    });
    datePicker.setValue(LocalDate.now());
  }

  @FXML
  private void handleConfirm() {

    String holder = cardHolderField.getText().trim();
    String card   = cardNumField.getText().trim();
    String exp    = expiryField.getText().trim();
    String cvv    = cvvField.getText().trim();
    LocalDate date= datePicker.getValue();

    // ---- Simple form validations ----
    if (holder.isEmpty() || card.isEmpty() || exp.isEmpty()
            || cvv.isEmpty() || date == null) {
      showWarning("All fields are required.");
      return;
    }
    if (holder.length() < 3) {
      showWarning("Cardholder name must be at least 3 characters.");
      return;
    }
    // 13–19 digits (Visa, MC, etc)
    if (!card.matches("\\d{13,19}")) {
      showWarning("Card number must be 13–19 digits.");
      return;
    }
    // MM/YY pattern, month 01–12
    if (!exp.matches("(0[1-9]|1[0-2])/[0-9]{2}")) {
      showWarning("Expiry must be in MM/YY format.");
      return;
    }

    // 1) Validate format & parse into YearMonth
    YearMonth expiryYm;
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/yy");
    try {
      expiryYm = YearMonth.parse(exp, fmt);
    } catch (DateTimeParseException e) {
      showWarning("Expiry must be in MM/YY format (e.g. 08/25).");
      return;
    }

    // 2) Check that it’s not in the past
    if (expiryYm.isBefore(YearMonth.now())) {
      showWarning("Card expiry must be in the future.");
      return;
    }
    // CVV: exactly 3 digits
    if (!cvv.matches("\\d{3}")) {
      showWarning("CVV must be exactly 3 digits.");
      return;
    }
    if (date.isBefore(LocalDate.now())) {
      showWarning("Booking date cannot be in the past.");
      return;
    }

    User user = Session.getCurrentUser();
    int userId = user.getId();

    // 🚧 NEW: duplicate booking check
    if (bookingService.bookingExists(userId, selectedPackageId, date)) {
      new Alert(Alert.AlertType.WARNING,
              "You’ve already booked this package for " + date + "."
      ).showAndWait();
      return;
    }

    // 1) Create booking
    Booking booking = new Booking(userId, selectedPackageId, date);
    int bookingId = bookingService.createBooking(booking);
    if (bookingId < 0) {
      new Alert(Alert.AlertType.ERROR,
              "Failed to create booking"
      ).showAndWait();
      return;
    }

    // 2) Upsert the payee (card details)
    PayeeCardDetails payee = new PayeeCardDetails(
            userId,
            holder,
            card,
            exp,
            cvv
    );
    payeeService.createOrUpdateDefaultPayee(payee);

    // 3) Record payment
    BigDecimal amount = packageService.getPackagePrice(selectedPackageId);
    boolean paid = paymentService.recordPayment(
            userId,
            bookingId,
            amount,
            "CARD",
            "PAID"
    );

    new Alert(paid
            ? Alert.AlertType.INFORMATION
            : Alert.AlertType.ERROR,
            paid
                    ? "Booking & payment successful!"
                    : "Payment failed."
    ).showAndWait();

    if (paid) {
      ((Stage)cardNumField.getScene().getWindow()).close();
    }
  }


  private void showWarning(String msg) {
    new Alert(Alert.AlertType.WARNING, msg)
            .showAndWait();
  }
  @FXML
  private void handleCancel() {
    ((Stage)cardNumField.getScene().getWindow()).close();
  }
}
