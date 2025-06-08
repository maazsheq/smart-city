package com.travel.smartcity.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {
  private int id;
  private int userId;
  private int payeeId;
  private BigDecimal amount;
  private LocalDateTime dateTime;

  public Payment(int id, int userId, int payeeId, BigDecimal amount, LocalDateTime dateTime) {
    this.id = id;
    this.userId = userId;
    this.payeeId = payeeId;
    this.amount = amount;
    this.dateTime = dateTime;
  }

  public int getId() { return id; }
  public int getUserId() { return userId; }
  public int getPayeeId() { return payeeId; }
  public BigDecimal getAmount() { return amount; }
  public LocalDateTime getDateTime() { return dateTime; }

  public void setId(int id) { this.id = id; }
  public void setUserId(int userId) { this.userId = userId; }
  public void setPayeeId(int payeeId) { this.payeeId = payeeId; }
  public void setAmount(BigDecimal amount) { this.amount = amount; }
  public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
}