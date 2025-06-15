package com.travel.smartcity.model;

import java.time.LocalDate;

public class Booking {
  private int    id;
  private int    userId;
  private int    packageId;
  private LocalDate bookingDate;

  public Booking() { }

  /** for inserts (no id yet) */
  public Booking(int userId, int packageId, LocalDate bookingDate) {
    this.userId      = userId;
    this.packageId   = packageId;
    this.bookingDate = bookingDate;
  }

  /** full constructor */
  public Booking(int id, int userId, int packageId, LocalDate bookingDate) {
    this.id          = id;
    this.userId      = userId;
    this.packageId   = packageId;
    this.bookingDate = bookingDate;
  }

  public int getId()                { return id; }
  public void setId(int id)         { this.id = id; }

  public int getUserId()            { return userId; }
  public void setUserId(int userId) { this.userId = userId; }

  public int getPackageId()             { return packageId; }
  public void setPackageId(int packageId) { this.packageId = packageId; }

  public LocalDate getBookingDate()              { return bookingDate; }
  public void     setBookingDate(LocalDate d)    { this.bookingDate = d; }

  @Override
  public String toString() {
    return "Booking{" +
            "id=" + id +
            ", userId=" + userId +
            ", packageId=" + packageId +
            ", bookingDate=" + bookingDate +
            '}';
  }
}
