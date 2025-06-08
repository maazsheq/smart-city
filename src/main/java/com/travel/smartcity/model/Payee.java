package com.travel.smartcity.model;

public class Payee {
  private int id;
  private int userId;
  private String name;
  private String accountNumber;

  public Payee(int id, int userId, String name, String accountNumber) {
    this.id = id;
    this.userId = userId;
    this.name = name;
    this.accountNumber = accountNumber;
  }

  public int getId() { return id; }
  public int getUserId() { return userId; }
  public String getName() { return name; }
  public String getAccountNumber() { return accountNumber; }

  public void setId(int id) { this.id = id; }
  public void setUserId(int userId) { this.userId = userId; }
  public void setName(String name) { this.name = name; }
  public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
}