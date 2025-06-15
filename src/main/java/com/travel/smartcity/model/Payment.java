package com.travel.smartcity.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Payment {
  private int          id;
  private int          bookingId;
  private int          userId;
  private BigDecimal   amount;
  private OffsetDateTime paidAt;
  private String       method;
  private String       status;

  public Payment() { }

  /** for inserts (id will be generated) */
  public Payment(int bookingId,
                 int userId,
                 BigDecimal amount,
                 OffsetDateTime paidAt,
                 String method,
                 String status)
  {
    this.bookingId = bookingId;
    this.userId    = userId;
    this.amount    = amount;
    this.paidAt    = paidAt;
    this.method    = method;
    this.status    = status;
  }

  /** full constructor */
  public Payment(int id,
                 int bookingId,
                 int userId,
                 BigDecimal amount,
                 OffsetDateTime paidAt,
                 String method,
                 String status)
  {
    this.id        = id;
    this.bookingId = bookingId;
    this.userId    = userId;
    this.amount    = amount;
    this.paidAt    = paidAt;
    this.method    = method;
    this.status    = status;
  }

  public int getId()                    { return id; }
  public void setId(int id)             { this.id = id; }

  public int getBookingId()             { return bookingId; }
  public void setBookingId(int bid)     { this.bookingId = bid; }

  public int getUserId()                { return userId; }
  public void setUserId(int uid)        { this.userId = uid; }

  public BigDecimal getAmount()         { return amount; }
  public void setAmount(BigDecimal amt) { this.amount = amt; }

  public OffsetDateTime getPaidAt()          { return paidAt; }
  public void           setPaidAt(OffsetDateTime t) { this.paidAt = t; }

  public String getMethod()             { return method; }
  public void   setMethod(String m)     { this.method = m; }

  public String getStatus()             { return status; }
  public void   setStatus(String s)     { this.status = s; }

  @Override
  public String toString() {
    return "Payment{" +
            "id=" + id +
            ", bookingId=" + bookingId +
            ", userId=" + userId +
            ", amount=" + amount +
            ", paidAt=" + paidAt +
            ", method='" + method + '\'' +
            ", status='" + status + '\'' +
            '}';
  }
}
